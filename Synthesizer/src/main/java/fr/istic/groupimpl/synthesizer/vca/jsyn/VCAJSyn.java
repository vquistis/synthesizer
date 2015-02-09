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
    	addPort(input = new UnitInputPort("vca_input"));
        addPort(inputam = new UnitInputPort("vca_inputam"));
        addPort(inputa0 = new UnitInputPort("vca_inputa0"));
        addPort(output = new UnitOutputPort("vca_output"));
    }

    @Override
    public void generate(int start, int limit) {
    	double[] inputs = input.getValues();
        double[] inputams = inputam.getValues();
        double[] inputa0s = inputa0.getValues();
        double[] outputs = output.getValues();

        for (int i = start; i < limit; i++) {
        	double am = SignalUtil.verifyAmplitude(inputams[i]);
        	if (am >0)
        	{
        	double decibel = ((am+SignalUtil.verifyAmplitude(inputa0s[i])-5) * 12 );
//        	outputs[i]= inputs[i];
        	outputs[i]=converter(decibel, inputs[i]);
        	}
        	else
        	{
        		outputs[i] = 0;
        	}
        	Log.getInstance().info(inputs[i]+" --> "+inputams[i]+ " -->  "+ inputa0s[i] +" -->  "+outputs[i]);
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
