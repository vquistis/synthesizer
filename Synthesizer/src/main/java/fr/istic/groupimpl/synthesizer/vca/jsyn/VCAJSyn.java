package fr.istic.groupimpl.synthesizer.vca.jsyn;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.logger.Log;
import fr.istic.groupimpl.synthesizer.util.SignalUtil;

/**
 *  Voltage Controller Amplitude
 *
 * 
 * a0 amplitude modulation with potentiometer
 *  
 * am amplitude modulation
 * +1 Volt = +12 dB
 * -1 Volt = -12 dB
 *    
 * <pre>
 * output = input*Math.pow(4, am-5 + a0);
 * <pre>
 * 
 * @author Team GroupImpl
 */
public class VCAJSyn extends UnitGenerator {
	
	private UnitInputPort input;	 	//Volt
	private UnitInputPort inputam; 		//Volt
	private UnitInputPort inputa0; 		//Volt
	private UnitOutputPort output; 		//Volt
	
	/**
     * Signal input
     * @return input
     */
    public UnitInputPort getInput() {
		return input;
	}
    
    /**
     * Modulation amplitude input
     * @return inputam
     */
    public UnitInputPort getInputam() {
		return inputam;
	}

	/**
	 * Modulation amplitude with potentiometer
	 * @return inputa0
	 */
	public UnitInputPort getInputa0() {
		return inputa0;
	}

	/**
	 * Signal Output
	 * @return output
	 */
	public UnitOutputPort getOutput() {
		return output;
	}

	/**
	 * Constructor
	 */
    public VCAJSyn() {
    	addPort(input = new UnitInputPort("Input"));
        addPort(inputam = new UnitInputPort("Inputam"));
        addPort(inputa0 = new UnitInputPort("Inputa0"));
        addPort(output = new UnitOutputPort("Output"));
    }

    @Override
    public void generate(int start, int limit) {
    	double[] inputs = input.getValues();
        double[] inputams = inputam.getValues();
        double[] inputa0s = inputa0.getValues();
        double[] outputs = output.getValues();

        for (int i = start; i < limit; i++) {
        	double decibel = (SignalUtil.verifyAmplitude(inputams[i]) * 5 * 12 ) + SignalUtil.verifyAmplitude(inputa0s[i]);
        	outputs[i]=converter(decibel, inputs[i]); 
        	Log.getInstance().info(inputs[i]+" --> "+inputams[i]+" -->  "+outputs[i]);
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
    public double converter(double decibel, double in) {
    	return in*Math.pow(10, decibel/20);
    }
}
