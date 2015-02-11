package fr.istic.groupimpl.synthesizer.seq;

import java.util.Collection;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.seq.jsyn.JsynSequencerCircuit;

public class ModelSeq extends ModelComponent {

	private JsynSequencerCircuit circuit;
	
	public ModelSeq( int nbPas ) {
		super();

		circuit = new JsynSequencerCircuit( nbPas);
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