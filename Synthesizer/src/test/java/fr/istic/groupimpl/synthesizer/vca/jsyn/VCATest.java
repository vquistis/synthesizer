package fr.istic.groupimpl.synthesizer.vca.jsyn;

import junit.framework.TestCase;

import com.jsyn.engine.SynthesisEngine;

public class VCATest extends TestCase {
	SynthesisEngine synthesisEngine;
	double valueTested;
	double inTested;  // compris entre -1 et 1
	double a0Tested ; // compris entre -5 et 5
	double amTested ; // compris entre -1 et 1
	double outTested;

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
	public void testVCA_in() throws InterruptedException {

		VCAJSyn vca = new VCAJSyn();
		synthesisEngine.add(vca);

		synthesisEngine.start();
		vca.start();

		valueTested = -1.0;
		inTested = -1.0;
		vca.getInput().set(inTested);
		synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 1);
		assertEquals("vca in value", valueTested, vca.getInput().getValue());

		valueTested = 0.0;
		inTested = 0.0;
		vca.getInput().set(inTested);
		synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 1);
		assertEquals("vca in value", valueTested, vca.getInput().getValue());

		valueTested = 1.0;
		inTested = 1.0;
		vca.getInput().set(inTested);
		synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 1);
		assertEquals("vca in value", valueTested, vca.getInput().getValue());
	}

	/**
	 * test of intput a0 
	 * 
	 * @throws InterruptedException
	 */
	public void testVCA_a0() throws InterruptedException {

		VCAJSyn vca = new VCAJSyn();
		synthesisEngine.add(vca);

		synthesisEngine.start();
		vca.start();

		valueTested = -5.0;
		a0Tested = -5.0;
		vca.getInputa0().set(a0Tested);
		synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 1);
		assertEquals("vca a0 value", valueTested, vca.getInputa0().getValue());

		valueTested = 0.0;
		a0Tested = 0.0;
		vca.getInputa0().set(a0Tested);
		synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 1);
		assertEquals("vca a0 value", valueTested, vca.getInputa0().getValue());

		valueTested = 1.0;
		a0Tested = 1.0;
		vca.getInputa0().set(a0Tested);
		synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 1);
		assertEquals("vca a0 value", valueTested, vca.getInputa0().getValue());

		valueTested = 5.0;
		a0Tested = 5.0;
		vca.getInputa0().set(a0Tested);
		synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 1);
		assertEquals("vca a0 value", valueTested, vca.getInputa0().getValue());
	}

	/**
	 * test of input am
	 * 
	 * @throws InterruptedException
	 */
