package fr.istic.groupimpl.synthesizer.util;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jsyn.engine.SynthesisEngine;

import junit.framework.TestCase;

public class TestOscilloscope extends TestCase {
	
	private Oscilloscope scope;
	private double [] bufferScope;
    @Override
    protected void setUp() throws Exception {
        super.setUp();
		OscilloscopeFactory scopeFact = OscilloscopeFactory.getFactoryInstance();
		scopeFact.setCmdGetBuffer(()->bufferScope );
		
		scope = scopeFact.getOscilloscope();	
	}

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
	
	@Test
	public void testCompress()
	{
		double [] buf = new double[128];
		
		double v = -50.;
		double inc= 1.;
		for ( int i = 0 ; i < buf.length ; i++, v += inc )
		{
			buf[i] = v;
		}
		
		bufferScope = buf;
		
		List<Oscilloscope.EltComp> result= scope.compress(buf);
		
		assertEquals(2,result.size());
		
		assertEquals(-50,result.get(0).val);
		assertEquals(1,result.get(0).indice);	
	}

}
