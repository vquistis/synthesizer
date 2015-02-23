package fr.istic.groupimpl.synthesizer.util.jsyn;

import com.jsyn.unitgen.UnitFilter;

import fr.istic.groupimpl.synthesizer.util.SignalUtil;

/**
 * The input signal is attenuated by an decibel value.
 * 
 * @author Team GroupImpl
 * 
 */
public class JsynAttenuationFilter extends UnitFilter {
	
	/** The mute. */
	private boolean mute=false;
	 	
	// Attenuation coefficient
	/** The coef. */
	private double coef = 1.;
	
	/** The last coef value. */
	private double lastCoefValue=coef;

    /**
     * @see com.jsyn.unitgen.UnitGenerator#generate(int, int)
     */
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
	 * Set an attenuation decibel value.
	 *
	 * @param dbValue the db value
	 */
	public void set(double dbValue) {
		lastCoefValue = convertDecibelToCoef(dbValue);
		if (!isMute()) {coef = lastCoefValue;}
	}
	
	/**
	 * Get an attenuation decibel value.
	 *
	 * @return the double
	 */
	public Double get() {
		return convertCoefToDecibel(coef);
	}
	
	/**
	 * Set Mute to the output signal.
	 *
	 * @param value     true|false
	 */
	public void setMute(boolean value) {
		mute=value;
		if (value) {
			coef = 0.;
		} else {
			coef = lastCoefValue;
		}
	}
	
    /**
     * Get mute state.
     *
     * @return boolean
     */
    public boolean isMute() {
		return mute;
	}
    
    /**
     * Calculate the coef value from the decibel value.
     *
     * @param decibel the decibel
     * @return double
     * 		Coefficient d'attenuation
     */
    private double convertDecibelToCoef(double decibel) {
    	return Math.pow(2, decibel/6);
    }
    
    /**
     * Calculate the decibel value from the coef value.
     *
     * @param coef 		coefficient d'attenuation
     * @return double
     * 		Decibel value
     */
    private double convertCoefToDecibel(double coef) {
    	return (Math.log(coef)/Math.log(2))*6;
    }
}