package fr.istic.groupimpl.synthesizer.keyboard.jsyn;


import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Before;

import com.jsyn.engine.SynthesisEngine;

import fr.istic.groupimpl.synthesizer.keyboard.jsyn.JSynKeyboard;
import fr.istic.groupimpl.synthesizer.rep.jsyn.JsynRepCircuit;
import fr.istic.groupimpl.synthesizer.util.SignalUtil;

/**
 * The Class REPTest.
 */
public class JSynKeyboardTest  {
	
	/** The synthesis engine. */
	SynthesisEngine synthesisEngine;
	
	JSynKeyboard ksyn;
	

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	public void setUp() throws Exception {
		synthesisEngine = new SynthesisEngine();
		ksyn = new JSynKeyboard();
		synthesisEngine.add(ksyn);

		synthesisEngine.start();
		ksyn.start();

	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@After
	public void tearDown() throws Exception {
		synthesisEngine.stop();
		
	}

	/**
	 * test first start .
	 *
	 */
	@Test
	public void testFirst() {
		
		
		assertEquals(0.,ksyn.getOutputCV().getValue(),0.0000001);
		assertEquals(0.,ksyn.getOutputGate().getValue(),0.0000001);
		

	}
	
	/**
	 * test after press true
	 * @throws InterruptedException 
	 */
	@Test
	public void testPressTrue() throws InterruptedException {
		
		ksyn.setPress(true);
		Thread.sleep(100L);
		assertEquals(0.,ksyn.getOutputCV().getValue(),0.0000001);
		assertEquals(1.,ksyn.getOutputGate().getValue(),0.0000001);
		
	}
	/**
	 * test after press true and setVoltCV
	 * @throws InterruptedException 
	 */
	@Test
	public void testPressTrueSetVolt() throws InterruptedException {
		
		double volt = (1.+1./12.)/SignalUtil.COEF_VOLT;
		ksyn.setPress(true);
		ksyn.setVolt(volt);
		Thread.sleep(100L);
		assertEquals(volt,ksyn.getOutputCV().getValue(),0.0000001);
		assertEquals(1.,ksyn.getOutputGate().getValue(),0.0000001);
		
	}
	/**
	 * test after press true and setVoltCV
	 * @throws InterruptedException 
	 */
	@Test
	public void testPressFalseSetVolt() throws InterruptedException {
		
		double volt = (1.-1./12.)/SignalUtil.COEF_VOLT;
		ksyn.setPress(true);
		ksyn.setPress(false);
		ksyn.setVolt(volt);
		Thread.sleep(100L);
		assertEquals(volt,ksyn.getOutputCV().getValue(),0.0000001);
		assertEquals(-1.,ksyn.getOutputGate().getValue(),0.0000001);
		
	}

	

	
}
