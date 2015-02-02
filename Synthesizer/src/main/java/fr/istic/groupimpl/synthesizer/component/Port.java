package fr.istic.groupimpl.synthesizer.component;

import fr.istic.groupimpl.synthesizer.command.ICommand;

public class Port {
	
	private ICommand cmd;
	
	public boolean isInput() {
		return false;		
	}
	
	public void setOnClickCommand(ICommand cmd) {
		this.cmd = cmd;
	}
	
	public void handleClick() {
		if (cmd != null) {
			cmd.execute();
		}
	}

}
