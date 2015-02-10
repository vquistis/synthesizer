package fr.istic.groupimpl.synthesizer.oscilloscope;

import java.util.Collection;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.oscilloscope.jsyn.JsynOscilloCircuit;

public class ModelOscillo extends ModelComponent {

	private JsynOscilloCircuit circuit;
	
	public ModelOscillo( int sizeBuffer ) {
		super();

		circuit = new JsynOscilloCircuit(3,sizeBuffer);
	}

	/**
	 * Méthode pour transmettre les dernière données valides
	 * @return 
	 * 		buffer de données
	 */
	public double [] getBuffer()
	{
		return circuit.getBuffer();
	}

	@Override
	public UnitGenerator getUnitGenerator() {
		return circuit;
	}

	/**
	 * Get the jsyn input port.
	 * @return UnitInputPort
	 */
	public UnitInputPort getInputPort() {
		return circuit.getInput();
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
