package fr.istic.groupimpl.synthesizer.out;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.util.AttenuatorFilter;

public class ModelOut extends ModelComponent {

	private LineOut out;
	private AttenuatorFilter attenuator;
	
	public ModelOut() {
		super();

		out = new LineOut();
		attenuator = new AttenuatorFilter();
		attenuator.output.connect(out.input);

		// Default value
		setAttenuation(0);

		/* Test */
		Synthesizer synth = JSyn.createSynthesizer();
		SineOscillator osc = new SineOscillator();
		osc.output.connect(attenuator.input);
		synth.add(osc);
		synth.add(out);
		synth.start();
		out.start();
	}

	public void setAttenuation(double value) {
		attenuator.set(value);
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
}
