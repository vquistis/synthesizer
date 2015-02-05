package fr.istic.groupimpl.synthesizer.util.jsyn;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class JsynAttenuationFilterTest {

	private JsynAttenuationFilter attenuator;

	@Before
	public void init() {
		attenuator = new JsynAttenuationFilter();
	}

	@Test
	public void testGenerateDiv4() {
		attenuator.set(-12); // attenuation -12db = input/4
		attenuator.input.set(3); // input = 3
		attenuator.generate(0, 1);
		assertEquals(attenuator.input.getValues()[0]/4, attenuator.output.getValues()[0], 0.001);
	}
	
	@Test
	public void testGenerateDiv2() {
		attenuator.set(-6); // attenuation -6db = input/2
		attenuator.input.set(3); // input = 3
		attenuator.generate(0, 1);
		assertEquals(attenuator.input.getValues()[0]/2, attenuator.output.getValues()[0], 0.001);
	}
	
	@Test
	public void testGenerate0() {
		attenuator.set(0); // pas d'attenuation
		attenuator.input.set(3); // input = 3
		attenuator.generate(0, 1);
		assertEquals(attenuator.input.getValues()[0], attenuator.output.getValues()[0], 0.001);
	}
	
	@Test
	public void testGenerateMul2() {
		attenuator.set(6); // attenuation +6db = input*2
		attenuator.input.set(3); // input = 3
		attenuator.generate(0, 1);
		assertEquals(attenuator.input.getValues()[0]*2, attenuator.output.getValues()[0], 0.001);
	}
	
	@Test
	public void testGenerateMul4() {
		attenuator.set(12); // attenuation +12db = input*4
		attenuator.input.set(3); // input = 3
		attenuator.generate(0, 1);
		assertEquals(attenuator.input.getValues()[0]*4, attenuator.output.getValues()[0], 0.001);
	}
}
