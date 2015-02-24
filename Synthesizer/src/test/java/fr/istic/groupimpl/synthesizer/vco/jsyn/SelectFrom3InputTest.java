package fr.istic.groupimpl.synthesizer.vco.jsyn;

import junit.framework.TestCase;

import com.jsyn.engine.SynthesisEngine;

/**
 * The Class SelectFrom3InputTest.
 */
public class SelectFrom3InputTest extends TestCase {
    
    /** The synthesis engine. */
    SynthesisEngine synthesisEngine;

    /**
     *  set up method.
     *
     * @throws Exception the exception
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        synthesisEngine = new SynthesisEngine();
    }

    /**
     *  tear down method.
     *
     * @throws Exception the exception
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        synthesisEngine.stop();
    }

    /**
     * Test select from3 input.
     *
     * @throws InterruptedException the interrupted exception
     */
    public void testSelectFrom3Input() throws InterruptedException {
        double tolerance = 0.002;
        
        SelectFrom3Input select = new SelectFrom3Input();
        synthesisEngine.add(select);

        select.getInput1().set(1.0);
        select.getInput2().set(2.0);
        select.getInput3().set(3.0);
       
        synthesisEngine.start();
//        double startTime = synthesisEngine.getCurrentTime();
        // pull from final UnitGenerator
        select.start();
        
        select.getInputSelect().set(1.0); // input 1
        synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 0.1);
        assertEquals("select ouput value", 1.0, select.getOutput().get(), tolerance);
        
        select.getInputSelect().set(2.0); // input 2
        synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 0.1);
        assertEquals("select ouput value", 2.0, select.getOutput().get(), tolerance);

        select.getInputSelect().set(3.0); // input 3
        synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 0.1);
        assertEquals("select ouput value", 3.0, select.getOutput().get(), tolerance);
    }
}
