package fr.istic.groupimpl.synthesizer.oscilloscope;

import java.util.Collection;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.oscilloscope.jsyn.JsynOscilloCircuit;

/**
 * Model of oscilloscope module.
 *
 * @author Team GroupImpl
 */
public class ModelOscillo extends ModelComponent {

	/** The circuit. */
	private JsynOscilloCircuit circuit;
	
	/**
	 * Constructor.
	 *
	 * @param sizeBuffer the size buffer
	 */
	public ModelOscillo( int sizeBuffer ) {
		super();

		circuit = new JsynOscilloCircuit(3,sizeBuffer);
	}

	/**
	 * Transmits the last valid data .
	 *
	 * @return buffer of data
	 */
	public double [] getBuffer()
	{
		return circuit.getBuffer();
	}

	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.IModelComponent#getUnitGenerator()
	 */
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

	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.IModelComponent#getAllPorts()
	 */
	@Override
	public Collection<UnitPort> getAllPorts() {
		return circuit.getPorts();
	}

}
