package fr.istic.groupimpl.synthesizer.eg;

import org.junit.Before;
import org.junit.Test;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitOscillator;

/**
 * The Class ModelEgTest.
 */
public class ModelEgTest {

	/** The model. */
	private ModelEg model;
	
	/** The synth. */
	private Synthesizer synth;

	/**
	 * Inits the.
	 */
	@Before
	public void init() {
		synth = JSyn.createSynthesizer();

		model = new ModelEg();
		UnitOscillator sineOsc = new SineOscillator();
		LineOut lineout = new LineOut();

		synth.add(model.getUnitGenerator());
		synth.add(sineOsc);
		synth.add(lineout);

		sineOsc.amplitude.connect(model.getOutputPort());
		lineout.getInput().connect(sineOsc.output);

		synth.start();
		lineout.start();

		model.setAttack(1); // Default Attack 1 sec
		model.setDecay(1); // Default Decay 1 sec
		model.setSustain(0.5); // Default Sustain 0.5 volts
		model.setRelease(1); // Default Release 1 sec

		sineOsc.frequency.set(440.0);
	}

	/**
	 * Test model.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	public void testModel() throws InterruptedException {
		model.getInputPort().set(1);
		Thread.sleep(3000);
		model.getInputPort().set(0);
		Thread.sleep(1000);
		model.getInputPort().set(1);
		Thread.sleep(3000);
		model.getInputPort().set(0);
		Thread.sleep(5000);
		synth.stop();
	}

}
