package fr.istic.groupimpl.synthesizer.seq.jsyn;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.util.SignalUtil;

public class JsynSequencerCircuit extends UnitGenerator {

	private UnitInputPort gate; 		//Volt
	private UnitOutputPort output; 		//Volt

	private double value;
	
	/**
	 * Constructor
	 */
    public JsynSequencerCircuit( int nbPas ) {
        addPort(gate = new UnitInputPort("sequencer_gate"));
        addPort(output= new UnitOutputPort("sequencer_out"));
        
     }
    
	public UnitInputPort getGate() {
		return gate;
	}

	public UnitOutputPort getOutput() {
		return output;
	}


    @Override
    public void generate(int start, int limit) {
        double[] inputs = gate.getValues();
        double[] outputs = output.getValues();

        for (int i = start; i < limit; i++) {    	
        	outputs[i] = SignalUtil.verifyAmplitude(inputs[i]);
         }
    }
    

}
