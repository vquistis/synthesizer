package fr.istic.groupimpl.synthesizer.player;

import org.junit.Before;
import org.junit.Test;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;

public class PlayerTest {

	private ModelPlayer model;
	private Synthesizer synth;
	
	@Before
	public void init() {
		synth = JSyn.createSynthesizer();
		model = new ModelPlayer();
		
		model.loadSample("temp_recording.wav");
		
		synth.add(model.getUnitGenerator());
		
		LineOut lineout = new LineOut();
		synth.add(lineout);
		
		lineout.getInput().connect(model.getOutputPort());
		
		synth.start();
		lineout.start();
	}
	
	@Test
	public void testModel() throws InterruptedException {
		model.play();
		synth.sleepFor(8);
		synth.stop();
	}
}