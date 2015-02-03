package fr.istic.groupimpl.synthesizer.out;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;

public class ModelOut extends ModelComponent {

	private JsynAttenuationOut out;
	
	public ModelOut() {
		super();

		out = new JsynAttenuationOut();
		out.input.setName("input_out"); // this name have to be the same as the name defined in the view
		setAttenuation(0); // Default value

		/* Test */
		Synthesizer synth = JSyn.createSynthesizer();
		SineOscillator osc = new SineOscillator();
		osc.output.connect(out.input);
		synth.add(osc);
		synth.add(out);
		synth.start();
		out.start();
	}

	public void setAttenuation(double value) {
		out.set(value);
	}

	public void start() {
		out.start();
	}
	public void stop() {
		out.stop();
	}

	@Override
	public UnitGenerator getUnitGenerator() {
		return out;
	}
	
	@Override
	public UnitInputPort getInputPort(String portName) {
		return (UnitInputPort) out.getPortByName(portName);
	}

	@Override
	public UnitOutputPort getOutputPort(String portName) {
		//This module doesn't have output port
		return null;
	}
}
