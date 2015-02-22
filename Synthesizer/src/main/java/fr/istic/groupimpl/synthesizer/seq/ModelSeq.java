package fr.istic.groupimpl.synthesizer.seq;

import java.util.Collection;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.seq.jsyn.JsynSequencerCircuit;

/**
 * 
 * Model of Seq module
 * 
 * @author Team GroupImpl
 *
 */
public class ModelSeq extends ModelComponent {

	private final double sigMin = 0.00001;
	private final double sigMax = 0.1;
	
	private JsynSequencerCircuit circuit;
	
	/**
	 * Constructor
	 * @param nbPas
	 */
	public ModelSeq( int nbPas ) {
		super();

		circuit = new JsynSequencerCircuit( nbPas,sigMin,sigMax);
	}

	
	
	/**
	 * Initialize a value for a step
	 * @param indice
	 * 	from 0 to 7 ( inclusive )
	 * @param newVal
	 *  new Value for -1. to 1. 
	 */
	public void setValue(int indice, Double newVal)
	{
		circuit.setValue(indice,newVal);
	}
	
	/**
	 * Initialize the step to 0 
	 */
	public void initBegin()
	{
		circuit.resetPas();
	}
	
	@Override
	public UnitGenerator getUnitGenerator() {
		return circuit;
	}

	/**
	 * Get the jsyn input port.
	 * @return UnitInputPort
	 */
	public UnitInputPort getGatePort() {
		return circuit.getGate();
	}

	/**
	 * Get the jsyn output port.
	 * @return UnitOutputPort
	 */
	public UnitOutputPort getOutputPort() {
		return circuit.getOutput();
	}

	@Override
	public Collection<UnitPort> getAllPorts() {
		return circuit.getPorts();
	}

}
