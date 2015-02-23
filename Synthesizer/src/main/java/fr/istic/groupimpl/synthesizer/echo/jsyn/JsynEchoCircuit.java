package fr.istic.groupimpl.synthesizer.echo.jsyn;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.util.SignalUtil;

/**
 * 
 * Jsyn Sequencer Circuit
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
	
	final double [] buffer;
	
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
     * set the value of a step
     * @param indice
     * 	indice of the step from 0 to 7
     * @param volt
     *  value of the step from -1 to 1
     */
    public void setPeriodValue( double period )
    {
    	int locPeriodNb = (int)(period*(double)frameRate);
    	if ( locPeriodNb > buffer.length )
    	{
    		locPeriodNb = buffer.length;
    	}
    	periodNb.set(locPeriodNb);
    	
    }
    
    public void setAttenuationValue( double v )
    {
    	coefAttenuation.set(Double.doubleToLongBits(Math.pow(2.,v/6.)));
    }
    
    
     
	/**
	 * get the gate port
	 * @return
	 *  the gate port
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
