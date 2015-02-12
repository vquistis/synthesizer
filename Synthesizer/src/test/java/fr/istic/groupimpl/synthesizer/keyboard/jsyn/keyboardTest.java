package fr.istic.groupimpl.synthesizer.keyboard.jsyn;

import junit.framework.TestCase;

import com.jsyn.engine.SynthesisEngine;
import com.jsyn.unitgen.LineOut;

import fr.istic.groupimpl.synthesizer.keyboard.JSynKeyboard;
import fr.istic.groupimpl.synthesizer.vco.jsyn.VCOCircuit;

public class keyboardTest extends TestCase {
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

    public void testkeyboard() throws InterruptedException {
    	
    	VCOCircuit vcoCircuit = new VCOCircuit();
    	synthesisEngine.add(vcoCircuit);
    	LineOut lineOut = new LineOut();
        JSynKeyboard keyboard = new JSynKeyboard();
        synthesisEngine.add(keyboard);
        
        synthesisEngine.add( lineOut = new LineOut() );
        vcoCircuit.getOutput().connect( 0, lineOut.input, 0 );
        vcoCircuit.getOutput().connect( 0, lineOut.input, 1 );
        
        keyboard.getOutput().connect(vcoCircuit.getInputFM());
        
        vcoCircuit.getInputF0().set(261);
        vcoCircuit.getInputFM().set(-1.1);
        vcoCircuit.getInputOctave().set(0.0);
        vcoCircuit.getInputAmplitude().set(1);
        vcoCircuit.getInputShape().set(3); // 1 | 2 | 3
        
         synthesisEngine.start();
        
        // pull from final UnitGenerator
        lineOut.start();     
             	
    	
	    try {
	    	for (int i=0; i<13 ; i++) {
	    		synthesisEngine.sleepUntil( synthesisEngine.getCurrentTime() + 1 );
	    		keyboard.getKey().set(i);
	    		
	    		//assertEquals("select ouput value", 1.0, oVCOCircuit.getOutput().get(), tolerance);
	    	}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
