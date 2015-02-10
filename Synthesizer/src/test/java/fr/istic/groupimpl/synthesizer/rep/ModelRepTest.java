package fr.istic.groupimpl.synthesizer.rep;

import org.junit.Before;
import org.junit.Test;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;

public class ModelRepTest {
	private ModelRep model;
	private Synthesizer synth;
	
	@Before
	public void init() {
		synth = JSyn.createSynthesizer();
		model = new ModelRep();
		
		synth.add(model.getUnitGenerator());
		
		LineOut lineout = new LineOut();
		synth.add(lineout);
		
		lineout.getInput().connect(model.getOutputPort("vco_output"));
		
		synth.start();
		lineout.start();
	}
	
	@Test
	public void testModel() throws InterruptedException {
		synth.sleepUntil( synth.getCurrentTime() + 0.5 );
		synth.stop();
	}
}
