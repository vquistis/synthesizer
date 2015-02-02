package fr.istic.groupimpl.synthesizer.util;

import java.text.MessageFormat;

import org.apache.logging.log4j.LogManager;

import fr.istic.groupimpl.synthesizer.logger.Log;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;


/**
 * Implémentation d'un bouton tournant (knob) avec graduations paramétrables
 * 
 * @author groupImpl
 *
 */
public class Potentiometre extends Region {

	private Region rgKnob = new Region();

	// angle en Radian
	private final double minAngle = -Math.PI;
	private final double maxAngle = Math.PI;
	private Rotate rotate = new Rotate();
	private Text title = new Text("");

	/**
	 * Constructeur
	 */

	private double debDragAngle;
	boolean dragOK = false;

	private void memPosAngle(double x, double y) {
		double centerX = getWidth() / 2.0;
		double centerY = getHeight() / 2.0;
		debDragAngle = Math.atan2((y - centerY), (x - centerX));
		dragOK = true;

	}

	private void traitePosAngle(double x, double y) {
		double centerX = getWidth() / 2.0;
		double centerY = getHeight() / 2.0;
		double ang = Math.atan2((y - centerY), (x - centerX));
		double delta = ang - debDragAngle;
		debDragAngle = ang;

		addValue(delta);
	}

	public Potentiometre(String title) {
		super();

		this.setPrefSize(200, 200);

		getStyleClass().add("button"); // NOI18N.
		rgKnob.setPrefSize(100, 100);
		rgKnob.getStyleClass().add("knob"); // NOI18N.
		rgKnob.getTransforms().add(rotate);
		rgKnob.setShape(new Circle(50));

		setOnDragDetected((event) -> {
			Log.getInstance().debug(
					"event OnDragDetected x=" + event.getX() + " y="
							+ event.getY());
			startFullDrag();
			dragOK = true;
			memPosAngle(event.getX(), event.getY());

			event.consume();
		});

		setOnMouseDragged((event) -> {
			Log.getInstance().debug(
					"event MouseDragged x=" + event.getX() + " y="
							+ event.getY());

			if (dragOK) {
				traitePosAngle(event.getX(), event.getY());
			} else {
				memPosAngle(event.getX(), event.getY());
				dragOK = true;
			}
			event.consume();

		});
		setOnDragDone((event) -> {
			Log.getInstance().trace(
					"event DragDone x=" + event.getX() + " y=" + event.getY());

			if (dragOK) {
				traitePosAngle(event.getX(), event.getY());
			}
			dragOK = false;
			event.consume();

		});
		this.title.setText(title);
		getChildren().add(this.title);
		getChildren().add(rgKnob);
		setPrefSize(100, 100);
		valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0,
					Number arg1, Number arg2) {

				requestLayout();
			}
		});
		minProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0,
					Number arg1, Number arg2) {
				requestLayout();
			}
		});
		maxProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0,
					Number arg1, Number arg2) {
				requestLayout();
			}
		});
	}

	private void addValue(double deltaAngle) {

		Log.getInstance().trace("1deltaAngle=" + deltaAngle);
		if (deltaAngle > Math.PI) {
			deltaAngle -= 2. * Math.PI;
		} else {
			if (deltaAngle < -Math.PI) {
				deltaAngle += 2. * Math.PI;
			}
		}
		Log.getInstance().trace("2deltaAngle=" + deltaAngle);

		double angle = valueToAngle(value.get());

		Log.getInstance().trace("1value=" + getValue());

		Log.getInstance().trace("1angle=" + angle);

		angle += deltaAngle;
		if (angle < minAngle) {
			angle = minAngle;
		} else {
			if (angle > maxAngle) {
				angle = maxAngle;
			}

		}
		Log.getInstance().trace("2angle=" + angle);

		value.set(angleToValue(angle));

		Log.getInstance().trace("2value=" + getValue());

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void layoutChildren() {
		super.layoutChildren();

		Log.getInstance().trace("layoutChildren()");
		double centerX = getWidth() / 2.0;
		double centerY = getHeight() / 2.0;
		double knobX = (getWidth() - rgKnob.getPrefWidth()) / 2.0;
		double knobY = (getHeight() - rgKnob.getPrefHeight()) / 2.0;
		rgKnob.setLayoutX(knobX);
		rgKnob.setLayoutY(knobY);
		double value = getValue();

		if (isDiscret()) {
			value = Math.floor(value + 0.5);
		}

		double angle = valueToAngle(value);

		Log.getInstance().trace("value=" + value + " angle=" + angle);
		if (minAngle <= angle && angle <= maxAngle) {
			rotate.setPivotX(rgKnob.getWidth() / 2.0);
			rotate.setPivotY(rgKnob.getHeight() / 2.0);
			double locAngle = (angle < Math.PI) ? angle + 2. * Math.PI
					: (angle > Math.PI) ? angle - 2. * Math.PI : angle;
			Log.getInstance().debug("locAngle=" + locAngle);
			rotate.setAngle(180. * locAngle / Math.PI);
		}
	}

	double valueToAngle(double value) {
		double maxValue = getMax();
		double minValue = getMin();
		double angle = minAngle + (maxAngle - minAngle) * (value - minValue)
				/ (maxValue - minValue);
		// System.out.printf("valueToAngle %f => %f", value, angle).println();
		return angle;
	}

	double angleToValue(double angle) {
		double maxValue = getMax();
		double minValue = getMin();
		double value = minValue + (maxValue - minValue) * (angle - minAngle)
				/ (maxAngle - minAngle);
		value = Math.max(minValue, value);
		value = Math.min(maxValue, value);
		// System.out.printf("angleToValue %f => %f", angle, value).println();
		return value;
	}

	private final DoubleProperty value = new SimpleDoubleProperty(this,
			"value", 0); // NOI18N.

	public final void setValue(double v) {
		value.set(v);
	}

	public final double getValue() {
		return value.get();
	}

	public final DoubleProperty valueProperty() {
		return value;
	}

	private final DoubleProperty min = new SimpleDoubleProperty(this, "min",
			-100); // NOI18N.

	public final void setMin(double v) {
		min.set(v);
	}

	public final double getMin() {
		return min.get();
	}

	public final DoubleProperty minProperty() {
		return min;
	}

	private final DoubleProperty max = new SimpleDoubleProperty(this, "max",
			100); // NOI18N.

	public final void setMax(double v) {
		max.set(v);
	}

	public final double getMax() {
		return max.get();
	}

	public final DoubleProperty maxProperty() {
		return max;
	}

	public void setTitle(Text title) {
		this.title = title;
	}

	private final BooleanProperty discret = new SimpleBooleanProperty(this,
			"discret", false);

	public final void setDiscret(boolean d) {
		discret.set(d);
	}

	public final boolean isDiscret() {
		return discret.get();
	}

	public final BooleanProperty discretProperty() {
		return discret;
	}

}
