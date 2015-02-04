package fr.istic.groupimpl.synthesizer.out;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.SineOscillator;

public class ModelOutTest extends ModelOut {

	@Before
	public void init() {
		Synthesizer synth = JSyn.createSynthesizer();
		SineOscillator osc = new SineOscillator();
		osc.output.connect(super.getInputPort("input_out"));
		synth.add(osc);
		synth.add(super.getUnitGenerator());
		synth.start();
		super.getUnitGenerator().start();
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
}
