package fr.istic.groupimpl.synthesizer.global;

import java.util.ArrayList;
import java.util.List;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;

import fr.istic.groupimpl.synthesizer.command.ICommand;
import fr.istic.groupimpl.synthesizer.component.IModelComponent;


public class ModelGlobal {

	private List<IModelComponent> modules = new ArrayList<IModelComponent>();
	
	public void addModule(IModelComponent model) {
		modules.add(model);
	}
	
	public void removeModule(IModelComponent model) {
		modules.remove(model);
	}
	
	public void connectModules(IModelComponent moduleOut, String outputPort, IModelComponent moduleIn, String inputPort) {
		UnitOutputPort out = (UnitOutputPort) moduleIn.getUnitGenerator().getPortByName(outputPort);
		UnitInputPort in = (UnitInputPort) moduleIn.getUnitGenerator().getPortByName(inputPort);
		out.connect(in);
	}
	
	public void disconnectModules(IModelComponent moduleOut, String outputPort, IModelComponent moduleIn, String inputPort) {
		UnitOutputPort out = (UnitOutputPort) moduleIn.getUnitGenerator().getPortByName(outputPort);
		UnitInputPort in = (UnitInputPort) moduleIn.getUnitGenerator().getPortByName(inputPort);
		out.disconnect(in);
	}
}
