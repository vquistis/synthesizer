package fr.istic.groupimpl.synthesizer.util;

import java.util.Date;

import fr.istic.groupimpl.synthesizer.logger.Log;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

/**
 * Implémentation d'un bouton tournant (knob) avec graduations paramétrables
 * 
 * To create a Potentiometre object, you must use PotentiometreFactory
 * 
 * Example :
 * 
 * PotentiometreFactory potentiometreFactory =
 * PotentiometreFactory.getFactoryInstance();
 * 
 **** initialisations on the factory object
 *
 * Potentiometre potentiometre = potentiometreFactory.getPotentiometre();
 * 
 * 
 * @author groupImpl
 *
 */
public class Potentiometre extends Region {

	final public static double RAYON_REF = 50.;

	private Region rgKnob = new Region();

	private Rotate rotate = new Rotate();
	private final Text title = new Text("");

	// valeur venant de initPotentiometre
	private final double rayon;
	private final double minValue;
	private final double maxValue;
	private final boolean discret;
	private final double nbSpins;

	private final boolean showTickMarks;
	private final boolean showTickLabels;
	private final double majorTickUnit;

	// angle en Radian
	private final double minAngle;
	private final double maxAngle;
	private final double valueDef;

	/**
	 * @return show Tick Marks flag
	 */
	public boolean isShowTickMarks() {
		return showTickMarks;
	}

	/**
	 * @return show Tick Labels flag
	 */
	public boolean isShowTickLabels() {
		return showTickLabels;
	}

	/**
	 * @return Major Tick Unit
	 */
	public double getMajorTickUnit() {
		return majorTickUnit;
	}

	// value interne differente de value que si discret
	private double val;

	/**
	 * Constructeur
	 */

	private double debDragAngle;
	boolean dragOK = false;

	private void traitePosAngle(double x, double y) {
		double centerX = getWidth() / 2.0;
		double centerY = getHeight() / 2.0;
		double ang = Math.atan2((y - centerY), (x - centerX));
		Log.getInstance().trace("traitePosAngle ang=" + ang);
		if (dragOK) {
			double delta = ang - debDragAngle;

			addValue(delta);
		}
		dragOK = true;
		debDragAngle = ang;

	}

	private long timeRelease = 0;

