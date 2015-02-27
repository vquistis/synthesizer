package fr.istic.groupimpl.synthesizer.player;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;

public class PlayerTest {

	private ModelPlayer model;
	private Synthesizer synth;
	private String sampleFileNameTest = "junit_PlayerTest.wav";
	private String sampleFileTest = "src/main/resources/sound/" + sampleFileNameTest;
	
	@Before
	public void init() {
		synth = JSyn.createSynthesizer();
		model = new ModelPlayer();
				
		synth.add(model.getUnitGenerator());
		
		LineOut lineout = new LineOut();
		synth.add(lineout);
		
		lineout.getInput().connect(model.getOutputPort());
		
		synth.start();
		lineout.start();
	}

	@After
	public void after() {
		synth.stop();
	}
	
	@Test
	public void testModel() throws InterruptedException {
		model.loadSample(sampleFileTest);
		model.play();
		
		synth.sleepFor(1);
		
		assertTrue("is running ?", model.isPlayRunning());
		
		synth.sleepFor(8);
		
		model.stop();
		
		assertFalse("is not running ?", model.isPlayRunning());
	}
	
	@Test
	public void testOutput() throws InterruptedException {
		double sumOutput = 0;
		
		model.loadSample(sampleFileTest);
		model.play();
		
		for (int i=0; i<20; i++) {
			synth.sleepUntil(synth.getCurrentTime() + 0.1);
			sumOutput =+ Math.abs(model.getOutputPort().get());
		}
		
		assertTrue("ouput", sumOutput > 0.00);
	}

	@Test
	public void testPlayAndStopWithoutSampleFile() {
		model.play();
		model.stop();
	}
	
	@Test
	public void testSampleFileLoading() {
		model.loadSample("error.wav");
		assertNull("File Name is null ?", model.getSampleFileName().get());
	}
	
	@Test
	public void testSampleFileName() {
		model.loadSample(sampleFileTest);
		assertEquals("File name", model.getSampleFileName().get(), sampleFileNameTest);
	}
	
}