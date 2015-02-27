package fr.istic.groupimpl.synthesizer.mixer.jsyn;

import junit.framework.TestCase;

import com.jsyn.engine.SynthesisEngine;

// TODO: Auto-generated Javadoc
/**
 * The Class MixerTest.
 */
public class MixerTest extends TestCase {
    
    /** The synthesis engine. */
    SynthesisEngine synthesisEngine;

    /**
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        synthesisEngine = new SynthesisEngine();
    }

    /** tear down method
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        synthesisEngine.stop();
    }
    
    /**
     * Test mixer_get number of input port.
     *
     * @throws InterruptedException the interrupted exception
     */
    public void testMixer_getNumberOfInputPort() throws InterruptedException {
        Integer NbPortTested = 3;
        Mixer mixer = new Mixer(NbPortTested);
        
        assertEquals("Get the number of input port", NbPortTested, mixer.getNumberOfInputPort(),0);
    }

    /**
     * Test mixer_get number of input port max.
     *
     * @throws InterruptedException the interrupted exception
     */
    public void testMixer_getNumberOfInputPortMax() throws InterruptedException {
        Integer NbPortTested = 11;
        Mixer mixer = new Mixer(NbPortTested);
        
        assertEquals("Get the number of input port", 10, mixer.getNumberOfInputPort(),0);
    }
    
    /**
     * Test mixer_get number of input port min.
     *
     * @throws InterruptedException the interrupted exception
     */
    public void testMixer_getNumberOfInputPortMin() throws InterruptedException {
        Integer NbPortTested = 1;
        Mixer mixer = new Mixer(NbPortTested);
        
        assertEquals("Get the number of input port", 2, mixer.getNumberOfInputPort(),0);
    }
    
    /**
     * Test mixer_ sum.
     *
     * @throws InterruptedException the interrupted exception
     */
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
    
    /**
     * Test mixer_ mute many.
     *
     * @throws InterruptedException the interrupted exception
     */
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
    
    /**
     * Test mixer_ mute all.
     *
     * @throws InterruptedException the interrupted exception
     */
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
    
    /**
     * Test mixer_ attenuator.
     *
     * @throws InterruptedException the interrupted exception
     */
    public void testMixer_Attenuator() throws InterruptedException {
    	double tolerance = 0.01;
        Integer NbPortTested = 3;
        Mixer mixer = new Mixer(NbPortTested);
        synthesisEngine.add(mixer);
       
        // replicator VCO to mixer
        mixer.getInput(0).set(1); // amplitude = 1
        mixer.getInput(1).set(1); // amplitude = 1
        mixer.getInput(2).set(1); // amplitude = 1
        
        mixer.setAttenuation(0, -6); // -6db => amplitude/2
        mixer.setAttenuation(1, -6); // -6db => amplitude/2
        mixer.setAttenuation(2, -6); // -6db => amplitude/2
        
        synthesisEngine.start();
        mixer.start();
		
        synthesisEngine.sleepUntil( synthesisEngine.getCurrentTime() + 0.1 );
        
		// is the sum correct ?
		assertEquals("mixer out value", 1.5, mixer.getOutput().get(), tolerance);

        mixer.setAttenuation(0, -6); // -6db => amplitude/2
        mixer.setAttenuation(1, 0);
        mixer.setAttenuation(2, 0);
        
        synthesisEngine.start();
        mixer.start();
		
        synthesisEngine.sleepUntil( synthesisEngine.getCurrentTime() + 0.1 );
        
		// is the sum correct ?
		assertEquals("mixer out value", 2.5, mixer.getOutput().get(), tolerance);
    }
    
    /**
     * Test mixer_ average output value.
     *
     * @throws InterruptedException the interrupted exception
     */
    public void testMixer_AverageOutputValue() throws InterruptedException {
    	double tolerance = 0.01;
        Integer NbPortTested = 3;
        Mixer mixer = new Mixer(NbPortTested);
        synthesisEngine.add(mixer);
       
        // replicator VCO to mixer
        mixer.getInput(0).set(1); // amplitude = 1
        mixer.getInput(1).set(1); // amplitude = 1
        mixer.getInput(2).set(1); // amplitude = 1

        synthesisEngine.start();
        mixer.start();
		
        synthesisEngine.sleepUntil( synthesisEngine.getCurrentTime() + 0.1 );
        
		// is the sum correct ?
		assertEquals("mixer out value", 3, mixer.getAverageOutputValue().get(), tolerance);

        mixer.setAttenuation(0, -6); // -6db => amplitude/2
        mixer.setAttenuation(1, 0);
        mixer.setAttenuation(2, 0);
        
        synthesisEngine.start();
        mixer.start();
		
        synthesisEngine.sleepUntil( synthesisEngine.getCurrentTime() + 0.1 );
        
		// is the sum correct ?
		assertEquals("mixer out value", 2.5, mixer.getAverageOutputValue().get(), tolerance);
    }
}
