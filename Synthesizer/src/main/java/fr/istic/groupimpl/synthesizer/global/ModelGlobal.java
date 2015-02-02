package fr.istic.groupimpl.synthesizer.global;

import java.util.ArrayList;
import java.util.List;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;

import fr.istic.groupimpl.synthesizer.command.ICommand;
import fr.istic.groupimpl.synthesizer.component.IModelComponent;


public class ModelGlobal {
	
	private ICommand handleConnectCmd;

	private List<IModelComponent> modules = new ArrayList<IModelComponent>();
	
	public void addModule(IModelComponent module) {
		modules.add(module);
	}
	
	public void removeModule(IModelComponent module) {
		modules.remove(module);
	}
	
	public void connectModules(IModelComponent moduleIn, String inputPort, IModelComponent moduleOut, String outputPort) {
		UnitOutputPort out = (UnitOutputPort) moduleIn.getUnitGenerator().getPortByName(outputPort);
		UnitInputPort in = (UnitInputPort) moduleIn.getUnitGenerator().getPortByName(inputPort);
		out.connect(in);
	}
	
	public void disconnectModules(IModelComponent moduleIn, String inputPort, IModelComponent moduleOut, String outputPort) {
		UnitOutputPort out = (UnitOutputPort) moduleIn.getUnitGenerator().getPortByName(outputPort);
		UnitInputPort in = (UnitInputPort) moduleIn.getUnitGenerator().getPortByName(inputPort);
		out.disconnect(in);
	}
}
