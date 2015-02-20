package fr.istic.groupimpl.synthesizer.component;

import java.util.Properties;

import fr.istic.groupimpl.synthesizer.command.ICommand;

/**
 * The abstract class ModelComponent
 * 
 * @author Team groupImpl
 *
 */
public abstract class ModelComponent implements IModelComponent {
	
	protected Properties propsVals;
	protected Properties commands;
	
	/**
	 * Constructor
	 */
	public ModelComponent() {
		propsVals = new Properties();
		commands = new Properties();
	}
	
	/**
	 * get property in propsVals
	 */
	public Object getValProperty(String prop) {
		return propsVals.get(prop);
	}
	
	/**
	 * add a property in propsVals
	 */
	public void setValProperty(String prop, Object val) {
		propsVals.put(prop, val);
		
		// Notifie les observers du changement de la propriété "prop" du modèle
		if (commands.containsKey(prop)) {
			ICommand cmd = (ICommand) commands.get(prop);
			cmd.execute();
		}
	}
	
	/**
	 * add a property in commands
	 */
	public void setCommandProperty(String prop, ICommand cmd) {
		commands.put(prop, cmd);
	}
		
}
