package fr.istic.groupimpl.synthesizer.rep;

import java.util.Collection;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.io.architecture.Module;
import fr.istic.groupimpl.synthesizer.rep.jsyn.JsynRepCircuit;

public class ModelRep extends ModelComponent{
	
	private JsynRepCircuit rep;
	
	public ModelRep(){
		super();
		rep = new JsynRepCircuit();
	}

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

	@Override
	public Collection<UnitPort> getAllPorts() {
		return rep.getPorts();
	}

}
