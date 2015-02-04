package fr.istic.groupimpl.synthesizer.out;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.unitgen.UnitGenerator;
import com.jsyn.unitgen.UnitSink;

/**
 * Input audio is sent to the external audio output device with an attenuation factor.
 */
public class JsynAttenuationOut extends UnitGenerator implements UnitSink {
	
    public UnitInputPort input;
    
    // Attenuation value
	private double coef = 1;

    public JsynAttenuationOut() {
        addPort(input = new UnitInputPort(2, "Input"));
    }

    @Override
    public void generate(int start, int limit) {
        double[] inputs0 = input.getValues(0);
        double[] inputs1 = input.getValues(1);
        double[] buffer0 = synthesisEngine.getOutputBuffer(0);
        double[] buffer1 = synthesisEngine.getOutputBuffer(1);
        for (int i = start; i < limit; i++) {
        	//attenuation of the input signal
        	double out0 = inputs0[i]*coef;
        	double out1 = inputs1[i]*coef;
            buffer0[i] += out0;
            buffer1[i] += out1;
        }
    }

    @Override
    public UnitInputPort getInput() {
        return input;
    }
    
	/**
	 * Set the attenuation value
	 */
	public void set(double coef) {
		this.coef = coef;
	}
}