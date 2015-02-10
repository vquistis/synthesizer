package fr.istic.groupimpl.synthesizer.vca.jsyn;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

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
 * decibel = (inputam + inputa0-5) * SignalUtil.COEF_VOLT * 12 );
 * output = input*Math.pow(10, decibel/20);
 * <pre>
 * 
 * @author Team GroupImpl
 */
public class VCAJSyn extends UnitGenerator {

	/** The input. */
	private UnitInputPort input;	 	//Volt

	/** The inputam. */
	private UnitInputPort inputam; 		//Volt

	/** The inputa0. */
	private UnitInputPort inputa0; 		//Volt

	/** The output. */
	private UnitOutputPort output; 		//Volt

	/**
	 * Signal input.
	 *
	 * @return input
	 */
	public UnitInputPort getInput() {
		return input;
	}

	/**
	 * Modulation amplitude input.
	 *
	 * @return inputam
	 */
	public UnitInputPort getInputam() {
		return inputam;
	}

	/**
	 * Modulation amplitude with potentiometer.
	 *
	 * @return inputa0
	 */
	public UnitInputPort getInputa0() {
		return inputa0;
	}

	/**
	 * Signal Output.
	 *
	 * @return output
	 */
	public UnitOutputPort getOutput() {
		return output;
	}

	/**
	 * create a new vca jsyn.
	 */
	public VCAJSyn() {
		addPort(input = new UnitInputPort("vca_input"));
		addPort(inputam = new UnitInputPort("vca_inputam"));
		addPort(inputa0 = new UnitInputPort("vca_inputa0"));
		addPort(output = new UnitOutputPort("vca_output"));
	}

	/** 
	 * generate signal
	 * @see com.jsyn.unitgen.UnitGenerator#generate(int, int)
	 */
	@Override
	public void generate(int start, int limit) {
		double[] inputs = input.getValues();
		double[] inputams = inputam.getValues();
		double[] inputa0s = inputa0.getValues();
		double[] outputs = output.getValues();

		for (int i = start; i < limit; i++) {
			double am = SignalUtil.verifyAmplitude(inputams[i])*SignalUtil.COEF_VOLT;
			if (am >0)
			{
				double decibel = ((am+SignalUtil.verifyAmplitude(inputa0s[i])-SignalUtil.COEF_VOLT) * 12 );
				outputs[i]=converter(decibel, inputs[i]);
			}
			else
			{
				outputs[i] = 0;
			}
		}
	}

	/**
	 * Converter decibel to volt.
	 *
	 * @param decibel the decibel
	 * @param in the in
	 * @return   Frequency to volt
	 */
	public double converter(double decibel, double in) {
		return in*Math.pow(10, decibel/20);
	}
}
