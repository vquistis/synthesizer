package fr.istic.groupimpl.synthesizer.vcf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitOscillator;

public class ModelVcfLPTest {

	private Synthesizer synth;
	private UnitOscillator sineOsc;
	private ModelVcf model;

	@Before
	public void init() {
		synth = JSyn.createSynthesizer();

		sineOsc = new SineOscillator();
		model = new ModelVcf(ModelVcf.Type.LP24);
		
		synth.add(sineOsc);
		synth.add(model.getUnitGenerator());

		sineOsc.amplitude.set(1);
		sineOsc.frequency.set(440);
		sineOsc.output.connect(model.getInputPort());

		synth.start();
	}
	
	@Test
	public void testOpenFreqMax() throws InterruptedException {
		// Configuration
		model.setCutFrequency(Double.MAX_VALUE);
		model.setResonance(1);	
		
		// Consomation de valeur
		model.getUnitGenerator().start();
		Thread.sleep(100);
		model.getUnitGenerator().stop();
		
		// Test des valeurs de sortie
		for (int i = 0; i < 8; i++) {
			assertEquals(model.getOutputPort().getValues()[i], sineOsc.output.getValues()[i], 0.01);
		}
	}

	@Test
	public void testOpenFreqNotMax() throws InterruptedException {
		// Configuration
		model.setCutFrequency(256);
		model.setResonance(1);	
		
		// Consomation de valeur
		model.getUnitGenerator().start();
		Thread.sleep(100);
		model.getUnitGenerator().stop();
		
		// Test des valeurs de sortie
		for (int i = 0; i < 8; i++) {
			assertNotEquals(model.getOutputPort().getValues()[i], sineOsc.output.getValues()[i], 0.0001);
		}
	}

	@Test
	public void testCloseFreqMin() throws InterruptedException {
		// Configuration
		model.setCutFrequency(1);
		model.setResonance(0);	
		
		// Consomation de valeur
		model.getUnitGenerator().start();
		Thread.sleep(100);
		model.getUnitGenerator().stop();
		
		// Test des valeurs de sortie
		for (int i = 0; i < 8; i++) {
			assertEquals(model.getOutputPort().getValues()[i], 0.0, 0.01);
		}
	}

	@Test
	public void testCloseFreqNotMin() throws InterruptedException {
		// Configuration
		model.setCutFrequency(256);
		model.setResonance(1);	
		
		// Consomation de valeur
		model.getUnitGenerator().start();
		Thread.sleep(100);
		model.getUnitGenerator().stop();
		
		// Test des valeurs de sortie
		for (int i = 0; i < 8; i++) {
			assertNotEquals(model.getOutputPort().getValues()[i], 0.0, 0.0001);
		}
	}

}
