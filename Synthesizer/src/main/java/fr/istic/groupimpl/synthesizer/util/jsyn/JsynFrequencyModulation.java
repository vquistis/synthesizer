package fr.istic.groupimpl.synthesizer.util.jsyn;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.util.SignalUtil;

/**
 * Frequency Voltage Controller
 * 
 * f0 default frequency
 *  
 * fm frequency modulation
 * +1 Volt = +1 Octave
 * -1 Volt = -1 Octave
 *    
 * octave value
 * minus or plus sign
 *    
 * <pre>
 * output = f0*Math.pow(2, octave + fm);
 * <pre>
 * 
 * @author Team GroupImpl
 */
public class JsynFrequencyModulation extends UnitGenerator {
	
	/** The inputfm. */
	private UnitInputPort inputfm; 		//Volt
	
	/** The inputf0. */
	private UnitInputPort inputf0; 		//HZ
	
	/** The input octave. */
	private UnitInputPort inputOctave; 	//UnitVariablePort 
	
	/** The output. */
	private UnitOutputPort output; 		//HZ
	
    
    /**
     * Modulation frequency input.
     *
     * @return the inputfm
     */
    public UnitInputPort getInputfm() {
		return inputfm;
	}

	/**
	 * Default Frequency input.
	 *
	 * @return the inputf0
	 */
	public UnitInputPort getInputf0() {
		return inputf0;
	}

	/**
	 * Octave value input.
	 *
	 * @return the input octave
	 */
	public UnitInputPort getInputOctave() {
		return inputOctave;
	}
	
	/**
	 * Frequency Output.
	 *
	 * @return the output
	 */
	public UnitOutputPort getOutput() {
		return output;
	}

	/**
	 * Constructor.
	 */
    public JsynFrequencyModulation() {
        addPort(inputfm = new UnitInputPort("Inputfm"));
        addPort(inputf0 = new UnitInputPort("Inputf0"));
        addPort(inputOctave = new UnitInputPort("InputOctave"));
        addPort(output = new UnitOutputPort("Output"));
    }

    /**
     * @see com.jsyn.unitgen.UnitGenerator#generate(int, int)
     */
    @Override
    public void generate(int start, int limit) {
        double[] inputfms = inputfm.getValues();
        double[] inputf0s = inputf0.getValues();
        double[] inputOctaves = inputOctave.getValues();
        double[] outputs = output.getValues();

        for (int i = start; i < limit; i++) {
        	double freq=converter(SignalUtil.verifyFms(inputfms[i]), inputf0s[i], inputOctaves[i]);        	
        	outputs[i]=SignalUtil.verifyFrequence(freq);
        }
    }
    
    
    
    /**
     * Converter.
     *
     * @param fm   Modulation Frequency 
     * @param f0   Default frequency
     * @param octave   octave value
     * @return   Frequency
     */
    public double converter(double fm, double f0, double octave) {
    	return f0*Math.pow(2, octave + fm*SignalUtil.COEF_VOLT);
    }
}
