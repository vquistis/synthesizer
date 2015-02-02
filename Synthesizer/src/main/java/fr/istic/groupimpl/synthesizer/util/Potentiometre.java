package fr.istic.groupimpl.synthesizer.util;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

import org.apache.logging.log4j.LogManager;

import fr.istic.groupimpl.synthesizer.logger.Log;

/**
 * Implémentation d'un bouton tournant (knob) avec graduations paramétrables
 * 
 * @author groupImpl
 *
 */
public class Potentiometre extends Region {

	private Region rgKnob = new Region();

	// angle en Radian 
	private final double minAngle = -3. * Math.PI / 2.;
	private final double maxAngle = Math.PI / 2.;
	private Rotate rotate = new Rotate();
//	private Line minLine = new Line();
//	private Line maxLine = new Line();
	// private Text text = new Text("");
	private Text title = new Text("");

	/**
	 * Constructeur
	 */

	private double debDragAngle;
	boolean dragOK = false;

	public Potentiometre(String title) {
		super();

		this.setPrefSize(200, 200);

		getStyleClass().add("button"); // NOI18N.
		rgKnob.setPrefSize(100, 100);
		rgKnob.getStyleClass().add("knob"); // NOI18N.
		rgKnob.getTransforms().add(rotate);
		rgKnob.setShape(new Circle(50));

		if (true) {
			setOnMousePressed((event) -> {
				Log.getInstance().trace("event OnMousePressed x="
						+ event.getX() + " y=" + event.getY());
				double x = event.getX();
				double y = event.getY();
				double centerX = getWidth() / 2.0;
				double centerY = getHeight() / 2.0;
				debDragAngle = Math.atan2((y - centerY), (x - centerX));
				dragOK = true;
				event.consume();
			});
			setOnMouseReleased((event) -> {
				Log.getInstance().trace("event MouseReleased x="
						+ event.getX() + " y=" + event.getY());

				if (!dragOK) {
					event.consume();
					return;
				}
				double x = event.getX();
				double y = event.getY();
				double centerX = getWidth() / 2.0;
				double centerY = getHeight() / 2.0;
				double delta = Math.atan2((y - centerY), (x - centerX))-debDragAngle;

				addValue(-delta);
				
				Log.getInstance().trace("event MouseReleased delta="+delta
						+ " value=" + value.get());

				dragOK = false;
				event.consume();

			});
		} else {

			setOnMouseMoved(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					double x = event.getX();
					double y = event.getY();
					double centerX = getWidth() / 2.0;
					double centerY = getHeight() / 2.0;
					// currentLine.setStartX(centerX);
					// currentLine.setStartY(centerY);
					// currentLine.setEndX(x);
					// currentLine.setEndY(y);
				}
			});
			setOnMouseDragged(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					double x = event.getX();
					double y = event.getY();
					double centerX = getWidth() / 2.0;
					double centerY = getHeight() / 2.0;
					// currentLine.setStartX(centerX);
					// currentLine.setStartY(centerY);
					// currentLine.setEndX(x);
					// currentLine.setEndY(y);
					double theta = Math.atan2((y - centerY), (x - centerX));

					LogManager.getLogger().debug("theta=" + theta);

					double angle = Math.toDegrees(theta);
					if (angle > 0.0) {
						angle = 180 + (180 - angle);
					} else {
						angle = 180 - (180 - Math.abs(angle));
					}
					if (angle >= 270) {
						angle = angle - 360;
					}
					double value = angleToValue(angle);
					// text.setText(MessageFormat.format("{0}\n{1}", angle,
					// value));
					setValue(value);
				}
			});
		}
//		minLine.setStroke(Color.GREEN);
//		maxLine.setStroke(Color.BLUE);
		// text.setTextOrigin(VPos.TOP);
		this.title.setText(title);
		getChildren().add(this.title);
//		getChildren().addAll(minLine, maxLine);
		getChildren().add(rgKnob);
		// getChildren().addAll(text);
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

		Log.getInstance().trace("1deltaAngle="+deltaAngle);
		if (deltaAngle > Math.PI) {
			deltaAngle -= 2. * Math.PI;
		} else {
			if (deltaAngle < -Math.PI) {
				deltaAngle += 2. * Math.PI;
			}
		}
		Log.getInstance().trace("2deltaAngle="+deltaAngle);

		double angle = valueToAngle( value.get() );

		Log.getInstance().trace("1value="+getValue());
		
		Log.getInstance().trace("1angle="+angle);
		
		angle += deltaAngle;
		if ( angle < minAngle )
		{
			angle = minAngle;
		}else
		{
			if ( angle > maxAngle )
			{
				angle = maxAngle;
			}
			
		}
		Log.getInstance().trace("2angle="+angle);

		value.set( angleToValue( angle ) );
		
		Log.getInstance().trace("2value="+getValue());	
		
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
		// currentLine.setStartX(centerX);
		// currentLine.setStartY(centerY);
//		minLine.setStartX(centerX);
//		minLine.setStartY(centerY);
//		minLine.setEndX(centerX +Math.cos(minAngle);
//		minLine.setEndY(centerY + 10 + 90 * Math.sin(Math.toRadians(-minAngle)));
//		maxLine.setStartX(centerX);
//		maxLine.setStartY(centerY);
//		maxLine.setEndX(centerX + 90 * Math.cos(Math.toRadians(-maxAngle)));
//		maxLine.setEndY(centerY + 90 * Math.sin(Math.toRadians(-maxAngle)));
		double knobX = (getWidth() - rgKnob.getPrefWidth()) / 2.0;
		double knobY = (getHeight() - rgKnob.getPrefHeight()) / 2.0;
		rgKnob.setLayoutX(knobX);
		rgKnob.setLayoutY(knobY);
		double value = getValue();
		
		double angle = valueToAngle(getValue());
		
		Log.getInstance().trace("value="+value+" angle="+angle);
		if (minAngle <= angle && angle <= maxAngle) {
			rotate.setPivotX(rgKnob.getWidth() / 2.0);
			rotate.setPivotY(rgKnob.getHeight() / 2.0);
			double locAngle=(angle < Math.PI )?angle+2.*Math.PI
					:(angle > Math.PI )?angle-2.*Math.PI:angle;
			Log.getInstance().trace("locAngle="+locAngle);
			rotate.setAngle(locAngle);
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

	private final DoubleProperty min = new SimpleDoubleProperty(this, "min", 0); // NOI18N.

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

}
