package fr.istic.groupimpl.synthesizer.util;

/**
 * The Class SignalUtil.
 */
public class SignalUtil {
	
	public static double COEF_VOLT=5.;
	
	/** The Constant AMPLITUDE_MAX. */
	private static final int FREQUENCE_MAX = 22100;

	/** The Constant AMPLITUDE_MAX. */
	private static final double AMPLITUDE_MAX = 5;

	/** The Constant AMPLITUDE_MIN. */
	private static final double AMPLITUDE_MIN = -5;
	
	/** The freq max. */
	private static int MODULATION_MAX= 10;
	
	/** The freq min. */
	private static  int MODULATION_MIN = -10;
	
	/** The valid. */
	private static boolean valid = true;
	
	/**
	 * Checks if is valid.
	 *
	 * @return true, if is valid
	 */
	public static boolean isValid() {
		return valid;
	}

	/**
	 * Sets the valid.
	 *
	 * @param valid the new valid
	 */
	public static void setValid(boolean valid) {
		SignalUtil.valid = valid;
	}
	
	/**
	 * Verify frequence.
	 *
	 * @param frequence the frequence
	 * @return the double
	 */
	public  static double verifyFrequence(double frequence){
		if ( !valid )
			return frequence;
    	if (frequence<FREQUENCE_MAX) {
    		return frequence;
		} else {
			return FREQUENCE_MAX;
		}
    }
    
    /**
     * Verify fms.
     *
     * @param mod the frequence
     * @return the double
     */
    public static double verifyFms(double mod){    	
		if ( !valid )
			return mod;
		if (mod>=MODULATION_MAX) {
    		return MODULATION_MAX;
		} else if (mod<=MODULATION_MIN) {
			return MODULATION_MIN;
		} else {
			return mod;
		}
    }
    
    /**
     * Verify amplitude.
     *
     * @param amplitude the amplitude
     * @return the double
     */
    public static double verifyAmplitude(double amplitude){ 
		if ( !valid )
			return amplitude;

		if (amplitude>=AMPLITUDE_MAX) {
    		return AMPLITUDE_MAX;
		} else if (amplitude<=AMPLITUDE_MIN) {
			return AMPLITUDE_MIN;
		} else {
			return amplitude;
		}
    }

}
