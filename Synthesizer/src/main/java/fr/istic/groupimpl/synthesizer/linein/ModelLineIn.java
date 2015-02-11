package fr.istic.groupimpl.synthesizer.linein;

import java.util.Collection;

import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.LineIn;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.io.architecture.Module;

/**
 * 
 * Model of line In module
 * 
 * @author Team GroupImpl
 *
 */
public class ModelLineIn extends ModelComponent {

	private LineIn lineIn;
	
	/**
	 * Constructor
	 */
	public ModelLineIn() {
		super();
		lineIn = new LineIn();
	}
	
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

	@Override
	public Collection<UnitPort> getAllPorts() {
		return lineIn.getPorts();
	}
}
