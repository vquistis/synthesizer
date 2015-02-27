package fr.istic.groupimpl.synthesizer.util;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import fr.istic.groupimpl.synthesizer.logger.Log;

/**
 * Implementation of knob with configurable graduations
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

	/** The Constant RAYON_REF. */
	final public static double RAYON_REF = 50.;

	/** The rg knob. */
	private Region rgKnob = new Region();

	/** The rotate. */
	private Rotate rotate = new Rotate();
	
	/** The title. */
	private final Text title = new Text("");

	// valeur venant de initPotentiometre
	/** The rayon. */
	private final double rayon;
	
	/** The min value. */
	private final double minValue;
	
	/** The max value. */
	private final double maxValue;
	
	/** The discret. */
	private final boolean discret;
	
	/** The nb spins. */
	private final double nbSpins;

	/** The show tick marks. */
	private final boolean showTickMarks;
	
	/** The show tick labels. */
	private final boolean showTickLabels;
	
	/** The major tick unit. */
	private final double majorTickUnit;
	
	/** The minor tick unit. */
	private final double minorTickUnit;

	// angle en Radian
	/** The min angle. */
	private final double minAngle;
	
	/** The max angle. */
	private final double maxAngle;
	
	/** The value def. */
	private final double valueDef;

	/**
	 * Checks if is show tick marks.
	 *
	 * @return show Tick Marks flag
	 */
	public boolean isShowTickMarks() {
		return showTickMarks;
	}

	/**
	 * Checks if is show tick labels.
	 *
	 * @return show Tick Labels flag
	 */
	public boolean isShowTickLabels() {
		return showTickLabels;
	}

	/**
	 * Gets the major tick unit.
	 *
	 * @return Major Tick Unit
	 */
	public double getMajorTickUnit() {
		return majorTickUnit;
	}

	// intern value is different from value only if it is discrete
	/** The val. */
	private double val;

	/** The deb drag angle. */
	private double debDragAngle;
	
	/** The drag ok. */
	boolean dragOK = false;
	
	/** The right button pressed. */
	boolean rightButtonPressed=false;

	/**
	 * Traite pos angle.
	 *
	 * @param x the x
	 * @param y the y
	 */
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

	/** The time release. */
	private long timeRelease = 0;

	/** The drag valid. */
	private boolean dragValid = false;


	/**
	 * Constructor.
	 *
	 * @param initPot the init pot
	 */
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
		minorTickUnit = initPot.getMinorTickUnit();

		this.setPrefSize(2. * rayon, 2. * rayon);
		
		this.setShape(new Circle(rayon));

		getStyleClass().add("knobButton"); // NOI18N.
		rgKnob.setPrefSize(2. * RAYON_REF, 2. * RAYON_REF);
		rgKnob.getStyleClass().add("knob");
		rgKnob.getTransforms().add(rotate);
		rgKnob.setShape(new Circle(RAYON_REF));

		rgKnob.setScaleX(rayon / RAYON_REF);
		rgKnob.setScaleY(rayon / RAYON_REF);
		setOnDragDetected((event) -> {
			event.consume();
		});
		setOnDragOver((event) -> {
			if (dragValid) {
				traitePosAngle(event.getX(), event.getY());
			}
			event.consume();
		});
		setOnDragDropped((event) -> {
			if (dragValid) {
				traitePosAngle(event.getX(), event.getY());
			}
			event.consume();
		});

		setOnMousePressed((event) -> {
			Log.getInstance().debug(
					"event MousePressed x=" + event.getX() + " y="
							+ event.getY());
			if ( event.getButton() == MouseButton.SECONDARY )
			{
				rightButtonPressed = true;
				event.consume();
				return;
			}
			dragValid = true;
			dragOK = false;
			if ( event.getButton() == MouseButton.SECONDARY )
			{
				rightButtonPressed = true;
			}
			traitePosAngle(event.getX(), event.getY());
			event.consume();

		});
		setOnMouseDragged((event) -> {
			Log.getInstance().debug(
					"event MouseDragged x=" + event.getX() + " y="
							+ event.getY());
			if (dragValid) {
				traitePosAngle(event.getX(), event.getY());
			}
			event.consume();

		});
		setOnMouseReleased((event) -> {
			Log.getInstance().debug(
					"event MouseReleased x=" + event.getX() + " y="
							+ event.getY());
			if ( event.getButton() == MouseButton.SECONDARY )
			{
				rightButtonPressed = false;
				event.consume();
				return;
			}

			long t = (new Date()).getTime();

			if (t >= timeRelease && (t - timeRelease) < 500) {
				// double clic detecté
				Log.getInstance().debug("Double Clic");
				setValue(valueDef);
			} else {
				if (dragValid) {
					traitePosAngle(event.getX(), event.getY());
					dragOK = false;
				}
			}
			timeRelease = t;
			dragValid = false;
			event.consume();

		});
		setOnMouseDragExited((event) -> {
			Log.getInstance().debug(
					"event MouseDragExited x=" + event.getX() + " y="
							+ event.getY());
			if (dragValid) {
				traitePosAngle(event.getX(), event.getY());
				dragOK = false;
				dragValid = false;
			}
			event.consume();

		});
		setOnScroll((event) -> {
			double v = ((double) event.getDeltaY()) / 500.;
			Log.getInstance().debug(
					"event OnScroll deltaY=" + event.getDeltaY() + " v=" + v);
			if ( rightButtonPressed )
			{
				v /= 10.;
			}
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

	/**
	 * Adds the value.
	 *
	 * @param deltaAngle the delta angle
	 */
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

	/**
	 * Discret.
	 *
	 * @param v the v
	 * @return the double
	 */
	private double discret(double v) {
		return Math.floor(v + 0.5);
	}

	// makes an equivalent angle between -Math.PI and Math.PI
	/**
	 * Correct angle.
	 *
	 * @param angle the angle
	 * @return the double
	 */
	private double correctAngle(double angle) {
		double sens = Math.signum(angle);
		int n = (int) Math.floor(Math.abs(angle) / Math.PI);

		n = (n + 1) / 2;

		double correct = -sens * ((double) n) * 2. * Math.PI;

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
			// double locAngle = (angle < -Math.PI) ? angle + 2. * Math.PI
			// : (angle > Math.PI) ? angle - 2. * Math.PI : angle;
			double locAngle = correctAngle(angle);
			Log.getInstance().debug("locAngle=" + locAngle);
			rotate.setAngle(180. * locAngle / Math.PI);
			val = value;
		}
	}

	/** The flag aff tick marks. */
	private AtomicBoolean flagAffTickMarks = new AtomicBoolean(false);

	/**
	 * Aff tick marks.
	 */
	private void affTickMarks() {

		if (flagAffTickMarks.getAndSet(true))
			return;

		double centerX = getWidth() / 2.0;
		double centerY = getHeight() / 2.0;

		if (isShowTickMarks()) {

			double rayonExt = rayon + rayon / 8.;
			double rayonExt2 = rayon + rayon / 12.;
			int nbmtu = 1;
			if (majorTickUnit != minorTickUnit) {
				nbmtu = (int) ((majorTickUnit + minorTickUnit / 2.) / minorTickUnit);
			}
			int nb = 0;
			for (double v = minValue; v <= (maxValue + minorTickUnit / 2.); v += minorTickUnit, nb++) {

				double ang = valueToAngle(v) - Math.PI / 2.;
				double debX = Math.cos(ang) * rayon + centerX;
				double debY = Math.sin(ang) * rayon + centerY;
				double endX = Math.cos(ang) * rayonExt + centerX;
				double endY = Math.sin(ang) * rayonExt + centerY;
				if (nb % nbmtu != 0) {
					endX = Math.cos(ang) * rayonExt2 + centerX;
					endY = Math.sin(ang) * rayonExt2 + centerY;
				}
				Line line = new Line();
				getChildren().add(line);
				line.setStartX(debX);
				line.setEndX(endX);
				line.setStartY(debY);
				line.setEndY(endY);
				Log.getInstance().trace("TRACE1");
				if (isShowTickLabels() && nb % nbmtu == 0) {
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

	/**
	 * Value to angle.
	 *
	 * @param value the value
	 * @return the double
	 */
	private double valueToAngle(double value) {
		double angle = minAngle + (maxAngle - minAngle) * (value - minValue)
				/ (maxValue - minValue);
		return angle;
	}

	/**
	 * Angle to value.
	 *
	 * @param angle the angle
	 * @return the double
	 */
	private double angleToValue(double angle) {
		double value = minValue + (maxValue - minValue) * (angle - minAngle)
				/ (maxAngle - minAngle);
		value = Math.max(minValue, value);
		value = Math.min(maxValue, value);
		return value;
	}

	/** The value. */
	private final DoubleProperty value = new SimpleDoubleProperty(this,
			"value", 0);

	/**
	 * set the value.
	 *
	 * @param v            Value for initialization
	 */
	public final void setValue(double v) {
		value.set(v);
		val = v;
	}

	/**
	 * get the value.
	 *
	 * @return value
	 */
	public final double getValue() {
		return value.get();
	}

	/**
	 * Value property.
	 *
	 * @return the value Property
	 */
	public final DoubleProperty valueProperty() {
		return value;
	}

	/**
	 * get the minimum value.
	 *
	 * @return the minimum value
	 */
	public final double getMin() {
		return minValue;
	}

	/**
	 * get the maximum value.
	 *
	 * @return the maximum value
	 */
	public final double getMax() {
		return maxValue;
	}

	/**
	 * get the discret flag ( to have only integer values ).
	 *
	 * @return the discret flag
	 */
	public final boolean isDiscret() {
		return discret;
	}

	/**
	 * get the number of spins.
	 *
	 * @return The spin number
	 */
	public double getNbSpins() {
		return nbSpins;
	}

}
