package fr.istic.groupimpl.synthesizer.mixer.jsyn;

import junit.framework.TestCase;

import com.jsyn.engine.SynthesisEngine;
import com.jsyn.unitgen.LineOut;

import fr.istic.groupimpl.synthesizer.rep.jsyn.JsynRepCircuit;
import fr.istic.groupimpl.synthesizer.vco.jsyn.VCOCircuit;

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
    	LineOut lineOut = null;
        double tolerance = 0.002;
        boolean activelineOut = true;
        Integer NbPortTested = 3;
    	
        VCOCircuit oVCOCircuit = new VCOCircuit();
        synthesisEngine.add(oVCOCircuit);
        
		JsynRepCircuit rep = new JsynRepCircuit();
		synthesisEngine.add(rep);
        
        Mixer mixer = new Mixer(NbPortTested);
        synthesisEngine.add(mixer);
        
        oVCOCircuit.getInputF0().set(880);
        oVCOCircuit.getInputFM().set(0.0);
        oVCOCircuit.getInputOctave().set(0.0);
        oVCOCircuit.getInputAmplitude().set(1);
        oVCOCircuit.getInputShape().set(2); // 1 | 2 | 3
       
        // connect VCO to replicator
        rep.getInput().connect(oVCOCircuit.getOutput());
        // replicator VCO to mixer
        mixer.getInput(0).connect(rep.getOutput(1));
        mixer.getInput(1).connect(rep.getOutput(2));
        mixer.getInput(2).connect(rep.getOutput(3));
        
        synthesisEngine.start();
        mixer.start();
		
	    try {
	    	for (int i=-10; i<=10 ; i++) {
	    		synthesisEngine.sleepUntil( synthesisEngine.getCurrentTime() + 0.133 );
	    		System.out.println((3*oVCOCircuit.getOutput().get()) + " | " + mixer.getOutput().get());
	    		
	    		// is the sum correct ?
	    		assertEquals("mixer out value", (oVCOCircuit.getOutput().get() * NbPortTested), mixer.getOutput().get(), tolerance);
	    		
	    		// modulation frequency
	    		oVCOCircuit.getInputFM().set(0.1*i);
	    	}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    public void testMixer_MuteMany() throws InterruptedException {
    	LineOut lineOut = null;
        double tolerance = 0.005; // tolerate the decibel to coef transformation
        boolean activelineOut = true;
        Integer NbPortTested = 3;
    	
        VCOCircuit oVCOCircuit = new VCOCircuit();
        synthesisEngine.add(oVCOCircuit);
        
		JsynRepCircuit rep = new JsynRepCircuit();
		synthesisEngine.add(rep);
        
        Mixer mixer = new Mixer(NbPortTested);
        synthesisEngine.add(mixer);
        
        oVCOCircuit.getInputF0().set(880);
        oVCOCircuit.getInputFM().set(0.0);
        oVCOCircuit.getInputOctave().set(0.0);
        oVCOCircuit.getInputAmplitude().set(1);
        oVCOCircuit.getInputShape().set(2); // 1 | 2 | 3
       
        // connect VCO to replicator
        rep.getInput().connect(oVCOCircuit.getOutput());
        // replicator VCO to mixer
        mixer.getInput(0).connect(rep.getOutput(1));
        mixer.getInput(1).connect(rep.getOutput(2));
        mixer.getInput(2).connect(rep.getOutput(3));
        
        mixer.setMute(0, false);
        mixer.setMute(1, true);
        mixer.setMute(2, true);
        
        synthesisEngine.start();
        mixer.start();
		
	    try {
	    	for (int i=-10; i<=10 ; i++) {
	    		synthesisEngine.sleepUntil( synthesisEngine.getCurrentTime() + 0.133 );
	    		System.out.println((oVCOCircuit.getOutput().get()) + " | " + mixer.getOutput().get());
	    		
	    		// is the sum correct ?
	    		assertEquals("mixer out value", oVCOCircuit.getOutput().get(), mixer.getOutput().get(), tolerance);
	    		
	    		// modulation frequency
	    		oVCOCircuit.getInputFM().set(0.1*i);
	    	}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    public void testMixer_MuteAll() throws InterruptedException {
    	LineOut lineOut = null;
        double tolerance = 0.005; // tolerate the decibel to coef transformation
        boolean activelineOut = true;
        Integer NbPortTested = 3;
    	
        VCOCircuit oVCOCircuit = new VCOCircuit();
        synthesisEngine.add(oVCOCircuit);
        
		JsynRepCircuit rep = new JsynRepCircuit();
		synthesisEngine.add(rep);
        
        Mixer mixer = new Mixer(NbPortTested);
        synthesisEngine.add(mixer);
        
        oVCOCircuit.getInputF0().set(880);
        oVCOCircuit.getInputFM().set(0.0);
        oVCOCircuit.getInputOctave().set(0.0);
        oVCOCircuit.getInputAmplitude().set(1);
        oVCOCircuit.getInputShape().set(2); // 1 | 2 | 3
       
        // connect VCO to replicator
        rep.getInput().connect(oVCOCircuit.getOutput());
        // replicator VCO to mixer
        mixer.getInput(0).connect(rep.getOutput(1));
        mixer.getInput(1).connect(rep.getOutput(2));
        mixer.getInput(2).connect(rep.getOutput(3));
        
        mixer.setMute(0, true);
        mixer.setMute(1, true);
        mixer.setMute(2, true);
        
        synthesisEngine.start();
        mixer.start();
		
	    try {
	    	for (int i=-10; i<=10 ; i++) {
	    		synthesisEngine.sleepUntil( synthesisEngine.getCurrentTime() + 0.133 );
	    		System.out.println((3*oVCOCircuit.getOutput().get()) + " | " + mixer.getOutput().get());
	    		
	    		// is the sum correct ?
	    		assertEquals("mixer out value", 0, mixer.getOutput().get(), tolerance);
	    		
	    		// modulation frequency
	    		oVCOCircuit.getInputFM().set(0.1*i);
	    	}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
}
