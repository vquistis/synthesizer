package fr.istic.groupimpl.synthesizer.player;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;

/**
 * The Class PlayerTest.
 */
public class PlayerTest {

	/** The model. */
	private ModelPlayer model;
	
	/** The synth. */
	private Synthesizer synth;
	
	/**
	 * Inits the.
	 */
	@Before
	public void init() {
		synth = JSyn.createSynthesizer();
		model = new ModelPlayer();
		
		model.loadSample("src/main/resources/sound/junit_PlayerTest.wav");
		
		synth.add(model.getUnitGenerator());
		
		LineOut lineout = new LineOut();
		synth.add(lineout);
		
		lineout.getInput().connect(model.getOutputPort());
		
		synth.start();
		lineout.start();
	}
	
	/**
	 * Test model.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	public void testModel() throws InterruptedException {
		model.play();
		synth.sleepFor(8);
		synth.stop();
	}
	
	/**
	 * Test output.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	public void testOutput() throws InterruptedException {
		double sumOutput = 0;
		model.play();
		for (int i=0; i<20; i++) {
			synth.sleepUntil(synth.getCurrentTime() + 0.1);
			sumOutput =+ Math.abs(model.getOutputPort().get());
		}
		
		assertTrue("ouput", sumOutput > 0.00);
		synth.stop();
	}
}