//	public void testVCA_am() throws InterruptedException {
//
//		VCAJSyn vca = new VCAJSyn();
//		synthesisEngine.add(vca);
//
//		synthesisEngine.start();
//
//		// pull from final UnitGenerator
//		vca.start();
//
//		valueTested = -1.0;
//		amTested = -1.0;
//		vca.getInputam().set(amTested);
//		synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 1);
//		assertEquals("vca am value", valueTested, vca.getInputam().getValue());
//
//		valueTested = 0.0;
//		amTested = 0.0;
//		vca.getInputam().set(amTested);
//		synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 1);
//		assertEquals("vca am value", valueTested, vca.getInputam().getValue());
//
//		valueTested = 1.0;
//		amTested = 1.0;
//		vca.getInputam().set(amTested);
//		synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 1);
//		assertEquals("vca am value", valueTested, vca.getInputam().getValue());
//	}

	/**
	 * test n°1 of output
	 * if am is disconnected or null then out = 0 
	 * 
	 * @throws InterruptedException
	 */
	public void testVCA_out1() throws InterruptedException {

		VCAJSyn vca = new VCAJSyn();
		synthesisEngine.add(vca);

		synthesisEngine.start();

		// pull from final UnitGenerator
		vca.start();

		valueTested = 0.0;
		synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 1);
		assertEquals("vca out value - with am null", valueTested, vca.getOutput().getValue());
	}

	/**
	 * test of output n°2
	 * if am = 5V and a0 = 0 then out = in 
	 * 
	 * @throws InterruptedException
	 */
	public void testVCA_out2() throws InterruptedException {

		VCAJSyn vca = new VCAJSyn();
		synthesisEngine.add(vca);

		synthesisEngine.start();

		// pull from final UnitGenerator
		vca.start();

		// in = -1.0 
		valueTested = -1.0;
		inTested = -1.0;
		amTested = 1.0;
		a0Tested = 0.0;  
		vca.getInput().set(inTested);
		vca.getInputam().set(amTested);
		vca.getInputa0().set(a0Tested);
		synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 1);
		assertEquals("vca out value - with in = -1 am = 5V & a0 = 0", valueTested, vca.getOutput().getValue());
		assertEquals("vca out value - with in = -1 am = 5V & a0 = 0", vca.getInput().getValue(), vca.getOutput().getValue());

		// in = 0.0 
		valueTested = 0.0;
		inTested = 0.0;
		amTested = 1.0;
		a0Tested = 0.0;  
		vca.getInput().set(inTested);
		vca.getInputam().set(amTested);
		vca.getInputa0().set(a0Tested);
		synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 1);
		assertEquals("vca out value - with in = 0 am = 5 & a0 = 0", valueTested, vca.getOutput().getValue());
		assertEquals("vca out value - with in = 0 am = 5 & a0 = 0", vca.getInput().getValue(), vca.getOutput().getValue());

		// in = 1.0 
		valueTested = 1.0;
		inTested = 1.0;
		amTested = 1.0;
		a0Tested = 0.0;  
		vca.getInput().set(inTested);
		vca.getInputam().set(amTested);
		vca.getInputa0().set(a0Tested);
		synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 1);
		assertEquals("vca out value - with in = 1 am = 5 & a0 = 0", valueTested, vca.getOutput().getValue());
		assertEquals("vca out value - with in = 1 am = 5 & a0 = 0", vca.getInput().getValue(), vca.getOutput().getValue());
		assertEquals("vca out value - with am = 5 & a0 = 0", vca.getInput().getValue(), vca.getOutput().getValue());
		System.out.println("test 2 " + vca.getOutput().get());
	}

	/**
	 * test of output n°3
	 * if am increase of 1V then  out = in * 4
	 * 
	 * @throws InterruptedException
	 */
	public void testVCA_out3() throws InterruptedException {

		VCAJSyn vca = new VCAJSyn();
		synthesisEngine.add(vca);

		synthesisEngine.start();

		// pull from final UnitGenerator
		vca.start();

		// in = -5.0 
		inTested = 0.5;
		amTested = 0.3;
		a0Tested = 0.0;  
		vca.getInput().set(inTested);
		vca.getInputam().set(amTested);
		vca.getInputa0().set(a0Tested);
		
		synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 1);
		double out = vca.getOutput().getValue();
	
		inTested = 0.5;
		amTested = 0.5; // + 0.2
		a0Tested = 0.0;  
		vca.getInput().set(inTested);
		vca.getInputam().set(amTested);
		vca.getInputa0().set(a0Tested);
		
		synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 1);
		double out1 = vca.getOutput().getValue();
		assertEquals("vca out value - with am + 1V ", out*4, out1, 0.0001);
	}
	
	/**
	 * test of output n°4
	 * if am decrease of 1V then  out = in / 4
	 * 
	 * @throws InterruptedException
	 */
	public void testVCA_out4() throws InterruptedException {

		VCAJSyn vca = new VCAJSyn();
		synthesisEngine.add(vca);

		synthesisEngine.start();

		// pull from final UnitGenerator
		vca.start();

		// in = -5.0 
		inTested = 0.5;
		amTested = 0.3;
		a0Tested = 0.0;  
		vca.getInput().set(inTested);
		vca.getInputam().set(amTested);
		vca.getInputa0().set(a0Tested);
		
		synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 1);
		double out = vca.getOutput().getValue();
	
		inTested = 0.5;
		amTested = 0.1; // - 0.2
		a0Tested = 0.0;  
		vca.getInput().set(inTested);
		vca.getInputam().set(amTested);
		vca.getInputa0().set(a0Tested);
		
		synthesisEngine.sleepUntil(synthesisEngine.getCurrentTime() + 1);
		double out1 = vca.getOutput().getValue();
		System.out.println("2 " + out/4 + " " + out1);
		assertEquals("vca out value - with am + 1V ", out/4, out1, 0.0001);
	}
}
