package fr.istic.groupimpl.synthesizer.component;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.command.ICommand;

public interface IModelComponent {
	/**
	 * Get value of the property passed as parameter.
	 * @param prop - name of property
	 * @return value of property
	 */
	public Object getValProperty(String prop);
	
	/**
	 * Set a value for the property passed as parameter.
	 * When the value change the command is executed 
	 * @param prop - name of property
	 * @param val - new value for the property
	 */
	public void setValProperty(String prop, Object val);
	
	/**
	 * Set the command to be executed when the value of property change
	 * @param prop - name of property
	 * @param cmd - 
	 */
	public void setCommandProperty(String prop, ICommand cmd);

	
	/**
	 * Get the jsyn object for the component.
	 * @return UnitGenerator
	 */
	public UnitGenerator getUnitGenerator();
	
	/**
	 * Get the jsyn input port.
	 * @param portName - the name associated to the port
	 * @return UnitInputPort
	 */
	public UnitInputPort getInputPort(String portName);
	
	/**
	 * Get the jsyn output port.
	 * @param portName - the name associated to the port
	 * @return UnitOutputPort
	 */
	public UnitOutputPort getOutputPort(String portName);
}
