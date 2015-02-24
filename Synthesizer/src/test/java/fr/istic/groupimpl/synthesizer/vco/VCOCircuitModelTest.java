package fr.istic.groupimpl.synthesizer.vco;

import org.junit.Before;
import org.junit.Test;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;

/**
 * The Class VCOCircuitModelTest.
 */
public class VCOCircuitModelTest {

	/** The model. */
	private ModelVco model;
	
	/** The synth. */
	private Synthesizer synth;
	
	/**
	 * Inits the.
	 */
	@Before
	public void init() {
		synth = JSyn.createSynthesizer();
		model = new ModelVco();
		
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
		synth.sleepUntil( synth.getCurrentTime() + 0.5 );
		synth.stop();
	}
}