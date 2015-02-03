package fr.istic.groupimpl.synthesizer.util;

/**
 * @author Yves Mocquard
 *
 *         Classe d'initialisation du potentiometre
 */
public class PotentiometreFactory {

	private PotentiometreFactory() {
	}
	static public PotentiometreFactory getFactoryInstance()
	{
		return new PotentiometreFactory();
	}
	public Potentiometre getPotentiometre()
	{
		return new Potentiometre( this );
	}
	private String title = "";
	private double rayon = Potentiometre.RAYON_REF;
	private double minValue = 0.;
	private double maxValue = 100.;
	private boolean discret = false;
	private double nbSpins = 1.;
	private double valueDef = 50.;

	private boolean showTickMarks = false;
	private boolean showTickLabels = false;
	private double majorTickUnit = 5.;

	public double getMinValue() {
		return minValue;
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	public boolean isDiscret() {
		return discret;
	}

	public void setDiscret(boolean discret) {
		this.discret = discret;
	}

	public double getNbSpins() {
		return nbSpins;
	}

	public void setNbSpins(double nbSpins) {
		this.nbSpins = nbSpins;
	}

	public double getValueDef() {
		return valueDef;
	}

	public void setValueDef(double valueDef) {
		this.valueDef = valueDef;
	}

	public boolean isShowTickMarks() {
		return showTickMarks;
	}

	public void setShowTickMarks(boolean showTickMarks) {
		this.showTickMarks = showTickMarks;
	}

	public boolean isShowTickLabels() {
		return showTickLabels;
	}

	public void setShowTickLabels(boolean showTickLabels) {
		this.showTickLabels = showTickLabels;
	}

	public double getMajorTickUnit() {
		return majorTickUnit;
	}

	public void setMajorTickUnit(double majorTickUnit) {
		this.majorTickUnit = majorTickUnit;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getRayon() {
		return rayon;
	}

	public void setRayon(double rayon) {
		this.rayon = rayon;
	}

}
