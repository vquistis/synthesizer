package fr.istic.groupimpl.synthesizer.seq.jsyn;

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
public class JsynSequencerCircuit extends UnitGenerator {

	private enum State 
	{
		ATTENTE_MIN,
		ATTENTE_MAX
	}
	
	private UnitInputPort gate; 		//Volt
	private UnitOutputPort output; 		//Volt

	private final double [] value;
	
	private int pasCourant=0;
	
	private final double sigMinFrontMontant;
	private final double sigMaxFrontMontant;
	private final int nbPas;
	
	
	private State etat=State.ATTENTE_MIN;
	
	
	
	/**
	 * Constructor
	 */
    public JsynSequencerCircuit( int nbPas, double sigMinFrontMontantVolt, double sigMaxFrontMontantVolt ) {
        addPort(gate = new UnitInputPort("sequencer_gate"));
        addPort(output= new UnitOutputPort("sequencer_out"));
        
        
        sigMinFrontMontant = sigMinFrontMontantVolt/SignalUtil.COEF_VOLT;
        sigMaxFrontMontant = sigMaxFrontMontantVolt/SignalUtil.COEF_VOLT;
        this.nbPas = nbPas;
             
        value = new double[nbPas];
     }
    
    
    /**
     * set the value of a step
     * @param indice
     * 	indice of the step from 0 to 7
     * @param volt
     *  value of the step from -1 to 1
     */
    public void setValue( int indice, double volt )
    {
    	value[indice] = volt/SignalUtil.COEF_VOLT;
    }
    
    /**
     * get the value of a step
     * @param indice
     * 	indice of the step from 0 to 7
     * @return
     *  the value of the step
     */
    public double getValue( int indice )
    {
    	return value[indice]*SignalUtil.COEF_VOLT;
    }
   
    /**
     * initalyse the step to 0
     */
    public void resetPas()
    {
    	pasCourant = 0;
    }
    
	/**
	 * get the gate port
	 * @return
	 *  the gate port
	 */
	public UnitInputPort getGate() {
		return gate;
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
        double[] inputs = gate.getValues();
        double[] outputs = output.getValues();

        for (int i = start; i < limit; i++) { 
        	if ( etat == State.ATTENTE_MIN )
        	{
        		if ( inputs[i] <= sigMinFrontMontant )
        		{
        			etat = State.ATTENTE_MAX;
        		}
        	}
        	else
        	{
        		// etat == State.ATTENTE_MAX
          		if ( inputs[i] >= sigMaxFrontMontant )
        		{
        			etat = State.ATTENTE_MIN;
        			pasCourant++;
        			pasCourant %= nbPas;
        		}
        	}
        	outputs[i] = value[pasCourant];
         }
    }
    

}
