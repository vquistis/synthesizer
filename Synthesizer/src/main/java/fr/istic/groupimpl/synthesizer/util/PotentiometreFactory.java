package fr.istic.groupimpl.synthesizer.util;

/**
 *  The Class PotentiometreFactory
 *
 *    The default values are :
 *         private String title = ""
 *         private double rayon = Potentiometre.RAYON_REF
 *         private double minValue = 0
 *         private double maxValue = 100
 *         private boolean discret = false
 *         private double nbSpins = 1
 *         private boolean showTickMarks = false
 *         private boolean showTickLabels = false
 *         private double majorTickUnit = 5
 *
 * @author Team GroupImpl
 *
 */
public class PotentiometreFactory {

	/**
	 * Constructor.
	 */
	private PotentiometreFactory() {
	}

	/**
	 * Gets the factory instance.
	 *
	 * @return the factory instance
	 */
	static public PotentiometreFactory getFactoryInstance() {
		return new PotentiometreFactory();
	}

	/**
	 * Gets the potentiometre.
	 *
	 * @return the potentiometre
	 */
	public Potentiometre getPotentiometre() {
		return new Potentiometre(this);
	}

	/** The title. */
	private String title = "";
	
	/** The rayon. */
	private double rayon = Potentiometre.RAYON_REF;
	
	/** The min value. */
	private double minValue = 0.;
	
	/** The max value. */
	private double maxValue = 100.;
	
	/** The discret. */
	private boolean discret = false;
	
	/** The nb spins. */
	private double nbSpins = 1.;
	
	/** The value def. */
	private double valueDef;
	
	/** The value def init. */
	private boolean valueDefInit;

	/** The show tick marks. */
	private boolean showTickMarks = false;
	
	/** The show tick labels. */
	private boolean showTickLabels = false;
	
	/** The major tick unit. */
	private double majorTickUnit = 5.;
	
	/** The minor tick unit. */
	private double minorTickUnit = 5.;
	
	/** The minor tick unit init. */
	private boolean minorTickUnitInit = false;

	/**
	 * Gets the minor tick unit.
	 *
	 * @return the minor tick unit
	 */
	public double getMinorTickUnit() {
		if( minorTickUnitInit )
			return minorTickUnit;
		else
			return majorTickUnit;
	}

	/**
	 * Sets the minor tick unit.
	 *
	 * @param minorTickUnit the new minor tick unit
	 */
	public void setMinorTickUnit(double minorTickUnit) {
		this.minorTickUnit = minorTickUnit;
		minorTickUnitInit = true;
	}

	/**
	 * get the minimum value.
	 *
	 * @return the minimum value
	 */
	public double getMinValue() {
		return minValue;
	}

	/**
	 * set the minimum value.
	 *
	 * @param minValue            the minimum value
	 */
	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	/**
	 * get the maximum value.
	 *
	 * @return the maximum value
	 */
	public double getMaxValue() {
		return maxValue;
	}

	/**
	 * set the maximum value.
	 *
	 * @param maxValue            the maximum value
	 */
	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	/**
	 * get the discret flag ( the discret flag allowed to have only integer
	 * values).
	 *
	 * @return the discret flag
	 */
	public boolean isDiscret() {
		return discret;
	}

	/**
	 * set the discret flag ( the discret flag allowed to have only integer
	 * values).
	 *
	 * @param discret the new discret
	 */
	public void setDiscret(boolean discret) {
		this.discret = discret;
	}

	/**
	 * get the number of permit spins.
	 *
	 * @return the number of permits spins
	 */
	public double getNbSpins() {
		return nbSpins;
	}

	/**
	 * set the number of permit spins.
	 *
	 * @param nbSpins            the number of permits spins
	 */
	public void setNbSpins(double nbSpins) {
		this.nbSpins = nbSpins;
	}

	/**
	 * get the default value, if this value is not initialize then the average
	 * between min and max values is the default value.
	 * 
	 * @return default value
	 */
	public double getValueDef() {
		if (valueDefInit)
			return valueDef;
		else {
			double v = (minValue + maxValue) / 2.;
			if (isDiscret()) {
				return Math.floor(v + 0.5);
			} else {
				return v;
			}
		}
	}

	/**
	 * set the default value.
	 *
	 * @param valueDef the new value def
	 */
	public void setValueDef(double valueDef) {
		this.valueDef = valueDef;
		valueDefInit = true;
	}

	/**
	 * get the show tick marks flag.
	 *
	 * @return the show tick marks flag
	 */
	public boolean isShowTickMarks() {
		return showTickMarks;
	}

	/**
	 * set the show tick marks flag.
	 *
	 * @param showTickMarks            the show tick marks flag
	 */
	public void setShowTickMarks(boolean showTickMarks) {
		this.showTickMarks = showTickMarks;
	}

	/**
	 * get the show tick labels flag.
	 *
	 * @return the show tick labels flag
	 */
	public boolean isShowTickLabels() {
		return showTickLabels;
	}

	/**
	 * set the show tick labels flag.
	 *
	 * @param showTickLabels            the show tick labels flag
	 */
	public void setShowTickLabels(boolean showTickLabels) {
		this.showTickLabels = showTickLabels;
	}

	/**
	 * get the major tick unit.
	 *
	 * @return the major tick unit
	 */
	public double getMajorTickUnit() {
		return majorTickUnit;
	}

	/**
	 * set the major tick unit.
	 *
	 * @param majorTickUnit            the major tick unit
	 */
	public void setMajorTickUnit(double majorTickUnit) {
		this.majorTickUnit = majorTickUnit;
	}

	/**
	 * get the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * set the title.
	 *
	 * @param title            the title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * get the rayon.
	 *
	 * @return the rayon
	 */
	public double getRayon() {
		return rayon;
	}

	/**
	 * set the rayon.
	 *
	 * @param rayon            the rayon
	 */
	public void setRayon(double rayon) {
		this.rayon = rayon;
	}

}
