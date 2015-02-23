package fr.istic.groupimpl.synthesizer.echo.jsyn;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

/**
 * 
 * Jsyn Echo Circuit
 * 
 * @author Team GroupImpl
 *
 */
public class JsynEchoCircuit extends UnitGenerator {

	private final int frameRate=44100;
	
	private UnitInputPort in; 		//Volt
	private UnitOutputPort output; 		//Volt

	private AtomicInteger periodNb =new AtomicInteger();
	private AtomicLong coefAttenuation=new AtomicLong(); // en fait c'est un double
	
	final private double [] buffer;
	
	int currentIndex=0;
	
	/**
	 * Constructor
	 */
    public JsynEchoCircuit( double maxPeriod ) {
        addPort(in = new UnitInputPort("echo_in"));
        addPort(output= new UnitOutputPort("echo_out"));
            
        int sizeBuffer = (int)maxPeriod*frameRate;
        
        buffer = new double [sizeBuffer];
        
        setPeriodValue(1.);
        setAttenuationValue(-6.);
      }
    
    
    /**
     * set the period Value in seconds
     * @param period
     *  value of the period in seconds
     */
    public void setPeriodValue( double period )
    {
    	int locPeriodNb = (int)(period*(double)frameRate);
    	if ( locPeriodNb >= buffer.length )
    	{
    		locPeriodNb = buffer.length-1;
    	}
    	periodNb.set(locPeriodNb);
    	
    }
    
    /**
     * set the attenuation value ( decibel, allways negative )
     * @param v
     */
    public void setAttenuationValue( double v )
    {
    	coefAttenuation.set(Double.doubleToLongBits(Math.pow(2.,v/6.)));
    }
    
    
     
	/**
	 * get the input port
	 * @return
	 *  the input port
	 */
	public UnitInputPort getInput() {
		return in;
	}

	/**
	 * get the output port
	 * @return
	 * the output port
	 */
	public UnitOutputPort getOutput() {
		return output;
	}


    @Override
    public void generate(int start, int limit) {
        double[] inputs = in.getValues();
        double[] outputs = output.getValues();
        double coef = Double.longBitsToDouble(coefAttenuation.get());
        int moinsPerNb=buffer.length-periodNb.get();
        for (int i = start; i < limit; i++) {        	
        	int attIndex = (currentIndex+moinsPerNb)%buffer.length;        	
        	buffer[currentIndex] = inputs[i] + buffer[attIndex]*coef;
         	outputs[i] = buffer[currentIndex++];
          	currentIndex %= buffer.length;
         }
    }
    

}
