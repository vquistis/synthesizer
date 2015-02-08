package fr.istic.groupimpl.synthesizer.util;

import java.util.ArrayList;
import java.util.List;

import fr.istic.groupimpl.synthesizer.logger.Log;
import javafx.application.Platform;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;

public class Oscilloscope extends Region {
	private boolean isRunning=false;

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
//		rectangle = new Rectangle(0, 0, getWidth(), getHeight());
//		rectangle.setFill(Color.BLACK);

//		getChildren().add(rectangle);

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
				Platform.runLater(()->affBuf());
			}
		});
	}

	public void start()
	{
		Log.getInstance().trace("Oscilloscope : start");
		isRunning = true;
		refreshThread.start();
	}
	void stop() {
		isRunning = false;
	}

	private List<Line> memLine = new ArrayList<>();

	private double coefX;
	private double coefY;

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
		int inc = 2; 
		if ( memLine.size() == 0)
		{
			for ( int i = 0 ; i < 520 ; i++ )
			{
				Line line;
				memLine.add(line = new Line(0,0,0,0));
				line.setFill(Color.GREEN);
				getChildren().add(line);
			}
		}
		for( int ind = inc, i= 0 ; ind < buf.length ; ind += inc, i++ )
		{
			Line line = memLine.get(i);
			line.setStartX(0.4*(ind-inc));
			line.setStartY(100.+50.*buf[ind-inc]);
			line.setEndX(0.4*ind);
			line.setEndY(100.+50.*buf[ind]);
			
		}
		
//
//		List<EltComp> res = compress(buf);
//		
//		Log.getInstance().trace("buf.length="+buf.length+" res.size()="+res.size());
//
//		for (Line l : memLine) {
//			l.setFill(Color.BLACK);
//			getChildren().remove(l);
//		}
//		Line line = null;
//		memLine.clear();
//		EltComp eltPres = null;
//		for (EltComp elt : res) {
//			if (eltPres != null) {
//				memLine.add(line = new Line(eltPres.x, eltPres.y, elt.x, elt.y));
//				getChildren().add(line);
//			}
//			eltPres = elt;
//		}
	}
}
