package fr.istic.groupimpl.synthesizer.rep;

import junit.framework.TestCase;

import com.jsyn.engine.SynthesisEngine;

import fr.istic.groupimpl.synthesizer.rep.jsyn.JsynRepCircuit;

/**
 * The Class REPTest.
 */
public class REPTest extends TestCase {
	
	/** The synthesis engine. */
	SynthesisEngine synthesisEngine;
	
	/** The value tested. */
	double valueTested;
	
	/** The in tested. */
	double inTested;  
	
	/** The out1 tested. */
	double out1Tested ; 
	
	/** The out2 tested. */
	double out2Tested ;
	
	/** The out3 tested. */
	double out3Tested;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		synthesisEngine = new SynthesisEngine();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		synthesisEngine.stop();
	}

	/**
	 * test of intput in .
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	public void testRep_in() throws InterruptedException {

		JsynRepCircuit rep = new JsynRepCircuit();
		synthesisEngine.add(rep);

		synthesisEngine.start();
		rep.start();

		valueTested = -1.0;
		inTested = -1.0;
		rep.getInput().set(inTested);
		synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 1);
		assertEquals("rep in value", valueTested, rep.getInput().getValue());

		valueTested = 0.0;
		inTested = 0.0;
		rep.getInput().set(inTested);
		synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 1);
		assertEquals("vca in value", valueTested, rep.getInput().getValue());

		valueTested = 1.0;
		inTested = 1.0;
		rep.getInput().set(inTested);
		synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 1);
		assertEquals("vca in value", valueTested, rep.getInput().getValue());
	}
	
	/**
	 * test of output.
	 *
	 * @throws InterruptedException the interrupted exception
	 */
	public void testREP_out() throws InterruptedException {

		JsynRepCircuit rep = new JsynRepCircuit();
		synthesisEngine.add(rep);

		synthesisEngine.start();

		// pull from final UnitGenerator
		rep.start();

		// in = -1.0
		valueTested = -1.0;
		inTested = -1.0;  
		rep.getInput().set(inTested);
		synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 1);
		assertEquals("rep out1 value", valueTested, rep.getOutput(1).getValue());
		assertEquals("rep out2 value", valueTested, rep.getOutput(2).getValue());
		assertEquals("rep out3 value", valueTested, rep.getOutput(3).getValue());

		// in = 0.0 
		valueTested = 0.0;
		inTested = 0.0;
		rep.getInput().set(inTested);
		synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 1);
		assertEquals("rep out1 value", valueTested, rep.getOutput(1).getValue());
		assertEquals("rep out2 value", valueTested, rep.getOutput(2).getValue());
		assertEquals("rep out3 value", valueTested, rep.getOutput(3).getValue());

		// in = 1.0 
		valueTested = 1.0;
		inTested = 1.0;
		rep.getInput().set(inTested);
		synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 1);
		assertEquals("rep out1 value", valueTested, rep.getOutput(1).getValue());
		assertEquals("rep out2 value", valueTested, rep.getOutput(2).getValue());
		assertEquals("rep out2 value", valueTested, rep.getOutput(3).getValue());
	}
}
