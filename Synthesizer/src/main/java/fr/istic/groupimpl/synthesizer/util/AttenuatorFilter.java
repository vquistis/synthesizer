package fr.istic.groupimpl.synthesizer.util;

import com.jsyn.unitgen.UnitFilter;

/**
 * Attenuation Filter
 */
public class AttenuatorFilter extends UnitFilter {

	private double dbValue = 0;

	@Override
	public void generate(int start, int limit) {
		// Get signal arrays from ports.
		double[] inputs = input.getValues();
		double[] outputs = output.getValues();

		for (int i = start; i < limit; i++) {
			double in = inputs[i];
			double out = in+dbValue;
			outputs[i] = out;
		}
	}

	/**
	 * Set the attenuation value in db
	 */
	public void set(double dbValue) {
		this.dbValue = dbValue;
	}

	/**
	 * Get the attenuation value in db
	 */
	public double get() {
		return this.dbValue;
	}
}
