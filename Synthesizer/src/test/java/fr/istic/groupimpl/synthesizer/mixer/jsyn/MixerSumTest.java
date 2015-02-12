package fr.istic.groupimpl.synthesizer.mixer.jsyn;

import junit.framework.TestCase;

import com.jsyn.engine.SynthesisEngine;

public class MixerSumTest extends TestCase {
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

    public void testMixerSum() throws InterruptedException {
        double tolerance = 0.002;
        
        MixerSum mixerSum = new MixerSum(4);
        synthesisEngine.add(mixerSum);

        mixerSum.getInput(0).set(1);
        mixerSum.getInput(1).set(1);
        mixerSum.getInput(2).set(1);
        mixerSum.getInput(3).set(1);
        
        synthesisEngine.start();
        // pull from final UnitGenerator
        mixerSum.start();
        
        synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 0.1);
        assertEquals("select ouput value", 4.0, mixerSum.getOutput().get(), tolerance);
        
        mixerSum.getInput(0).set(2);
        
        synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 0.1);
        assertEquals("select ouput value", 5.0, mixerSum.getOutput().get(), tolerance);
 
        mixerSum.getInput(1).set(2);
        
        synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 0.1);
        assertEquals("select ouput value", 6.0, mixerSum.getOutput().get(), tolerance);

        mixerSum.getInput(2).set(2);
        
        synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 0.1);
        assertEquals("select ouput value", 7.0, mixerSum.getOutput().get(), tolerance);
        
        mixerSum.getInput(3).set(2);
        
        synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 0.1);
        assertEquals("select ouput value", 8.0, mixerSum.getOutput().get(), tolerance);
    }
}