	Potentiometre(PotentiometreFactory initPot) {

		super();
		rayon = initPot.getRayon();
		minValue = initPot.getMinValue();
		maxValue = initPot.getMaxValue();
		discret = initPot.isDiscret();
		nbSpins = initPot.getNbSpins();
		minAngle = -Math.PI * nbSpins;
		maxAngle = Math.PI * nbSpins;
		valueDef = initPot.getValueDef();

		showTickMarks = initPot.isShowTickMarks();
		showTickLabels = initPot.isShowTickLabels();
		majorTickUnit = initPot.getMajorTickUnit();

		this.setPrefSize(2. * rayon, 2. * rayon);
		this.setShape(new Circle(2. * rayon));

		getStyleClass().add("button"); // NOI18N.
		rgKnob.setPrefSize(2. * RAYON_REF, 2. * RAYON_REF);
		rgKnob.getStyleClass().add("knob");
		rgKnob.getTransforms().add(rotate);
		rgKnob.setShape(new Circle(RAYON_REF));

		rgKnob.setScaleX(rayon / RAYON_REF);
		rgKnob.setScaleY(rayon / RAYON_REF);
		setOnDragOver((event) -> {
			traitePosAngle(event.getX(), event.getY());
			event.consume();
		});
		setOnDragDropped((event) -> {
			traitePosAngle(event.getX(), event.getY());
			event.consume();
		});

		setOnMousePressed((event) -> {
			Log.getInstance().debug(
					"event MousePressed x=" + event.getX() + " y="
							+ event.getY());
			dragOK = false;
			traitePosAngle(event.getX(), event.getY());
			event.consume();

		});
		setOnMouseDragged((event) -> {
			Log.getInstance().debug(
					"event MouseDragged x=" + event.getX() + " y="
							+ event.getY());
			traitePosAngle(event.getX(), event.getY());
			event.consume();

		});
		setOnMouseReleased((event) -> {
			Log.getInstance().debug(
					"event MouseReleased x=" + event.getX() + " y="
							+ event.getY());

			long t = (new Date()).getTime();

			if (t >= timeRelease && (t - timeRelease) < 500) {
				// double clic detecté
				Log.getInstance().debug("Double Clic");
				setValue(valueDef);
			} else {
				traitePosAngle(event.getX(), event.getY());
				dragOK = false;
			}
			timeRelease = t;
			event.consume();

		});
		setOnMouseDragExited((event) -> {
			Log.getInstance().debug(
					"event MouseDragExited x=" + event.getX() + " y="
							+ event.getY());
			traitePosAngle(event.getX(), event.getY());
			dragOK = false;
			event.consume();

		});
	    setOnScroll( (event)-> {
	    	double v = ((double)event.getDeltaY())/100.;
	    	Log.getInstance().debug(
					"event OnScroll deltaY="+event.getDeltaY()+" v="+v);
	        	addValue(v);
	        event.consume();
	    });


		this.title.setText(initPot.getTitle());
		getChildren().add(this.title);
		getChildren().add(rgKnob);
		valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0,
					Number arg1, Number arg2) {

				requestLayout();
			}
		});
		setValue(valueDef);

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

		double angle = valueToAngle(val);

		Log.getInstance().trace("1value=" + val);

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

		val = angleToValue(angle);

		if (isDiscret()) {
			if (discret(val) != getValue()) {
				value.set(discret(val));
			}
		} else {
			value.set(val);
		}

		Log.getInstance().trace("2value=" + getValue());

	}

	private double discret(double v) {
		return Math.floor(v + 0.5);
	}

	// rend un angle equivalent mais entre -Math.PI et Math.PI
	private double correctAngle( double angle )
	{
		double sens = Math.signum(angle);	
		int n = (int)Math.floor(Math.abs(angle)/Math.PI);
		
		n = (n+1)/2;
		
		double correct = -sens*((double)n)*2.*Math.PI;
				
		return angle + correct;		
				
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void layoutChildren() {
		super.layoutChildren();

		Log.getInstance().trace("layoutChildren()");
		double knobX = (getWidth() - rgKnob.getPrefWidth()) / 2.0;
		double knobY = (getHeight() - rgKnob.getPrefHeight()) / 2.0;
		rgKnob.setLayoutX(knobX);
		rgKnob.setLayoutY(knobY);
		double value = getValue();
		double angle = valueToAngle(value);
		affTickMarks();
		Log.getInstance().trace("value=" + value + " angle=" + angle);
		if (minAngle <= angle && angle <= maxAngle) {
			rotate.setPivotX(rgKnob.getWidth() / 2.0);
			rotate.setPivotY(rgKnob.getHeight() / 2.0);
//			double locAngle = (angle < -Math.PI) ? angle + 2. * Math.PI
//					: (angle > Math.PI) ? angle - 2. * Math.PI : angle;
			double locAngle = correctAngle(angle);
			Log.getInstance().debug("locAngle=" + locAngle);
			rotate.setAngle(180. * locAngle / Math.PI);
			val = value;
		}
	}

	private int nbAffTickMarks = 0;

	private void affTickMarks() {
		if (nbAffTickMarks++ > 1)
			return;

		double centerX = getWidth() / 2.0;
		double centerY = getHeight() / 2.0;

		if (isShowTickMarks()) {

			double rayonExt = rayon + rayon / 8.;

			for (double v = minValue; v <= maxValue; v += majorTickUnit) {

				double ang = valueToAngle(v) - Math.PI / 2.;
				double debX = Math.cos(ang) * rayon + centerX;
				double endX = Math.cos(ang) * rayonExt + centerX;
				double debY = Math.sin(ang) * rayon + centerY;
				double endY = Math.sin(ang) * rayonExt + centerY;
				Line line = new Line();
				getChildren().add(line);
				line.setStartX(debX);
				line.setEndX(endX);
				line.setStartY(debY);
				line.setEndY(endY);
				Log.getInstance().trace("TRACE1");
				if (isShowTickLabels()) {
					Log.getInstance().trace("TRACE2");
					Text label = null;
					if (Math.floor(majorTickUnit) == majorTickUnit)
						label = new Text("" + (int) v);
					else
						label = new Text("" + v);
					getChildren().add(label);
					double d = 10;

					double x = Math.cos(ang) * (rayonExt + d) - 5 + centerX;
					double y = Math.sin(ang) * (rayonExt + d) + 5 + centerY;
					label.setTranslateX(x);
					label.setTranslateY(y);

				}

			}
		}
	}

	private double valueToAngle(double value) {
		double angle = minAngle + (maxAngle - minAngle) * (value - minValue)
				/ (maxValue - minValue);
		return angle;
	}

	private double angleToValue(double angle) {
		double value = minValue + (maxValue - minValue) * (angle - minAngle)
				/ (maxAngle - minAngle);
		value = Math.max(minValue, value);
		value = Math.min(maxValue, value);
		return value;
	}

	private final DoubleProperty value = new SimpleDoubleProperty(this,
			"value", 0);

	/**
	 * set the value
	 * 
	 * @param v
	 *            Value for initialization
	 */
	public final void setValue(double v) {
		value.set(v);
		val = v;
	}

	/**
	 * get the value
	 * 
	 * @return value
	 */
	public final double getValue() {
		return value.get();
	}

	/**
	 * @return the value Property
	 */
	public final DoubleProperty valueProperty() {
		return value;
	}

	/**
	 * get the minimum value
	 * 
	 * @return the minimum value
	 */
	public final double getMin() {
		return minValue;
	}

	/**
	 * get the maximum value
	 * 
	 * @return the maximum value
	 */
	public final double getMax() {
		return maxValue;
	}

	/**
	 * get the discret flag ( to have only integer values )
	 * 
	 * @return the discret flag
	 */
	public final boolean isDiscret() {
		return discret;
	}

	/**
	 * get the number of spins
	 * 
	 * @return The spin number
	 */
	public double getNbSpins() {
		return nbSpins;
	}

}
