package fr.istic.groupimpl.synthesizer.out;

import org.junit.Before;
import org.junit.Test;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.SineOscillator;

public class ModelOutTest {

	private ModelOut model;
	
	@Before
	public void init() {
		model = new ModelOut();
		
		Synthesizer synth = JSyn.createSynthesizer();
		SineOscillator osc = new SineOscillator();
		osc.amplitude.set(1);
		osc.frequency.set(440);
		model.getInputPort("out_input").connect(osc.output);
		synth.add(model.getUnitGenerator());
		synth.add(osc);
		synth.start();
	}
	
	@Test
	public void testAttenuation() throws InterruptedException {
		model.getUnitGenerator().start();

		model.setAttenuation(-12);
		Thread.sleep(1000);
		model.setAttenuation(-6);
		Thread.sleep(1000);
		model.setAttenuation(0);
		Thread.sleep(1000);
		model.setAttenuation(6);
		Thread.sleep(1000);
		model.setAttenuation(12);
		Thread.sleep(1000);
		
		model.getUnitGenerator().stop();
	}

	@Test
	public void testMute() throws InterruptedException {
		for (int i = 10; i > 0; i--) {
			model.getUnitGenerator().start();
			Thread.sleep(1000);
			model.getUnitGenerator().stop();
			Thread.sleep(1000);
		}
	}
}
