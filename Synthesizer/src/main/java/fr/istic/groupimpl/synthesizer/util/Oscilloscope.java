package fr.istic.groupimpl.synthesizer.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import fr.istic.groupimpl.synthesizer.logger.Log;

public class Oscilloscope extends Region {

	private final long TIME_STABILITY=5000;
	
	public static final int SIZE_BUFFER_READ = 2048;
	final int nbDataUsed = 1024;

	private boolean isRunning = false;

	private final GetBuffer cmdGetBuffer;
	private long refreshPeriod;

	public static final long MAX_REFRESH_PERIOD = 2000;
	public static final long MIN_REFRESH_PERIOD = 100;

	private Rectangle rectangle;

	private Thread refreshThread;

	/**
	 * interface of the getBuffuer command.
	 */
	public interface GetBuffer {
		double[] getBuffer();
	}

	Oscilloscope(OscilloscopeFactory oscFact) {
		setHeight(oscFact.getHeight());
		setWidth(oscFact.getWidth());

		cmdGetBuffer = oscFact.getCmdGetBuffer();
		refreshPeriod = oscFact.getRefreshPeriod();
		rectangle = new Rectangle(0, 0, getWidth(), getHeight());
		rectangle.setFill(Color.BLACK);

		getChildren().add(rectangle);

		rectangle.toFront();

		refreshThread = new Thread(() -> {
			Log.getInstance().trace("Debut refreshThread");

			while (isRunning) {
				try {
					Thread.sleep(refreshPeriod);
				} catch (InterruptedException e) {
					isRunning = false;
					Thread.currentThread().interrupt();
				}
				Log.getInstance().trace("Boucle dans refreshThread");
				Platform.runLater(() -> affBuf());
			}
		});
	}

	private class HistMax
	{
		
		public HistMax(long t, double v) {
			super();
			this.t = t;
			this.v = v;
		}
		long t;
		double v;
	}
	
	private Deque<HistMax> dequeStability=new LinkedList<>();
	private void stabAdd(double v)
	{
		dequeStability.addLast(new HistMax((new Date()).getTime(), Math.abs(v)));
	}
	
	private double stabGetMax()
	{
		double res=0.;
		double memT=-1;
		boolean flagErr=false;
		for ( HistMax p:dequeStability)
		{
			res = Math.max(p.v,res);
			if ( memT > p.t )
			{
				// anomalie du temps
				flagErr=true;
			}
			memT = p.t;
		}
		if( flagErr )
			dequeStability.clear();
		return res;
	}
	private void stabPurge()
	{
		boolean flagCont=true;
		long borneT=(new Date()).getTime()-TIME_STABILITY;
		while( flagCont && !dequeStability.isEmpty() )
		{
			HistMax hm = dequeStability.getFirst();
			if ( hm.t < borneT )
			{
				dequeStability.removeFirst();
			}
			else
			{
				flagCont = false;
			}
		}
	}

	/**
	 * Set the refresh period
	 * 
	 * @param v
	 *            Value of the period in seconds
	 */
	public void setRefreshPeriod(double v) {
		refreshPeriod = (long) (v * 1000);

		if (refreshPeriod < MIN_REFRESH_PERIOD)
			refreshPeriod = MIN_REFRESH_PERIOD;
		if (refreshPeriod > MAX_REFRESH_PERIOD)
			refreshPeriod = MAX_REFRESH_PERIOD;
	}

	/**
	 * To start the refresh thread
	 */
	public void start() {
		Log.getInstance().trace("Oscilloscope : start");
		isRunning = true;
		refreshThread.start();
	}

	
	
	/**
	 * To stop the refresh thread
	 */
	public void stop() {
		Log.getInstance().trace("Oscilloscope : stop");
		if (isRunning) {
			isRunning = false;
			refreshThread.interrupt();
		}

		isRunning = false;
	}

	private List<Line> memLine = new ArrayList<>();

	private Line middleLine;
	private Line hightLine;
	private Line lowLine;

	private Text middleText;
	private Text hightText;
	private Text lowText;

	private double coefX;
	private double coefY;
	private double baseY;

