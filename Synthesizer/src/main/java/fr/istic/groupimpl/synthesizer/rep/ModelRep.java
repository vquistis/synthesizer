package fr.istic.groupimpl.synthesizer.rep;

import java.util.Collection;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.rep.jsyn.JsynRepCircuit;

/**
 * 
 * Model of rep module
 *  
 * @author Team GroupImpl
 */
public class ModelRep extends ModelComponent{
	
	/** The rep. */
	private JsynRepCircuit rep;
	
	/**
	 * Constructor.
	 */
	public ModelRep(){
		super();
		rep = new JsynRepCircuit();
	}

	/**
	 * @see fr.istic.groupimpl.synthesizer.component.IModelComponent#getUnitGenerator()
	 */
	@Override
	public UnitGenerator getUnitGenerator() {
		return rep;
	}

	/**
	 * Get the jsyn input port.
	 * @return UnitInputPort
	 */
	public UnitInputPort getInputPort() {
		return rep.getInput();
	}

	/**
	 * Get the jsyn output port.
	 * @param portName The name of one of three output
	 * @return UnitOutputPort
	 */
	public UnitOutputPort getOutputPort(String portName) {
		if (portName.equals("rep_out1"))
			return rep.getOutput(1);
		if (portName.equals("rep_out2"))
			return rep.getOutput(2);
		if (portName.equals("rep_out3"))
			return rep.getOutput(3);
		
		return null;
	}

	/**
	 * @see fr.istic.groupimpl.synthesizer.component.IModelComponent#getAllPorts()
	 */
	@Override
	public Collection<UnitPort> getAllPorts() {
		return rep.getPorts();
	}

}
