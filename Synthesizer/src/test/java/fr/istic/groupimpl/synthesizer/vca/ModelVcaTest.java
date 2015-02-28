package fr.istic.groupimpl.synthesizer.vca;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitOscillator;

/**
 * The Class ModelVcfHPTest.
 */
public class ModelVcaTest {

	/** The synth. */
	private Synthesizer synth;

	/** The sine osc. */
	private UnitOscillator sineOsc;

	/** The model. */
	private ModelVca model;

	/**
	 * set up method.
	 */
	@Before
	public void init() {
		synth = JSyn.createSynthesizer();

		sineOsc = new SineOscillator();
		model = new ModelVca();

		sineOsc.amplitude.set(1);
		sineOsc.frequency.set(440);

		synth.add(sineOsc);
		synth.add(model.getUnitGenerator());

		synth.start();
	}

	/**
	 * Test get value of input port am.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	public void testGetInputPort_am() throws InterruptedException {
		// Configuration
		sineOsc.output.connect(model.getInputPort("vca_inputam"));

		// Consomation de valeur
		model.getUnitGenerator().start();
		Thread.sleep(100);
		model.getUnitGenerator().stop();

		// Test des valeurs de sortie
		assertEquals(sineOsc.output.getValues()[0], model.getInputPort("vca_inputam").getValue(), 0.01);
		assertNotEquals(sineOsc.output.getValues()[0], model.getInputPort("vca_inputa0").getValue(), 0.01);
		assertNotEquals(sineOsc.output.getValues()[0], model.getInputPort("vca_input").getValue(), 0.01);
	}

	/**
	 * Test get value of input port in.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	public void testGetInputPort_in() throws InterruptedException {
		// Configuration
		sineOsc.output.connect(model.getInputPort("vca_input"));

		// Consomation de valeur
		model.getUnitGenerator().start();
		Thread.sleep(100);
		model.getUnitGenerator().stop();

		// Test des valeurs de sortie
		assertEquals(sineOsc.output.getValues()[0], model.getInputPort("vca_input").getValue(), 0.01);
		assertNotEquals(sineOsc.output.getValues()[0], model.getInputPort("vca_inputa0").getValue(), 0.01);
		assertNotEquals(sineOsc.output.getValues()[0], model.getInputPort("vca_inputam").getValue(), 0.01);

	}

	/**
	 * Test get value of input port a0.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	public void testGetInputPort_a0() throws InterruptedException {
		// Configuration
		sineOsc.output.connect(model.getInputPort("vca_inputa0"));

		// Consomation de valeur
		model.getUnitGenerator().start();
		Thread.sleep(100);
		model.getUnitGenerator().stop();

		// Test des valeurs de sortie

		assertEquals(sineOsc.output.getValues()[0], model.getInputPort("vca_inputa0").getValue(), 0.01);
		assertNotEquals(sineOsc.output.getValues()[0], model.getInputPort("vca_input").getValue(), 0.01);
		assertNotEquals(sineOsc.output.getValues()[0], model.getInputPort("vca_inputam").getValue(), 0.01);
	}
	
	/**
	 * Test set value of input port a0.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	public void testsetVolt() throws InterruptedException {
		// Configuration
		sineOsc.output.connect(model.getInputPort("vca_inputa0"));

		// Consomation de valeur
		model.getUnitGenerator().start();
		Thread.sleep(100);
		model.getUnitGenerator().stop();
		model.setVolt(3.5);
		// Test des valeurs de sortie
		assertEquals(sineOsc.output.getValues()[0], model.getInputPort("vca_inputa0").getValue(), 0.01);
	}

}
