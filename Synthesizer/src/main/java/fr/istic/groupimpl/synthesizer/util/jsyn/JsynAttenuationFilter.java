package fr.istic.groupimpl.synthesizer.util.jsyn;

import com.jsyn.unitgen.UnitFilter;

import fr.istic.groupimpl.synthesizer.util.SignalUtil;

/**
 * The input signal is attenuated by an decibel value.
 */
public class JsynAttenuationFilter extends UnitFilter {
	
    // Attenuation coefficient
	private double coef = 1;

    @Override
    public void generate(int start, int limit) {
		// Get signal arrays from ports.
		double[] inputs = input.getValues();
		double[] outputs = output.getValues();
		
        for (int i = start; i < limit; i++) {
        	//attenuation of the input signal
			double in = SignalUtil.verifyAmplitude(inputs[i]);
			double out = in*coef;
			outputs[i] = SignalUtil.verifyAmplitude(out);
        }
    }

	/**
	 * Set an attenuation decibel value
	 */
	public void set(double dbValue) {
		coef = Math.pow(2, dbValue/6);
	}
}