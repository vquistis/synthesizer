package fr.istic.groupimpl.synthesizer.keyboard;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;
import com.jsyn.unitgen.UnitSource;


public class JSynKeyboard extends UnitGenerator implements UnitSource {

	public UnitInputPort key;
	public UnitOutputPort output;
	
	public JSynKeyboard() {
		addPort(key = new UnitInputPort("key"));
		addPort(output = new UnitOutputPort("Output"));
	}

	@Override
	public void generate(int start, int limit) {
		double[] keys = key.getValues();
		double[] outputs = output.getValues();

		for (int i = start; i < limit; i++) {
			outputs[i] = keys[i] / 12;
		}
	}

	@Override
	public UnitOutputPort getOutput() {
		return output;
	}

	public UnitInputPort getKey() {
		return key;
	}

	public void setKey(UnitInputPort key) {
		this.key = key;
	}
	
}
