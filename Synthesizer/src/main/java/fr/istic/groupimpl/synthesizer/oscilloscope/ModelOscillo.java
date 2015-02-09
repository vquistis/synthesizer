package fr.istic.groupimpl.synthesizer.oscilloscope;

import java.util.Collection;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;

public class ModelOscillo extends ModelComponent {

	private JsynOscilloCircuit circuit;
	
	public ModelOscillo() {
		super();

		circuit = new JsynOscilloCircuit(3,1024);
	}

	public double [] getBuffer()
	{
		return circuit.getBuffer();
	}

	@Override
	public UnitGenerator getUnitGenerator() {
		return circuit;
	}
	
	@Override
	public UnitInputPort getInputPort(String portName) {
		return circuit.getInput();
	}

	@Override
	public UnitOutputPort getOutputPort(String portName) {
		//This module doesn't have output port
		return circuit.getOutput();
	}

	@Override
	public Collection<UnitPort> getAllPorts() {
		return circuit.getPorts();
	}
}
