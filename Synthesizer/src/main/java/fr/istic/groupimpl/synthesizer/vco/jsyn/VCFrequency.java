package fr.istic.groupimpl.synthesizer.vco.jsyn;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitVariablePort;
import com.jsyn.unitgen.UnitGenerator;

/**
 * Frequency Voltage Controller
 * +1 Volt = +1 Octave
 * -1 Volt = -1 Octave
 *  
 * <pre>
 * output = f0*Math.pow(2, octave + fm);
 * <pre>
 * 
 * 
 * @author Team GroupImpl
 */

public class VCFrequency extends UnitGenerator {
	private UnitInputPort inputfm; 		//Volt
	private UnitInputPort inputf0; 		//HZ
	private UnitInputPort inputOctave; 	//UnitVariablePort 
	private UnitOutputPort output; 		//HZ
    
    public UnitInputPort getInputfm() {
		return inputfm;
	}

	public UnitInputPort getInputf0() {
		return inputf0;
	}

	public UnitInputPort getInputOctave() {
		return inputOctave;
	}
	
	public UnitOutputPort getOutput() {
		return output;
	}

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
        	outputs[i] = converter(inputfms[i], inputf0s[i], inputOctaves[i]);
        }
    }
    
    public double converter(double fm, double f0, double octave) {
    	return f0*Math.pow(2, octave + fm);
    }
}
