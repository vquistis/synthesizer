package fr.istic.groupimpl.synthesizer.vco.jsyn;

import junit.framework.TestCase;

import com.jsyn.engine.SynthesisEngine;
import com.jsyn.unitgen.LineOut;

public class VCOCircuitTest extends TestCase {
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

    public void testVCOCircuit() throws InterruptedException {
    	LineOut lineOut = null;
        double tolerance = 0.002;
        boolean activelineOut = true;
    	double time;
    	
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
        oVCOCircuit.getInputAmplitude().set(10);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
        if (activelineOut) {
        	lineOut.stop();
        }
    }
    /*
    public void testVCOCircuit_NamedPort() throws InterruptedException {
    	LineOut lineOut = null;
        double tolerance = 0.002;
        boolean activelineOut = true;
    	double time;
    	
        VCOCircuit oVCOCircuit = new VCOCircuit();
        synthesisEngine.add(oVCOCircuit);
        
        
        
        oVCOCircuit.getOutput().setName("vco_output");
        
        oVCOCircuit.getPorts().contains(oVCOCircuit.getOutput());
        assertEquals("vco_output", oVCOCircuit.getPortByName("outputAmplitude").getName());
        assertEquals("vco_output", oVCOCircuit.getPortByName("vco_output").getName());
        
    }
    */
}
