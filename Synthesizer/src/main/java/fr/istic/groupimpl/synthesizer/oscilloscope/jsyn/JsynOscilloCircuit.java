package fr.istic.groupimpl.synthesizer.oscilloscope.jsyn;

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
	
	/**
	 * rend un nouvel index qui etait libre tout en le bloquant
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
	 * fonction qui rend un buffer de donnee lu recemment
	 * @return
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
     * Fonction qui stocke un élément de donné
     * @param v
     * 	Element de donnée
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
