package fr.istic.groupimpl.synthesizer.keyboard;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;
import com.jsyn.unitgen.UnitSource;

import fr.istic.groupimpl.synthesizer.util.SignalUtil;


public class JSynKeyboard extends UnitGenerator implements UnitSource {

	/* Declare ports */
	public UnitInputPort key;
	public UnitOutputPort output;
	
	/**
	 * constructor
	 */
	public JSynKeyboard() {
		addPort(key = new UnitInputPort("key"));
		addPort(output = new UnitOutputPort("output"));
	}
	
	@Override
	public void generate(int start, int limit) {
		double[] keys = key.getValues();
		double[] outputs = output.getValues();

		for (int i = start; i < limit; i++) {
			outputs[i] = (keys[i] / 12) / SignalUtil.COEF_VOLT;
		}
	}

	/**
	 * Signal output
	 *
	 * @return output
	 */
	@Override
	public UnitOutputPort getOutput() {
		return output;
	}

	/**
	 * Signal input
	 *
	 * @return input
	 */
	public UnitInputPort getKey() {
		return key;
	}

}
