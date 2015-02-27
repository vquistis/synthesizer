package fr.istic.groupimpl.synthesizer.vco.jsyn;

import junit.framework.TestCase;

import com.jsyn.engine.SynthesisEngine;
import com.jsyn.unitgen.LineOut;

/**
 * The Class VCOCircuitTest.
 */
public class VCOCircuitTest extends TestCase {
    
	// turn on or turn off the sound
    private boolean activelineOut = true;
    
    /** The synthesis engine. */
    SynthesisEngine synthesisEngine;

    /** set up method
     * @see junit.framework.TestCase#setUp()
     */
    @Override
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
     * Test vco circuit.
     *
     * @throws InterruptedException the interrupted exception
     */
    public void testVCOCircuit() throws InterruptedException {
    	LineOut lineOut = null;
        boolean activelineOut = true;
    	
        VCOCircuit oVCOCircuit = new VCOCircuit();
        synthesisEngine.add(oVCOCircuit);
        
        if (activelineOut) {
        	// Add a stereo audio output unit.
	        synthesisEngine.add( lineOut = new LineOut() );
	        oVCOCircuit.getOutput().connect( 0, lineOut.input, 0 );
	        oVCOCircuit.getOutput().connect( 0, lineOut.input, 1 );
        }
        
        oVCOCircuit.getInputF0().set(440);
        oVCOCircuit.getInputFM().set(-1.1);
        oVCOCircuit.getInputOctave().set(0.0);
        oVCOCircuit.getInputAmplitude().set(1);
        oVCOCircuit.setAmplitude(5); // volt
        oVCOCircuit.getInputShape().set(3); // 1 | 2 | 3
       
        synthesisEngine.start();
        
        // pull from final UnitGenerator
        if (activelineOut) {
        	lineOut.start();
        } else {
            oVCOCircuit.start();        	
        }
    	
	    try {
	    	for (int i=-10; i<=10 ; i++) {
	    		synthesisEngine.sleepUntil( synthesisEngine.getCurrentTime() + 0.1 );
	    		oVCOCircuit.getInputFM().set(0.1*i);
	    		
	    		//assertEquals("select ouput value", 1.0, oVCOCircuit.getOutput().get(), tolerance);
	    	}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    
        if (activelineOut) {
        	lineOut.stop();
        }
    }
    
    /**
     * Test vco circuit : Square signal.
     *
     * @throws InterruptedException the interrupted exception
     */
    public void testVCOCircuit_Square() throws InterruptedException {
    	LineOut lineOut = null;
    	
        VCOCircuit oVCOCircuit = new VCOCircuit();
        synthesisEngine.add(oVCOCircuit);
        
        if (activelineOut) {
        	// Add a stereo audio output unit.
	        synthesisEngine.add( lineOut = new LineOut() );
	        oVCOCircuit.getOutputSquare().connect( 0, lineOut.input, 0 );
	        oVCOCircuit.getOutputSquare().connect( 0, lineOut.input, 1 );
        }
        
        oVCOCircuit.getInputF0().set(440);
        oVCOCircuit.getInputFM().set(-1.1);
        oVCOCircuit.getInputOctave().set(0.0);
        oVCOCircuit.getInputAmplitude().set(1);
        oVCOCircuit.setAmplitude(5); // volt
        oVCOCircuit.getInputShape().set(3); // Square
       
        synthesisEngine.start();
        
        // pull from final UnitGenerator
        if (activelineOut) {
        	lineOut.start();
        } else {
            oVCOCircuit.start();        	
        }
    	
	    try {
	    	for (int i=-10; i<=10 ; i++) {
	    		synthesisEngine.sleepUntil( synthesisEngine.getCurrentTime() + 0.1 );
	    		oVCOCircuit.getInputFM().set(0.1*i);
	    		
	    		//assertEquals("select ouput value", 1.0, oVCOCircuit.getOutput().get(), tolerance);
	    	}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    
        if (activelineOut) {
        	lineOut.stop();
        }
    }

    /**
     * Test vco circuit : Sawtooth signal.
     *
     * @throws InterruptedException the interrupted exception
     */
    public void testVCOCircuit_Sawtooth() throws InterruptedException {
    	LineOut lineOut = null;
    	
        VCOCircuit oVCOCircuit = new VCOCircuit();
        synthesisEngine.add(oVCOCircuit);
        
        if (activelineOut) {
        	// Add a stereo audio output unit.
	        synthesisEngine.add( lineOut = new LineOut() );
	        oVCOCircuit.getOutputSawtooth().connect( 0, lineOut.input, 0 );
	        oVCOCircuit.getOutputSawtooth().connect( 0, lineOut.input, 1 );
        }
        
        oVCOCircuit.getInputF0().set(440);
        oVCOCircuit.getInputFM().set(-1.1);
        oVCOCircuit.getInputOctave().set(0.0);
        oVCOCircuit.getInputAmplitude().set(1);
        oVCOCircuit.setAmplitude(5); // volt
        oVCOCircuit.getInputShape().set(2); // Sawtooth
       
        synthesisEngine.start();
        
        // pull from final UnitGenerator
        if (activelineOut) {
        	lineOut.start();
        } else {
            oVCOCircuit.start();        	
        }
    	
	    try {
	    	for (int i=-10; i<=10 ; i++) {
	    		synthesisEngine.sleepUntil( synthesisEngine.getCurrentTime() + 0.1 );
	    		oVCOCircuit.getInputFM().set(0.1*i);
	    		
	    		//assertEquals("select ouput value", 1.0, oVCOCircuit.getOutput().get(), tolerance);
	    	}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    
        if (activelineOut) {
        	lineOut.stop();
        }
    }

    /**
     * Test vco circuit : Triangle signal.
     *
     * @throws InterruptedException the interrupted exception
     */
    public void testVCOCircuit_Triangle() throws InterruptedException {
    	LineOut lineOut = null;
    	
        VCOCircuit oVCOCircuit = new VCOCircuit();
        synthesisEngine.add(oVCOCircuit);
        
        if (activelineOut) {
        	// Add a stereo audio output unit.
	        synthesisEngine.add( lineOut = new LineOut() );
	        oVCOCircuit.getOutputTriangle().connect( 0, lineOut.input, 0 );
	        oVCOCircuit.getOutputTriangle().connect( 0, lineOut.input, 1 );
        }
        
        oVCOCircuit.getInputF0().set(440);
        oVCOCircuit.getInputFM().set(-1.1);
        oVCOCircuit.getInputOctave().set(0.0);
        oVCOCircuit.getInputAmplitude().set(1);
        oVCOCircuit.setAmplitude(5); // volt
        oVCOCircuit.getInputShape().set(1); // Triangle
       
        synthesisEngine.start();
        
        // pull from final UnitGenerator
        if (activelineOut) {
        	lineOut.start();
        } else {
            oVCOCircuit.start();        	
        }
    	
	    try {
	    	for (int i=-10; i<=10 ; i++) {
	    		synthesisEngine.sleepUntil( synthesisEngine.getCurrentTime() + 0.1 );
	    		oVCOCircuit.getInputFM().set(0.1*i);
	    		
	    		//assertEquals("select ouput value", 1.0, oVCOCircuit.getOutput().get(), tolerance);
	    	}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    
        if (activelineOut) {
        	lineOut.stop();
        }
    }
}
