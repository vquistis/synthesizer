package fr.istic.groupimpl.synthesizer.player.jsyn;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jsyn.engine.SynthesisEngine;

import fr.istic.groupimpl.synthesizer.util.SignalUtil;

public class PlayerGateTest {

	private String sampleFileTest = "src/main/resources/sound/junit_PlayerTest.wav";
	
    /** The synthesis engine. */
    SynthesisEngine synthesisEngine;
	
	@Before
	public void init() {
		synthesisEngine = new SynthesisEngine();
	}

	@After
	public void after() {
		synthesisEngine.stop();
	}
	
	@Test
    public void testPlayer() throws InterruptedException, IOException {
    	double sigMin = 0.00001;
    	double sigMax = 1;
        
        PlayerGate playerGate = new PlayerGate(sigMin, sigMax);
        synthesisEngine.add(playerGate);
        synthesisEngine.start();

		File sampleFile = new File( sampleFileTest );
		playerGate.loadSample(sampleFile);
		
        playerGate.play();
        synthesisEngine.sleepFor(0.2);
        assertTrue("is running ?", playerGate.dataQueue.hasMore());
        playerGate.stop();
        synthesisEngine.sleepFor(0.2);
        assertFalse("is running ?", playerGate.dataQueue.hasMore());
    }
	
	@Test
    public void testGate() throws InterruptedException, IOException {
    	double sigMin = 0.00001;
    	double sigMax = 1;
        
        PlayerGate playerGate = new PlayerGate(sigMin, sigMax);
        synthesisEngine.add(playerGate);
        synthesisEngine.start();

		File sampleFile = new File( sampleFileTest );
		playerGate.loadSample(sampleFile);
		
        // pull from final UnitGenerator
        playerGate.start();
        
        synthesisEngine.sleepFor(0.2);
        playerGate.getGate().set(1);
        synthesisEngine.sleepFor(0.2);
        assertTrue("is running ?", playerGate.dataQueue.hasMore());
    }
	
	@Test
    public void testGateMin() throws InterruptedException, IOException {
    	double sigMin = 0.1;
    	double sigMax = 1;
        
        PlayerGate playerGate = new PlayerGate(sigMin, sigMax);
        synthesisEngine.add(playerGate);
        synthesisEngine.start();

		File sampleFile = new File( sampleFileTest );
		playerGate.loadSample(sampleFile);
		
		playerGate.getGate().set((sigMin/SignalUtil.COEF_VOLT)+0.1); // more than sigMin
		
        // pull from final UnitGenerator
        playerGate.start();
        
        synthesisEngine.sleepFor(0.2);
        playerGate.getGate().set(1);
        synthesisEngine.sleepFor(0.2);
        assertFalse("is running ?", playerGate.dataQueue.hasMore());
    }
	
	@Test
    public void testGateMax() throws InterruptedException, IOException {
    	double sigMin = 0.1;
    	double sigMax = 1;
        
        PlayerGate playerGate = new PlayerGate(sigMin, sigMax);
        synthesisEngine.add(playerGate);
        synthesisEngine.start();

		File sampleFile = new File( sampleFileTest );
		playerGate.loadSample(sampleFile);
		
        // pull from final UnitGenerator
        playerGate.start();
        
        synthesisEngine.sleepFor(0.2);
        playerGate.getGate().set((sigMax/SignalUtil.COEF_VOLT)-0.1);  // more than sigMax
        synthesisEngine.sleepFor(0.2);
        assertFalse("is running ?", playerGate.dataQueue.hasMore());
    }

	@Test
    public void testGateWithoutSampleFile() throws InterruptedException {
    	double sigMin = 0.1;
    	double sigMax = 1;
        
        PlayerGate playerGate = new PlayerGate(sigMin, sigMax);
        synthesisEngine.add(playerGate);
        synthesisEngine.start();
		
        // pull from final UnitGenerator
        playerGate.start();
        
        synthesisEngine.sleepFor(0.2);
        playerGate.getGate().set(1);
        synthesisEngine.sleepFor(0.2);
        assertFalse("is running ?", playerGate.dataQueue.hasMore());
    }
	
	@Test
    public void testPlayerWithoutSampleFile() throws InterruptedException {
    	double sigMin = 0.1;
    	double sigMax = 1;
        
        PlayerGate playerGate = new PlayerGate(sigMin, sigMax);
        synthesisEngine.add(playerGate);
        synthesisEngine.start();
		
        // pull from final UnitGenerator
        playerGate.start();
        
        synthesisEngine.sleepFor(0.2);
        playerGate.play();
        synthesisEngine.sleepFor(0.2);
        assertFalse("is running ?", playerGate.dataQueue.hasMore());
        
        synthesisEngine.sleepFor(0.2);
        playerGate.stop();
        assertFalse("is running ?", playerGate.dataQueue.hasMore());
    }
	
	@Test
	public void testOutput() {
    	double sigMin = 0.00001;
    	double sigMax = 1;
        
        PlayerGate playerGate = new PlayerGate(sigMin, sigMax);
        
	    assertNotNull("L'instance est créée", playerGate.getOutput());
	}
	
	@Test (expected=FileNotFoundException.class)
    public void testPlayer_WrongPathFile() throws InterruptedException, IOException {
    	double sigMin = 0.00001;
    	double sigMax = 1;
        
        PlayerGate playerGate = new PlayerGate(sigMin, sigMax);
        
		File sampleFile = new File( "error" + sampleFileTest );
		playerGate.loadSample(sampleFile);
     }
}