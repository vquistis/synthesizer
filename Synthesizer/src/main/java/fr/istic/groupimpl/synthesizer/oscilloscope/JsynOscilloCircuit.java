package fr.istic.groupimpl.synthesizer.oscilloscope;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.util.SignalUtil;

public class JsynOscilloCircuit extends UnitGenerator {

	private UnitInputPort input; 		//Volt
	private UnitOutputPort output; 		//Volt

	
	
	/**
	 * Constructor
	 */
    public JsynOscilloCircuit( int nbBuf, int sizeBuf ) {
        addPort(input = new UnitInputPort("oscillo_in"));
        addPort(output= new UnitOutputPort("oscillo_out"));
        
        buf = new double[nbBuf][sizeBuf];
        blocBuf = new AtomicBoolean[nbBuf];
        for ( int i = 0 ; i < nbBuf ; i++ )
        {
        	blocBuf[i] = new AtomicBoolean(false);
        }
        indice = 0;
        indBuf = 0;
        blocBuf[0].set(true);
    }
    
	public UnitInputPort getInput() {
		return input;
	}

	public UnitOutputPort getOutput() {
		return output;
	}

	private final double [][] buf;
	
	private final AtomicBoolean [] blocBuf;
	
	private int indBuf;
	private int indice;
	
	private int newIndBuf()
	{
		int ind = indBuf;
		for ( int i = 0 ; i < blocBuf.length ; i++ )
		{
			ind++;
			ind %= blocBuf.length;
			if ( !blocBuf[ind].getAndSet(true) )
			{
				return ind;
			}
		}
		return -1;
	}

	public double [] getBuffer()
	{
		int ind = newIndBuf();
		
		double [] ret = Arrays.copyOf(buf[ind], buf[ind].length);
		
		blocBuf[ind].set(false);
		
		return ret;
	}	
		
    void storeBuf( double v )
    {
    	buf[indBuf][indice++] = v;
    	if ( indice >= buf[indBuf].length )
    	{
    		int oldIndBuf = indBuf;
     		indBuf = newIndBuf();
       		blocBuf[oldIndBuf].set(false);
    		indice = 0;
    	}
    }
    

    @Override
    public void generate(int start, int limit) {
        double[] inputs = input.getValues();
        double[] outputs = output.getValues();

        for (int i = start; i < limit; i++) {    	
        	outputs[i] = inputs[i];
        	storeBuf(outputs[i]);
         }
    }
    

}
