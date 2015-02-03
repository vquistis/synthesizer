package fr.istic.groupimpl.synthesizer.util;

/**
 * @author Yves Mocquard
 *
 *         Classe d'initialisation du potentiometre the default values are :
 *         private String title = ""; private double rayon =
 *         Potentiometre.RAYON_REF; private double minValue = 0.; private double
 *         maxValue = 100.; private boolean discret = false; private double
 *         nbSpins = 1.; private double valueDef; private boolean valueDefInit;
 *
 *         private boolean showTickMarks = false; private boolean showTickLabels
 *         = false; private double majorTickUnit = 5.;
 *
 */
public class PotentiometreFactory {

	private PotentiometreFactory() {
	}

	static public PotentiometreFactory getFactoryInstance() {
		return new PotentiometreFactory();
	}

	public Potentiometre getPotentiometre() {
		return new Potentiometre(this);
	}

	private String title = "";
	private double rayon = Potentiometre.RAYON_REF;
	private double minValue = 0.;
	private double maxValue = 100.;
	private boolean discret = false;
	private double nbSpins = 1.;
	private double valueDef;
	private boolean valueDefInit;

	private boolean showTickMarks = false;
	private boolean showTickLabels = false;
	private double majorTickUnit = 5.;

	/**
	 * get the minimum value
	 * 
	 * @return the minimum value
	 * 
	 */
	public double getMinValue() {
		return minValue;
	}

	/**
	 * set the minimum value
	 * 
	 * @param minValue
	 *            the minimum value
	 */
	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	/**
	 * get the maximum value
	 * 
	 * @return the maximum value
	 */
	public double getMaxValue() {
		return maxValue;
	}

	/**
	 * set the maximum value
	 * 
	 * @param maxValue
	 *            the maximum value
	 */
	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	/**
	 * get the discret flag ( the discret flag allowed to have only integer
	 * values)
	 * 
	 * @return the discret flag
	 */
	public boolean isDiscret() {
		return discret;
	}

	/**
	 * set the discret flag ( the discret flag allowed to have only integer
	 * values)
	 * 
	 * @param discret
	 */
	public void setDiscret(boolean discret) {
		this.discret = discret;
	}

	/**
	 * get the number of permit spins
	 * 
	 * @return the number of permits spins
	 */
	public double getNbSpins() {
		return nbSpins;
	}

	/**
	 * set the number of permit spins
	 * 
	 * @param nbSpins
	 *            the number of permits spins
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
		else
			return (minValue + maxValue) / 2.;
	}

	/**
	 * set the default value
	 * @param valueDef
	 */
	public void setValueDef(double valueDef) {
		this.valueDef = valueDef;
		valueDefInit = true;
	}

	/**
	 * get the show tick marks flag
	 * @return the show tick marks flag
	 */
	public boolean isShowTickMarks() {
		return showTickMarks;
	}

	/**
	 * set the show tick marks flag
	 * @param showTickMarks
	 * the show tick marks flag
	 */
	public void setShowTickMarks(boolean showTickMarks) {
		this.showTickMarks = showTickMarks;
	}

	/**
	 * get the show tick labels flag
	 * @return the show tick labels flag
	 */
	public boolean isShowTickLabels() {
		return showTickLabels;
	}

	/**
	 * set the show tick labels flag
	 * @param showTickLabels
	 * the show tick labels flag
	 */
	public void setShowTickLabels(boolean showTickLabels) {
		this.showTickLabels = showTickLabels;
	}

	/**
	 * get the major tick unit
	 * @return 
	 * the major tick unit
	 */
	public double getMajorTickUnit() {
		return majorTickUnit;
	}

	/**
	 * set the major tick unit 
	 * @param majorTickUnit
	 *	 the major tick unit 
	 */
	public void setMajorTickUnit(double majorTickUnit) {
		this.majorTickUnit = majorTickUnit;
	}

	/**
	 * get the title
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * set the title
	 * @param title
	 * the title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * get the rayon
	 * @return
	 * the rayon
	 */
	public double getRayon() {
		return rayon;
	}

	/**
	 * set the rayon
	 * @param rayon
	 * 	the rayon
	 */
	public void setRayon(double rayon) {
		this.rayon = rayon;
	}

}
