package fr.istic.groupimpl.synthesizer.out;

import java.util.Collection;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.out.jsyn.JsynOutCircuit;

/**
 * 
 * Model of out module
 *  
 * @author Team GroupImpl
 *
 */
public class ModelOut extends ModelComponent {

	/** The out. */
	private JsynOutCircuit out;
	
	/**
	 * Constructor.
	 */
	public ModelOut() {
		super();

		out = new JsynOutCircuit();
		setAttenuation(0); // Default value
	}

	/**
	 * Set an attenuation to the output signal.
	 *
	 * @param dbValue the new attenuation
	 */
	public void setAttenuation(double dbValue) {
		out.setAttenuation(dbValue);
	}

	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.IModelComponent#getUnitGenerator()
	 */
	@Override
	public UnitGenerator getUnitGenerator() {
		return out;
	}

	/**
	 * Get the jsyn input port.
	 * @return UnitInputPort
	 */
	public UnitInputPort getInputPort() {
		return out.getInput();
	}

	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.IModelComponent#getAllPorts()
	 */
	@Override
	public Collection<UnitPort> getAllPorts() {
		return out.getPorts();
	}

}
