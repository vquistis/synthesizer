package fr.istic.groupimpl.synthesizer.component;

import java.util.Collection;

import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.command.ICommand;

/**
 * The interface IModelComponent
 * 
 * @author Team groupImpl
 *
 */
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
	 * Get all port of the component.
	 * @return Collection of port
	 */
	public Collection<UnitPort> getAllPorts();
}
