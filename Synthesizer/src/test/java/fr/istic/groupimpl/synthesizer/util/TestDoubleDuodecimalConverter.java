package fr.istic.groupimpl.synthesizer.util;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * The Class TestDoubleDuodecimalConverter.
 */
public class TestDoubleDuodecimalConverter extends TestCase {
	
	/** The converter. */
	DoubleDuodecimalStringConverter converter=null;
	
    /**
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
       converter=new DoubleDuodecimalStringConverter();
        
	}

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

	
	/**
	 * Test to string juste.
	 */
	@Test
	public void testToStringJuste()
	{
		assertEquals("0.1", converter.toString(1./12.) );
		assertEquals("0.9", converter.toString(9./12.) );
		assertEquals("0.a", converter.toString(10./12.) );
		assertEquals("0.b", converter.toString(11./12.) );
		assertEquals("1",converter.toString(1.));
	}
	
	/**
	 * Test to string juste neg.
	 */
	@Test
	public void testToStringJusteNeg()
	{
		assertEquals("-0.1", converter.toString(-1./12.) );
		assertEquals("-0.9", converter.toString(-9./12.) );
		assertEquals("-0.a", converter.toString(-10./12.) );
		assertEquals("-0.b", converter.toString(-11./12.) );
		assertEquals("-1",converter.toString(-1.));
	}
	
	/**
	 * Test to string pas juste.
	 */
	@Test
	public void testToStringPasJuste()
	{
		assertEquals("0.27b", converter.toString(0.22222222) );
	}
	
	/**
	 * Test to string pas juste neg.
	 */
	@Test
	public void testToStringPasJusteNeg()
	{
		assertEquals("-0.27b", converter.toString(-0.22222222) );
	}
	
	/**
	 * Test all value.
	 */
	@Test 
	public void testAllValue()
	{
		int borne=12*12*12*2;
		double q=12.*12.*12.;
		for ( int i = 0 ; i < borne ; i++ )
		{
			double v = -1. + ((double)i)/q;
			if ( v != 0 )
				v += Math.signum(v)*0.000001;
			String str=converter.toString(v);
			assertEquals("Convert str="+str,v,(double)converter.fromString(str),0.00001);
		}
	}
	
	

}
