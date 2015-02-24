package fr.istic.groupimpl.synthesizer.out;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jsyn.engine.SynthesisEngine;
import com.jsyn.unitgen.SineOscillator;

import fr.istic.groupimpl.synthesizer.out.jsyn.JsynOutCircuit;

/**
 * The Class JsynOutCircuitTest.
 */
public class JsynOutCircuitTest {
	
    /** The synthesis engine. */
    private SynthesisEngine synthesisEngine;
    
    /** The circuit. */
    private JsynOutCircuit circuit;

    /**
     * Inits the.
     */
    @Before
    public void init() {
        synthesisEngine = new SynthesisEngine();

		SineOscillator osc = new SineOscillator();
		osc.amplitude.set(1);
		osc.frequency.set(440);
		
        circuit = new JsynOutCircuit();
		circuit.getInput().connect(osc.output);
		
		synthesisEngine.add(osc);
        synthesisEngine.add(circuit);

        synthesisEngine.start();
    }
    
    /**
     * Uninit.
     */
    @After
    public void uninit() {
        synthesisEngine.stop();
    }

	/**
	 * Test attenuation.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	public void testAttenuation() throws InterruptedException {
		circuit.start();

		circuit.setAttenuation(-12);
		Thread.sleep(1000);
		circuit.setAttenuation(-6);
		Thread.sleep(1000);
		circuit.setAttenuation(0);
		Thread.sleep(1000);
		circuit.setAttenuation(6);
		Thread.sleep(1000);
		circuit.setAttenuation(12);
		Thread.sleep(1000);
		
		circuit.stop();
	}
	

	/**
	 * Test mute.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	public void testMute() throws InterruptedException {
		for (int i = 5; i > 0; i--) {
			circuit.start();
			Thread.sleep(1000);
			circuit.stop();
			Thread.sleep(1000);
		}
	}
}
