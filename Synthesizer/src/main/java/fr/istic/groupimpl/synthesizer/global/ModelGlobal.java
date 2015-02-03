package fr.istic.groupimpl.synthesizer.global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.util.Pair;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.IModelComponent;

public class ModelGlobal {

	private ControllerGlobal controller;

	private List<IModelComponent> modules = new ArrayList<IModelComponent>();

	private Synthesizer synth;

	/*
	 * f(output) -> input
	 */
	private Map<IModelComponent,Map<String,Pair<IModelComponent,String>>> outputConnexions;
	/*
	 * f(input) -> output
	 */
	private Map<IModelComponent,Map<String,Pair<IModelComponent,String>>> inputConnexions;

	public ModelGlobal(ControllerGlobal controller) {
		this.controller = controller;
		this.synth = JSyn.createSynthesizer();
		this.synth.start();
	}

	public void addModule(IModelComponent module) {
		modules.add(module);
		UnitGenerator unitGen = module.getUnitGenerator();
		synth.add(unitGen);
		unitGen.start();
	}

	public void removeModule(IModelComponent module) {
		/* 
		 * TODO disconnect every modules from this one before removing it:
		 * forall input of the module -> search modules connected to the input in the inputConnexions map and disconnect them
		 * forall output of the module -> search modules connected to the output in the outputConnexions map and disconnect them
		 */
		disconnectAllInputPort(module);
		disconnectAllOutputPort(module);
		UnitGenerator unitGen = module.getUnitGenerator();
		unitGen.stop();
		synth.remove(unitGen);
		modules.remove(module);
	}

	public void connectModules(IModelComponent moduleOut, String outputPort, IModelComponent moduleIn, String inputPort) {
		UnitOutputPort out = (UnitOutputPort) moduleOut.getUnitGenerator().getPortByName(outputPort);
		UnitInputPort in = (UnitInputPort) moduleIn.getUnitGenerator().getPortByName(inputPort);
		out.connect(in);
		putOutputConnexion(moduleOut, outputPort, moduleIn, inputPort);
		putInputConnexion(moduleIn, inputPort, moduleOut, outputPort);
		controller.handleConnectModules();
	}

	public void disconnectModules(IModelComponent moduleOut, String outputPort, IModelComponent moduleIn, String inputPort) {
		UnitOutputPort out = (UnitOutputPort) moduleOut.getUnitGenerator().getPortByName(outputPort);
		UnitInputPort in = (UnitInputPort) moduleIn.getUnitGenerator().getPortByName(inputPort);
		out.disconnect(in);
	}

	/*
	 * Called by the controller whenever a cable is disconnected but not deleted.
	 * Disconnects the output port connected to the given input port, if any.
	 */
	public void disconnectInputPort(IModelComponent module, String port) {
		Map<String,Pair<IModelComponent,String>> tmp = inputConnexions.get(module);
		if(tmp != null) {
			Pair<IModelComponent,String> p = tmp.get(port);
			UnitOutputPort out = (UnitOutputPort) p.getKey().getUnitGenerator().getPortByName(p.getValue());
			UnitInputPort in = (UnitInputPort) module.getUnitGenerator().getPortByName(port);



			out.disconnect(in);
			tmp.remove(port);
			inputConnexions.put(module, tmp);
		}
	}

	public void disconnectAllInputPort(IModelComponent module) {
		Map<String,Pair<IModelComponent,String>> tmp = inputConnexions.get(module);
		if(tmp != null) {
			tmp.forEach((s,p) -> {
				UnitOutputPort out = (UnitOutputPort) p.getKey().getUnitGenerator().getPortByName(p.getValue());
				UnitInputPort in = (UnitInputPort) module.getUnitGenerator().getPortByName(s);
				out.disconnect(in);
			});

			inputConnexions.remove(module);
		}
	}

	/*
	 * Called by the controller whenever a cable is disconnected but not deleted.
	 */
	public void disconnectOutputPort(IModelComponent module, String port) {
		/*
		 * Retrieve all connexions from this module
		 */
		Map<String,Pair<IModelComponent,String>> tmp = outputConnexions.get(module);
		if(tmp != null) {
			Pair<IModelComponent,String> p = tmp.get(port);
			UnitOutputPort out = (UnitOutputPort) module.getUnitGenerator().getPortByName(port);
			UnitInputPort in = (UnitInputPort) p.getKey().getUnitGenerator().getPortByName(p.getValue());
			out.disconnect(in);
			tmp.remove(port);
			outputConnexions.put(module, tmp);
		}
	}

	public void disconnectAllOutputPort(IModelComponent module) {
		Map<String,Pair<IModelComponent,String>> tmp = outputConnexions.get(module);
		if(tmp != null) {
			tmp.forEach((s,p) -> {
				UnitOutputPort out = (UnitOutputPort) module.getUnitGenerator().getPortByName(s);
				UnitInputPort in = (UnitInputPort) p.getKey().getUnitGenerator().getPortByName(p.getValue());
				out.disconnect(in);
			});
			outputConnexions.remove(module);
		}
	}

	private void removeConnexion(IModelComponent moduleOut, String outputPort, IModelComponent moduleIn, String inputPort) {
		Map<String,Pair<IModelComponent,String>> tmpOut = outputConnexions.get(moduleOut);
		if(tmpOut != null) {
			tmpOut.remove(outputPort);
			outputConnexions.put(moduleOut, tmpOut);
		}
		Map<String,Pair<IModelComponent,String>> tmpIn = inputConnexions.get(moduleIn);
		if(tmpIn != null) {
			tmpIn.remove(inputPort);
			inputConnexions.put(moduleIn, tmpIn);
		}

	}

	private void putOutputConnexion(IModelComponent moduleOut, String outputPort, IModelComponent moduleIn, String inputPort) {
		Map<String,Pair<IModelComponent,String>> tmp = outputConnexions.get(moduleOut);
		if(tmp == null) {
			tmp = new HashMap<String,Pair<IModelComponent,String>>();
		}
		tmp.put(outputPort, new Pair<IModelComponent,String>(moduleIn,inputPort));
	}

	private void putInputConnexion(IModelComponent moduleIn, String inputPort, IModelComponent moduleOut, String outputPort) {
		Map<String,Pair<IModelComponent,String>> tmp = inputConnexions.get(moduleIn);
		if(tmp == null) {
			tmp = new HashMap<String,Pair<IModelComponent,String>>();
		}
		tmp.put(inputPort, new Pair<IModelComponent,String>(moduleOut,outputPort));
	}
}
