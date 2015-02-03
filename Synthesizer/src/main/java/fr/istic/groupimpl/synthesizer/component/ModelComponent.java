package fr.istic.groupimpl.synthesizer.component;

import java.util.Properties;

import fr.istic.groupimpl.synthesizer.command.ICommand;
import fr.istic.groupimpl.synthesizer.component.IModelComponent;

public abstract class ModelComponent implements IModelComponent {
	
	protected Properties propsVals;
	protected Properties commands;
		
	public ModelComponent() {
		propsVals = new Properties();
		commands = new Properties();
	}
	
	public Object getValProperty(String prop) {
		return propsVals.get(prop);
	}
	
	public void setValProperty(String prop, Object val) {
		propsVals.put(prop, val);
		
		// Notifie les observers du changement de la propriété "prop" du modèle
		if (commands.containsKey(prop)) {
			ICommand cmd = (ICommand) commands.get(prop);
			cmd.execute();
		}
	}
	
	public void setCommandProperty(String prop, ICommand cmd) {
		commands.put(prop, cmd);
	}
		
}
