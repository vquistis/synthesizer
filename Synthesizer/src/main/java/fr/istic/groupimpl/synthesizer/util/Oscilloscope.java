package fr.istic.groupimpl.synthesizer.util;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;

public class Oscilloscope extends Region {
	boolean isRunning;

	final GetBuffer cmdGetBuffer;
	final long refreshPeriod;
	Rectangle rectangle;

	Thread refreshThread;

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

		refreshThread = new Thread(() -> {

			while (isRunning) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					isRunning = false;
					Thread.currentThread().interrupt();
				}
				affBuf();
			}
		});
		refreshThread.start();
	}

	void stop() {
		isRunning = false;
	}

	private List<Line> memLine = new ArrayList<>();

	private double coefX;
	private double coefY;

	private class EltComp {
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

	private List<EltComp> compress(double[] buf) {
		List<EltComp> result = new ArrayList<EltComp>();

		EltComp elt = null;

		result.add(elt = new EltComp(0, buf[0]));

		boolean flagBruit = false;

		double coef = Double.NaN;
		for (int i = 0; i < buf.length; i++) {
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

		List<EltComp> res = compress(buf);

		for (Line l : memLine) {
			l.setFill(Color.BLACK);
			getChildren().remove(l);
		}
		Line line = null;
		memLine.clear();
		EltComp eltPres = null;
		for (EltComp elt : res) {
			if (eltPres != null) {
				memLine.add(line = new Line(eltPres.x, eltPres.y, elt.x, elt.y));
				getChildren().add(line);
			}
			eltPres = elt;
		}
	}
}
