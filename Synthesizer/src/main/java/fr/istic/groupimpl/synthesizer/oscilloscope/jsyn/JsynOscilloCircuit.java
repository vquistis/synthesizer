package fr.istic.groupimpl.synthesizer.oscilloscope.jsyn;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.util.SignalUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class of Jsyn oscilloscope module.
 *
 * @author Team GroupImpl
 */
public class JsynOscilloCircuit extends UnitGenerator {

	/** The input volt. */
	private UnitInputPort input; 		
	
	/** The output Volt. */
	private UnitOutputPort output; 	
	
	/**
	 * Constructor.
	 *
	 * @param nbBuf the nb buf
	 * @param sizeBuf the size buf
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
    
    /**
	 * Get the jsyn input port.
	 * @return UnitInputPort
	 */
	public UnitInputPort getInput() {
		return input;
	}

	/**
	 * Get the jsyn output port.
	 * @return UnitOutputPort
	 */
	public UnitOutputPort getOutput() {
		return output;
	}

	/** The buf. */
	private final double [][] buf;
	
	/** The bloc buf. */
	private final AtomicBoolean [] blocBuf;
	
	/** The ind buf. */
	private int indBuf;
	
	/** The indice. */
	private int indice;
	
	/**
	 * Makes a new index which was free while blocking.
	 *
	 * @return indice
	 */
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

	/**
	 * Makes a given buffer read recently.
	 *
	 * @return the buffer
	 */
	public double [] getBuffer()
	{
		int ind = newIndBuf();
		
		double [] ret = Arrays.copyOf(buf[ind], buf[ind].length);
		
		blocBuf[ind].set(false);
		
		for ( int i = 0 ; i < ret.length ; i++ )
		{
			ret[i] *= SignalUtil.COEF_VOLT;
		}
		return ret;
	}	
		
    /**
     * Stores a given element.
     *
     * @param v 	buffer of data
     */
    private void storeBuf( double v )
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
    

    /**
     * @see com.jsyn.unitgen.UnitGenerator#generate(int, int)
     */
    @Override
    public void generate(int start, int limit) {
        double[] inputs = input.getValues();
        double[] outputs = output.getValues();

        for (int i = start; i < limit; i++) {    	
        	//       	outputs[i] = SignalUtil.verifyAmplitude(inputs[i]);
        	// pas de verification de l'amplitude pour l'oscilloscope
        	outputs[i] = inputs[i];
        	storeBuf(outputs[i]);
         }
    }
    

}
