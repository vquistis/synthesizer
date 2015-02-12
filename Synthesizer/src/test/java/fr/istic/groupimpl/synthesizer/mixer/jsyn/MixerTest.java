package fr.istic.groupimpl.synthesizer.mixer.jsyn;

import junit.framework.TestCase;

import com.jsyn.engine.SynthesisEngine;

public class MixerTest extends TestCase {
    SynthesisEngine synthesisEngine;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        synthesisEngine = new SynthesisEngine();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        synthesisEngine.stop();
    }

    public void testMixer_Sum() throws InterruptedException {
    	double tolerance = 0.01;
        Integer NbPortTested = 3;
        Mixer mixer = new Mixer(NbPortTested);
        synthesisEngine.add(mixer);
       
        // replicator VCO to mixer
        mixer.getInput(0).set(1);
        mixer.getInput(1).set(1);
        mixer.getInput(2).set(1);
        
        synthesisEngine.start();
        mixer.start();
		
        synthesisEngine.sleepUntil( synthesisEngine.getCurrentTime() + 0.1 );
        
		// is the sum correct ?
		assertEquals("mixer out value", 3.0, mixer.getOutput().get(), tolerance);

        mixer.getInput(0).set(0.2);
        mixer.getInput(1).set(0.7);
        mixer.getInput(2).set(0.9);
        
        synthesisEngine.sleepUntil( synthesisEngine.getCurrentTime() + 0.1 );
        
		// is the sum correct ?
		assertEquals("mixer out value", 1.8, mixer.getOutput().get(), tolerance);
    }
    
    public void testMixer_MuteMany() throws InterruptedException {   
    	double tolerance = 0.01;
        Integer NbPortTested = 3;
        Mixer mixer = new Mixer(NbPortTested);
        synthesisEngine.add(mixer);
       
        // replicator VCO to mixer
        mixer.getInput(0).set(1);
        mixer.getInput(1).set(1);
        mixer.getInput(2).set(1);
        
        mixer.setMute(0, false);
        mixer.setMute(1, true);
        mixer.setMute(2, true);
        
        synthesisEngine.start();
        mixer.start();
		
        synthesisEngine.sleepUntil( synthesisEngine.getCurrentTime() + 0.1 );
        
		// is the sum correct ?
		assertEquals("mixer out value", 1.0, mixer.getOutput().get(), tolerance);

        mixer.getInput(0).set(0.2);
        mixer.getInput(1).set(0.7);
        mixer.getInput(2).set(0.9);

        mixer.setMute(0, true);
        mixer.setMute(1, false);
        mixer.setMute(2, true);
        
        synthesisEngine.sleepUntil( synthesisEngine.getCurrentTime() + 0.1 );
        
		// is the sum correct ?
		assertEquals("mixer out value", 0.7, mixer.getOutput().get(), tolerance);
    }
    
    public void testMixer_MuteAll() throws InterruptedException {
    	double tolerance = 0.01;
        Integer NbPortTested = 3;
        Mixer mixer = new Mixer(NbPortTested);
        synthesisEngine.add(mixer);
       
        // replicator VCO to mixer
        mixer.getInput(0).set(1);
        mixer.getInput(1).set(1);
        mixer.getInput(2).set(1);
        
        mixer.setMute(0, true);
        mixer.setMute(1, true);
        mixer.setMute(2, true);
        
        synthesisEngine.start();
        mixer.start();
		
        synthesisEngine.sleepUntil( synthesisEngine.getCurrentTime() + 0.1 );
        
		// is the sum correct ?
		assertEquals("mixer out value", 0.0, mixer.getOutput().get(), tolerance);
		
        mixer.getInput(0).set(0.2);
        mixer.getInput(1).set(0.7);
        mixer.getInput(2).set(0.9);

        synthesisEngine.sleepUntil( synthesisEngine.getCurrentTime() + 0.1 );
        
		// is the sum correct ?
		assertEquals("mixer out value", 0.0, mixer.getOutput().get(), tolerance);
		
    }
}
