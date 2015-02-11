package fr.istic.groupimpl.synthesizer.rep;

import junit.framework.TestCase;

import com.jsyn.engine.SynthesisEngine;

import fr.istic.groupimpl.synthesizer.rep.jsyn.JsynRepCircuit;

public class REPTest extends TestCase {
	SynthesisEngine synthesisEngine;
	double valueTested;
	double inTested;  
	double out1Tested ; 
	double out2Tested ;
	double out3Tested;

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

	/**
	 * test of intput in 
	 * 
	 * @throws InterruptedException
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
	 * test of output
	 * 
	 * @throws InterruptedException
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
