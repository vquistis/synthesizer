package view;

import java.text.MessageFormat;

import javafx.beans.property.DoubleProperty;
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
 * Implémentation d'un bouton tournant (knod)
 * avec graduations paramétrables
 * 
 * @author groupImpl
 *
 */
public class Potentiometre extends Region {

	private Region rg_knob = new Region(); 
		
	private final double minAngle = -20;
	private final double maxAngle = 200;
	private Rotate rotate = new Rotate();
	private Line minLine = new Line();
	private Line maxLine = new Line();
	private Text text = new Text("");
	private Text title = new Text("");

	/**
	 * Constructeur
	 */
	public Potentiometre(String str) {
		super();

		this.setPrefSize(200, 200);
		
		getStyleClass().add("button"); // NOI18N.
		rg_knob.setPrefSize(100, 100);
		rg_knob.getStyleClass().add("knob"); // NOI18N.
		rg_knob.getTransforms().add(rotate);
		rg_knob.setShape(new Circle(50));
		
		setOnMouseMoved(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				double x = event.getX();
				double y = event.getY();
				double centerX = getWidth() / 2.0;
				double centerY = getHeight() / 2.0;
//				currentLine.setStartX(centerX);
//				currentLine.setStartY(centerY);
//				currentLine.setEndX(x);
//				currentLine.setEndY(y);
			}
		});
		setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				double x = event.getX();
				double y = event.getY();
				double centerX = getWidth() / 2.0;
				double centerY = getHeight() / 2.0;
//				currentLine.setStartX(centerX);
//				currentLine.setStartY(centerY);
//				currentLine.setEndX(x);
//				currentLine.setEndY(y);
				double theta = Math.atan2((y - centerY), (x - centerX));
				double angle = Math.toDegrees(theta);
				if (angle > 0.0) {
					angle = 180 + (180 - angle);
				} else {
					angle = 180 - (180 - Math.abs(angle));
				}
				if (angle >= 270) {
					angle =  angle - 360;
				}
				double value = angleToValue(angle);
				text.setText(MessageFormat.format("{0}\n{1}", angle, value));
				setValue(value);
			}
		});
		
		minLine.setStroke(Color.GREEN);
		maxLine.setStroke(Color.BLUE);
		text.setTextOrigin(VPos.TOP);
		title.setText(str);
		getChildren().add(title);
		getChildren().addAll(minLine, maxLine);
		getChildren().add(rg_knob);
	 	getChildren().addAll(text);
		setPrefSize(100, 100);
		valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				requestLayout();
			}
		});
		minProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				requestLayout();
			}
		});
		maxProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				requestLayout();
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void layoutChildren() {
		super.layoutChildren();
		double centerX = getWidth() / 2.0;
		double centerY = getHeight() / 2.0;
//		currentLine.setStartX(centerX);
//		currentLine.setStartY(centerY);
		minLine.setStartX(centerX);
		minLine.setStartY(centerY);
		minLine.setEndX(centerX + 10 + 90 * Math.cos(Math.toRadians(-minAngle)));
		minLine.setEndY(centerY + 10 + 90 * Math.sin(Math.toRadians(-minAngle)));
		maxLine.setStartX(centerX);
		maxLine.setStartY(centerY);
		maxLine.setEndX(centerX + 90 * Math.cos(Math.toRadians(-maxAngle)));
		maxLine.setEndY(centerY + 90 * Math.sin(Math.toRadians(-maxAngle)));
		double knobX = (getWidth() - rg_knob.getPrefWidth()) / 2.0;
		double knobY = (getHeight() - rg_knob.getPrefHeight()) / 2.0;
		rg_knob.setLayoutX(knobX);
		rg_knob.setLayoutY(knobY);
		double value = getValue();
		double angle = valueToAngle(getValue());
		if (minAngle <= angle && angle <= maxAngle) {
			rotate.setPivotX(rg_knob.getWidth() / 2.0);
			rotate.setPivotY(rg_knob.getHeight() / 2.0);
			rotate.setAngle(-angle);
		}
	}

	double valueToAngle(double value) {
		double maxValue = getMax();
		double minValue = getMin();
		double angle = minAngle + (maxAngle - minAngle) * (value - minValue) / (maxValue - minValue);
		System.out.printf("valueToAngle %f => %f", value, angle).println();
		return angle;
	}

	double angleToValue(double angle) {
		double maxValue = getMax();
		double minValue = getMin();
		double value = minValue + (maxValue - minValue) * (angle - minAngle) / (maxAngle - minAngle);
		value = Math.max(minValue, value);
		value = Math.min(maxValue, value);
		System.out.printf("angleToValue %f => %f", angle, value).println();
		return value;
	}
	 
	private final DoubleProperty value = new SimpleDoubleProperty(this, "value", 0); // NOI18N.

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
	private final DoubleProperty max = new SimpleDoubleProperty(this, "max", 100); // NOI18N.

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
