package fr.istic.groupimpl.synthesizer.util;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * The Class TestOscilloscope.
 */
public class TestOscilloscope extends TestCase {
	
	/** The scope. */
	private Oscilloscope scope;
	
	/** The buffer scope. */
	private double [] bufferScope=new double[2048];
	
	/** The nb get buffer. */
	private int nbGetBuffer=0;
	
	/**
	 * Gets the buffer.
	 *
	 * @return the buffer
	 */
	private double [] getBuffer()
	{
		nbGetBuffer++;
		return bufferScope;
		
	}
    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
		OscilloscopeFactory scopeFact = OscilloscopeFactory.getFactoryInstance();
		scopeFact.setCmdGetBuffer( ()->getBuffer() );
		
		scope = scopeFact.getOscilloscope();	
	}

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    
    /**
     * Test set period.
     */
    @Test
    public void testSetPeriod()
    {
     	scope.setRefreshPeriod(0.5);
     	
     	assertEquals(0.5,scope.getRefreshPeriod(),0.000001);
     	
    }
    
    /**
     * Test set null period.
     */
    @Test
    public void testSetNullPeriod()
    {
     	scope.setRefreshPeriod(0.0);
     	
     	assertEquals(0.1,scope.getRefreshPeriod(),0.000001);
     	
    }
    
    /**
     * Test set big period.
     */
    @Test
    public void testSetBigPeriod()
    {
     	scope.setRefreshPeriod(1123.);
     	
     	assertEquals(2.,scope.getRefreshPeriod(),0.000001);
     	
    }

}
