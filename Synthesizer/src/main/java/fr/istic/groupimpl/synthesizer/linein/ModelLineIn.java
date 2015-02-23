package fr.istic.groupimpl.synthesizer.linein;

import java.util.Collection;

import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.LineIn;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;

/**
 * Model of line In module.
 *
 * @author Team GroupImpl
 */
public class ModelLineIn extends ModelComponent {

	/** The line in. */
	private LineIn lineIn;
	
	/**
	 * Constructor.
	 */
	public ModelLineIn() {
		super();
		lineIn = new LineIn();
	}
	
	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.IModelComponent#getUnitGenerator()
	 */
	@Override
	public UnitGenerator getUnitGenerator() {
		return lineIn;
	}

	/**
	 * Get the jsyn output port.
	 * @return UnitInputPort
	 */
	public UnitOutputPort getOutputPort() {
		return lineIn.output;
	}

	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.IModelComponent#getAllPorts()
	 */
	@Override
	public Collection<UnitPort> getAllPorts() {
		return lineIn.getPorts();
	}
}
