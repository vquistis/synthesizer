package fr.istic.groupimpl.synthesizer.vca.jsyn;

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
public class VCFrequency extends UnitGenerator {
	
	private UnitInputPort inputfm; 		//Volt
	private UnitInputPort inputf0; 		//HZ
	private UnitInputPort inputOctave; 	//UnitVariablePort 
	private UnitOutputPort output; 		//HZ
	
    
    /**
     * Modulation frequency input
     * @return
     */
    public UnitInputPort getInputfm() {
		return inputfm;
	}

	/**
	 * Default Frequency input
	 * @return
	 */
	public UnitInputPort getInputf0() {
		return inputf0;
	}

	/**
	 * Octave value input
	 * @return
	 */
	public UnitInputPort getInputOctave() {
		return inputOctave;
	}
	
	/**
	 * Frequency Output
	 * @return
	 */
	public UnitOutputPort getOutput() {
		return output;
	}

	/**
	 * Constructor
	 */
    public VCFrequency() {
        addPort(inputfm = new UnitInputPort("Inputfm"));
        addPort(inputf0 = new UnitInputPort("Inputf0"));
        addPort(inputOctave = new UnitInputPort("InputOctave"));
        addPort(output = new UnitOutputPort("Output"));
    }

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
     * @param fm
     *   Modulation Frequency 
     * @param f0
     *   Default frequency
     * @param octave
     *   octave value
     * @return
     *   Frequency
     */
    public double converter(double fm, double f0, double octave) {
    	return f0*Math.pow(2, octave + fm);
    }
}