	private String strVolt(double value) {
		if (value == Math.floor(value)) {
			return "" + ((int) value) + " V";
		} else {
			return "" + value + " V";
		}

	}
	private double lastHValue=1.;
	private void affBuf() {

		double[] buf = cmdGetBuffer.getBuffer();
		double moy = 0;
		double valMin = 100;
		double valMax = -100;
		int nbPassage = 0;
		int firstPassage = 0;

		for (int i = 0; i < buf.length; i++) {
			moy += buf[i];
			if (i != 0 && buf[i - 1] <= 0. && buf[i] >= 0.) {
				if (nbPassage == 0) {
					firstPassage = i;
				}
				nbPassage++;
			}
			valMin = Math.min(buf[i], valMin);
			valMax = Math.max(buf[i], valMax);
		}
		moy /= buf.length;

		Log.getInstance().trace(
				"moy=" + moy + " valMin=" + valMin + " valMax=" + valMax);

		if (memLine.size() == 0) {
			middleLine = new Line(0, 0, 0, 0);
			middleLine.setStroke(Color.WHITE);
			getChildren().add(middleLine);
			lowLine = new Line(0, 0, 0, 0);
			lowLine.setStroke(Color.ORANGE);
			getChildren().add(lowLine);
			hightLine = new Line(0, 0, 0, 0);
			hightLine.setStroke(Color.ORANGE);
			getChildren().add(hightLine);
			int borne = nbDataUsed - 1;
			for (int i = 0; i < borne; i++) {
				Line line;
				memLine.add(line = new Line(0, 0, 0, 0));
				line.setStroke(Color.GREEN);
				getChildren().add(line);
			}
			coefX = getWidth() / (nbDataUsed - 1);

			middleText = new Text("");
			middleText.setFill(Color.WHITE);
			getChildren().add(middleText);

			lowText = new Text("");
			lowText.setFill(Color.ORANGE);
			getChildren().add(lowText);

			hightText = new Text("");
			hightText.setFill(Color.ORANGE);
			getChildren().add(hightText);

		}
		if (nbPassage < 2 || firstPassage >= 1024 )
			firstPassage = 0;

		double hVjuste = Math.max(Math.abs(valMax), Math.abs(valMin));
		double hValue = Math.ceil(hVjuste);

		if (hValue == 1.) {
			double[] tabCoef = { 1., 2., 5., 10., 20., 50., 100. };
			int i;
			for (i = 1; i < tabCoef.length; i++) {
				if (Math.ceil(hVjuste * tabCoef[i]) != 1.) {
					hValue = 1. / tabCoef[i - 1];
					break;
				}
			}
			if (i == tabCoef.length) {
				hValue = 1. / tabCoef[i - 1];
			}
		}

		if (hValue == 0.)
		{
			hValue = lastHValue;
		}
		stabAdd(hValue);
		hValue = stabGetMax();
		stabPurge();
		
		coefY = -(getHeight() * 0.45) / hValue;
		baseY = getHeight() / 2.;

		middleText.setText("0 V");
		middleText.setTranslateX(0);
		middleText.setTranslateY(baseY+4);
		middleLine.setStartX(middleText.getLayoutBounds().getMaxX());
		middleLine.setStartY(baseY);
		middleLine.setEndX(getWidth());
		middleLine.setEndY(baseY);

		hightText.setTranslateX(0);
		hightText.setTranslateY(baseY + hValue * coefY+4);
		hightText.setText(strVolt(hValue));
		hightLine.setStartX(hightText.getLayoutBounds().getMaxX());
		hightLine.setStartY(baseY + hValue * coefY);
		hightLine.setEndX(getWidth());
		hightLine.setEndY(baseY + hValue * coefY);

		lowText.setTranslateX(0);
		lowText.setTranslateY(baseY - hValue * coefY+4);
		lowText.setText("-" + strVolt(hValue));
		lowLine.setStartX(lowText.getLayoutBounds().getMaxX());
		lowLine.setStartY(baseY - hValue * coefY);
		lowLine.setEndX(getWidth());
		lowLine.setEndY(baseY - hValue * coefY);

		int borne = nbDataUsed - 1;
		for (int ind = firstPassage, i = 0; i < borne; ind++, i++) {
			Line line = memLine.get(i);
			line.setStartX(coefX * i);
			line.setStartY(baseY + coefY * buf[ind]);
			line.setEndX(coefX * (i + 1));
			line.setEndY(baseY + coefY * buf[ind + 1]);

		}
		
		lastHValue = hValue;

	}
}
