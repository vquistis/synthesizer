package fr.istic.groupimpl.synthesizer.util.jsyn;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class JsynAttenuationFilterTest {

	private JsynAttenuationFilter attenuator;
	private int nbInput;

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

	@Test
	public void testAttenuationDiv4() {
		attenuator.set(-12); // attenuation -12db = input/4
		testGenerate(1.0/4);
	}
	
	@Test
	public void testAttenuationDiv2() {
		attenuator.set(-6); // attenuation -6db = input/2
		testGenerate(1.0/2);
	}
	
	@Test
	public void testAttenuation0() {
		attenuator.set(0); // pas d'attenuation
		testGenerate(1);
	}
	
	@Test
	public void testAttenuationMul2() {
		attenuator.set(6); // attenuation +6db = input*2
		testGenerate(2);
	}
	
	@Test
	public void testAttenuationMul4() {
		attenuator.set(12); // attenuation +12db = input*4
		testGenerate(4);
	}
	
	private void testGenerate(double coef) {
		attenuator.generate(0, nbInput+1);
		for (int i = 0; i < nbInput+1; i++) {
			assertEquals(attenuator.input.getValues()[i]*coef, attenuator.output.getValues()[i], 0.0001);
		}
	}
}
