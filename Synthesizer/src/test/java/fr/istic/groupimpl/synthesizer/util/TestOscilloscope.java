package fr.istic.groupimpl.synthesizer.util;

import junit.framework.TestCase;

import org.junit.Test;

public class TestOscilloscope extends TestCase {
	
	private Oscilloscope scope;
	private double [] bufferScope=new double[2048];
	private int nbGetBuffer=0;
	private double [] getBuffer()
	{
		nbGetBuffer++;
		return bufferScope;
		
	}
    @Override
    protected void setUp() throws Exception {
        super.setUp();
		OscilloscopeFactory scopeFact = OscilloscopeFactory.getFactoryInstance();
		scopeFact.setCmdGetBuffer( ()->getBuffer() );
		
		scope = scopeFact.getOscilloscope();	
	}

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    
    @Test
    public void testSetPeriod()
    {
     	scope.setRefreshPeriod(0.5);
     	
     	assertEquals(0.5,scope.getRefreshPeriod(),0.000001);
     	
    }
    @Test
    public void testSetNullPeriod()
    {
     	scope.setRefreshPeriod(0.0);
     	
     	assertEquals(0.1,scope.getRefreshPeriod(),0.000001);
     	
    }
    @Test
    public void testSetBigPeriod()
    {
     	scope.setRefreshPeriod(1123.);
     	
     	assertEquals(2.,scope.getRefreshPeriod(),0.000001);
     	
    }

}
