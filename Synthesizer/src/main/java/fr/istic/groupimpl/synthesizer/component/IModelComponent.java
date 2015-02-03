package fr.istic.groupimpl.synthesizer.component;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.command.ICommand;

public interface IModelComponent {
	
	public Object getValProperty(String prop);
	
	public void setValProperty(String prop, Object val);
	
	public void setCommandProperty(String prop, ICommand cmd);

	public UnitGenerator getUnitGenerator();
	public UnitInputPort getInputPort(String portName);
	public UnitOutputPort getOutputPort(String portName);
}
