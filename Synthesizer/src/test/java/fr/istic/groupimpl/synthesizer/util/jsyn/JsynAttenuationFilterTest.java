package fr.istic.groupimpl.synthesizer.util.jsyn;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import fr.istic.groupimpl.synthesizer.util.SignalUtil;

/**
 * The Class JsynAttenuationFilterTest.
 */
public class JsynAttenuationFilterTest {

	/** The attenuator. */
	private JsynAttenuationFilter attenuator;
	
	/** The nb input. */
	private int nbInput;

	/**
	 * Inits the.
	 */
	@Before
	public void init() {
		attenuator = new JsynAttenuationFilter();
		attenuator.input.set(0.3);
		attenuator.input.set(3.0);
		attenuator.input.set(1.8);
		attenuator.input.set(2.4);
		attenuator.input.set(1.1);
		attenuator.input.set(0.6);
		nbInput = 6;
	}

	/**
	 * Test attenuation div4.
	 */
	@Test
	public void testAttenuationDiv4() {
		attenuator.set(-12); // attenuation -12db = input/4
		assertEquals(-12, attenuator.get(), 0.0001);
		testGenerate(1.0/4);
	}
	
	/**
	 * Test attenuation div2.
	 */
	@Test
	public void testAttenuationDiv2() {
		attenuator.set(-6); // attenuation -6db = input/2
		assertEquals(-6, attenuator.get(), 0.0001);
		testGenerate(1.0/2);
	}
	
	/**
	 * Test attenuation0.
	 */
	@Test
	public void testAttenuation0() {
		attenuator.set(0); // pas d'attenuation
		assertEquals(0, attenuator.get(), 0.0001);
		testGenerate(1);
	}
	
	/**
	 * Test attenuation mul2.
	 */
	@Test
	public void testAttenuationMul2() {
		attenuator.set(6); // attenuation +6db = input*2
		assertEquals(6, attenuator.get(), 0.0001);
		testGenerate(2);
	}
	
	/**
	 * Test attenuation mul4.
	 */
	@Test
	public void testAttenuationMul4() {
		attenuator.set(12); // attenuation +12db = input*4
		assertEquals(12, attenuator.get(), 0.0001);
		testGenerate(4);
	}
	
	/**
	 * Test generate.
	 *
	 * @param coef the coef
	 */
	private void testGenerate(double coef) {
		attenuator.generate(0, nbInput+1);
		for (int i = 0; i < nbInput+1; i++) {
			double expected = SignalUtil.verifyAmplitude(attenuator.input.getValues()[i]*coef);
			assertEquals(expected, attenuator.output.getValues()[i], 0.0001);
		}
	}
}
