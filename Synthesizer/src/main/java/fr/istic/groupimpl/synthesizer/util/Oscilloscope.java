package fr.istic.groupimpl.synthesizer.util;

import java.util.ArrayList;
import java.util.List;

import fr.istic.groupimpl.synthesizer.logger.Log;
import javafx.application.Platform;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class Oscilloscope extends Region {
	
	public static final int SIZE_BUFFER_READ=2048;
	final int nbDataUsed=1024;
	
	private boolean isRunning = false;

	private final GetBuffer cmdGetBuffer;
	private final long refreshPeriod;
	private Rectangle rectangle;

	private Thread refreshThread;

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

	public void start() {
		Log.getInstance().trace("Oscilloscope : start");
		isRunning = true;
		refreshThread.start();
	}

	public void stop() {
		Log.getInstance().trace("Oscilloscope : stop");
		if ( isRunning )
		{
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
	private double baseX;

	class EltComp {
		public EltComp(int indice, double val) {
			this.indice = indice;
			this.val = val;
			this.x = ((double) indice) * coefX;
			this.y = val * coefY;
		}

		final int indice;
		final double val;
		final double x;
		final double y;

	}

	List<EltComp> compress(double[] buf) {
		List<EltComp> result = new ArrayList<EltComp>();

		EltComp elt = null;

		result.add(elt = new EltComp(0, buf[0]));

		boolean flagBruit = false;

		double coef = Double.NaN;
		for (int i = 1; i < buf.length; i++) {
			double newCoef = (buf[i] - elt.val) / (i - elt.indice);

			if (coef == Double.NaN) {
				coef = newCoef;
				continue;
			}

			if (Math.abs(newCoef - coef) < 0.01) {
				coef = newCoef;
			} else {
				i--;
				result.add(elt = new EltComp(i, buf[i]));
				coef = Double.NaN;
				if (i > 60 && result.size() > i / 4) {
					flagBruit = true;
					break;
				}
			}
		}

		if (flagBruit) {
			result.clear();
			int dec = buf.length / 40;
			for (int i = 0; i < buf.length; i += dec) {
				result.add(new EltComp(i, buf[i]));
			}
		}

		return result;

	}

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
			middleLine = new Line(0,0,0,0);
			middleLine.setStroke(Color.WHITE);
			getChildren().add(middleLine);
			lowLine = new Line(0,0,0,0);
			lowLine.setStroke(Color.ORANGE);
			getChildren().add(lowLine);
			hightLine = new Line(0,0,0,0);
			hightLine.setStroke(Color.ORANGE);
			getChildren().add(hightLine);
			int borne = nbDataUsed-1;
			for (int i = 0; i < borne ; i++) {
				Line line;
				memLine.add(line = new Line(0, 0, 0, 0));
				line.setStroke(Color.GREEN);
				getChildren().add(line);
			}
			coefX = getWidth()/(nbDataUsed-1);
			
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
		if ( nbPassage < 2 )
			firstPassage = 0; 
		
		double hValue = Math.ceil(Math.max(Math.abs(valMax), Math.abs(valMin)));
		if ( hValue == 0. )
			hValue = 1.;
		coefY = -(getHeight()*0.45)/hValue;
		baseY = getHeight()/2.+10;
		baseX = 30;
		
		middleText.setText("0 V");
		middleText.setTranslateX(0);
		middleText.setTranslateY(baseY);		
		middleLine.setStartX(baseX);
		middleLine.setStartY(baseY);
		middleLine.setEndX(getWidth()+baseX);
		middleLine.setEndY(baseY);
		
		hightText.setTranslateX(0);
		hightText.setTranslateY(baseY+hValue*coefY);
		hightText.setText(""+((int)hValue)+" V");
		hightLine.setStartX(baseX);
		hightLine.setStartY(baseY+hValue*coefY);
		hightLine.setEndX(getWidth()+baseX);
		hightLine.setEndY(baseY+hValue*coefY);
		
		lowText.setTranslateX(0);
		lowText.setTranslateY(baseY-hValue*coefY);
		lowText.setText("-"+((int)hValue)+" V");
		lowLine.setStartX(baseX);
		lowLine.setStartY(baseY-hValue*coefY);
		lowLine.setEndX(getWidth()+baseX);
		lowLine.setEndY(baseY-hValue*coefY);
			
		int borne = nbDataUsed-1;
		for (int ind = firstPassage, i = 0; i < borne ; ind++, i++) {
			Line line = memLine.get(i);
			line.setStartX( coefX*i+baseX);
			line.setStartY(baseY + coefY* buf[ind]);
			line.setEndX(coefX* (i+1)+baseX);
			line.setEndY(baseY + coefY* buf[ind+1]);

		}

		//
		// List<EltComp> res = compress(buf);
		//
		// Log.getInstance().trace("buf.length="+buf.length+" res.size()="+res.size());
		//
		// for (Line l : memLine) {
		// l.setFill(Color.BLACK);
		// getChildren().remove(l);
		// }
		// Line line = null;
		// memLine.clear();
		// EltComp eltPres = null;
		// for (EltComp elt : res) {
		// if (eltPres != null) {
		// memLine.add(line = new Line(eltPres.x, eltPres.y, elt.x, elt.y));
		// getChildren().add(line);
		// }
		// eltPres = elt;
		// }
	}
}
