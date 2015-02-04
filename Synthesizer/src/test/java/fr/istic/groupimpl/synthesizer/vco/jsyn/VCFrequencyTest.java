package fr.istic.groupimpl.synthesizer.vco.jsyn;

import junit.framework.TestCase;

import com.jsyn.engine.SynthesisEngine;

public class VCFrequencyTest extends TestCase {
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

    public void testVCFrequency_f0() throws InterruptedException {
        double tolerance = 0.002;
        double f0Tested = 0.0;
        double fmTested = 0.0;
        double OctaveTested = 0.0;
        
        VCFrequency oVCFrequency = new VCFrequency();
        synthesisEngine.add(oVCFrequency);
          	
        synthesisEngine.start();
        double startTime = synthesisEngine.getCurrentTime();
        // pull from final UnitGenerator
        oVCFrequency.start();
 
        f0Tested = 0.0;
        oVCFrequency.getInputf0().set(f0Tested);
        oVCFrequency.getInputfm().set(fmTested);
        oVCFrequency.getInputOctave().set(OctaveTested);
        synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 0.1);
        assertEquals("oVCFrequency ouput value", f0Tested, oVCFrequency.getOutput().get(), tolerance);
        
        f0Tested = 220.0;
        oVCFrequency.getInputf0().set(f0Tested);
        synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 0.1);
        assertEquals("oVCFrequency ouput value", f0Tested, oVCFrequency.getOutput().get(), tolerance);
        
        f0Tested = 440.0;
        oVCFrequency.getInputf0().set(f0Tested);
        synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 0.1);
        assertEquals("oVCFrequency ouput value", f0Tested, oVCFrequency.getOutput().get(), tolerance);
        
        f0Tested = 20000.0;
        oVCFrequency.getInputf0().set(f0Tested);
        synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 0.1);
        assertEquals("oVCFrequency ouput value", f0Tested, oVCFrequency.getOutput().get(), tolerance);
    }
    
    public void testVCFrequency_Octave() throws InterruptedException {
        double tolerance = 0.002;
        double f0Tested = 0.0;
        double fmTested = 0.0;
        double OctaveTested = 0.0;
        
        VCFrequency oVCFrequency = new VCFrequency();
        synthesisEngine.add(oVCFrequency);
          	
        synthesisEngine.start();
        double startTime = synthesisEngine.getCurrentTime();
        // pull from final UnitGenerator
        oVCFrequency.start();
        
        f0Tested = 440.0;
        OctaveTested = 0.0;
        oVCFrequency.getInputf0().set(f0Tested);
        oVCFrequency.getInputfm().set(fmTested);
        oVCFrequency.getInputOctave().set(OctaveTested);
        synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 0.1);
        assertEquals("oVCFrequency ouput value", f0Tested*Math.pow(2, OctaveTested), oVCFrequency.getOutput().get(), tolerance);
        
        f0Tested = 440.0;
        OctaveTested = 1.0;
        oVCFrequency.getInputf0().set(f0Tested);
        oVCFrequency.getInputOctave().set(OctaveTested);
        synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 0.1);
        assertEquals("oVCFrequency ouput value", f0Tested*Math.pow(2, OctaveTested), oVCFrequency.getOutput().get(), tolerance);
        
        f0Tested = 440.0;
        OctaveTested = 2.0;
        oVCFrequency.getInputf0().set(f0Tested);
        oVCFrequency.getInputOctave().set(OctaveTested);
        synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 0.1);
        assertEquals("oVCFrequency ouput value", f0Tested*Math.pow(2, OctaveTested), oVCFrequency.getOutput().get(), tolerance);
        
        f0Tested = 440.0;
        OctaveTested = 3.0;
        oVCFrequency.getInputf0().set(f0Tested);
        oVCFrequency.getInputOctave().set(OctaveTested);
        synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 0.1);
        assertEquals("oVCFrequency ouput value", f0Tested*Math.pow(2, OctaveTested), oVCFrequency.getOutput().get(), tolerance);
        
        f0Tested = 440.0;
        OctaveTested = 4.0;
        oVCFrequency.getInputf0().set(f0Tested);
        oVCFrequency.getInputOctave().set(OctaveTested);
        synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 0.1);
        assertEquals("oVCFrequency ouput value", f0Tested*Math.pow(2, OctaveTested), oVCFrequency.getOutput().get(), tolerance);
        
        f0Tested = 440.0;
        OctaveTested = 5.0;
        oVCFrequency.getInputf0().set(f0Tested);
        oVCFrequency.getInputOctave().set(OctaveTested);
        synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 0.1);
        assertEquals("oVCFrequency ouput value", f0Tested*Math.pow(2, OctaveTested), oVCFrequency.getOutput().get(), tolerance);
       
        f0Tested = 440.0;
        OctaveTested = 6.0;
        oVCFrequency.getInputf0().set(f0Tested);
        oVCFrequency.getInputOctave().set(OctaveTested);
        synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 0.1);
        assertEquals("oVCFrequency ouput value", f0Tested*Math.pow(2, OctaveTested), oVCFrequency.getOutput().get(), tolerance);
    }
    
    public void testVCFrequency_fm() throws InterruptedException {
        double tolerance = 0.002;
        double f0Tested = 0.0;
        double fmTested = 0.0;
        double OctaveTested = 0.0;
        
        VCFrequency oVCFrequency = new VCFrequency();
        synthesisEngine.add(oVCFrequency);
          	
        synthesisEngine.start();
        double startTime = synthesisEngine.getCurrentTime();
        // pull from final UnitGenerator
        oVCFrequency.start();
        
        f0Tested = 440.0;
        fmTested = 0.0;
        oVCFrequency.getInputf0().set(f0Tested);
        oVCFrequency.getInputfm().set(fmTested);
        oVCFrequency.getInputOctave().set(OctaveTested);
        synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 0.1);
        assertEquals("oVCFrequency ouput value", f0Tested*Math.pow(2, fmTested), oVCFrequency.getOutput().get(), tolerance);
       
        f0Tested = 440.0;
        fmTested = 0.5;
        oVCFrequency.getInputf0().set(f0Tested);
        oVCFrequency.getInputfm().set(fmTested);
        oVCFrequency.getInputOctave().set(OctaveTested);
        synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 0.1);
        assertEquals("oVCFrequency ouput value", f0Tested*Math.pow(2, fmTested), oVCFrequency.getOutput().get(), tolerance);
        
        f0Tested = 440.0;
        fmTested = 1;
        oVCFrequency.getInputf0().set(f0Tested);
        oVCFrequency.getInputfm().set(fmTested);
        oVCFrequency.getInputOctave().set(OctaveTested);
        synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 0.1);
        assertEquals("oVCFrequency ouput value", f0Tested*Math.pow(2, fmTested), oVCFrequency.getOutput().get(), tolerance);
        
        f0Tested = 440.0;
        fmTested = -0.5;
        oVCFrequency.getInputf0().set(f0Tested);
        oVCFrequency.getInputfm().set(fmTested);
        oVCFrequency.getInputOctave().set(OctaveTested);
        synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 0.1);
        assertEquals("oVCFrequency ouput value", f0Tested*Math.pow(2, fmTested), oVCFrequency.getOutput().get(), tolerance);
        
        f0Tested = 440.0;
        fmTested = -1;
        oVCFrequency.getInputf0().set(f0Tested);
        oVCFrequency.getInputfm().set(fmTested);
        oVCFrequency.getInputOctave().set(OctaveTested);
        synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 0.1);
        assertEquals("oVCFrequency ouput value", f0Tested*Math.pow(2, fmTested), oVCFrequency.getOutput().get(), tolerance);
    }
}