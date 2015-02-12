package fr.istic.groupimpl.synthesizer.keyboard;

import java.util.Collection;

import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;

/**
 * 
 * Model of keyboard module
 * 
 * @author Team GroupImpl
 *
 */
public class ModelKeyboard extends ModelComponent {

	private JSynKeyboard keyboard;
	
	/**
	 * Constructor
	 */
	public ModelKeyboard() {
		super();
		keyboard = new JSynKeyboard();
	}
	
	@Override
	public UnitGenerator getUnitGenerator() {
		return keyboard;
	}

	/**
	 * Get the jsyn output port.
	 * @return UnitInputPort
	 */
	public UnitOutputPort getOutputPort() {
		return keyboard.getOutput();
	}

	@Override
	public Collection<UnitPort> getAllPorts() {
		return keyboard.getPorts();
	}

}
