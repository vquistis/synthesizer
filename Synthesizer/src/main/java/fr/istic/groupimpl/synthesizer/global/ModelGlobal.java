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

	public void addModule(IModelComponent model) {
		modules.add(model);
		UnitGenerator unitGen = model.getUnitGenerator();
		synth.add(unitGen);
		unitGen.start();
	}

	public void removeModule(IModelComponent model) {
		/* 
		 * TODO disconnect every modules from this one before removing it:
		 * forall input of the module -> search modules connected to the input in the inputConnexions map and disconnect them
		 * forall output of the module -> search modules connected to the output in the outputConnexions map and disconnect them
		 */

		modules.remove(model);
	}



	public void connectModules(IModelComponent moduleOut, String outputPort, IModelComponent moduleIn, String inputPort) {
		UnitOutputPort out = (UnitOutputPort) moduleIn.getUnitGenerator().getPortByName(outputPort);
		UnitInputPort in = (UnitInputPort) moduleIn.getUnitGenerator().getPortByName(inputPort);
		out.connect(in);
		putOutputConnexion(moduleOut, outputPort, moduleIn, inputPort);
		putInputConnexion(moduleIn, inputPort, moduleOut, outputPort);
		controller.handleConnectModules();
	}

	public void disconnectModules(IModelComponent moduleOut, String outputPort, IModelComponent moduleIn, String inputPort) {
		UnitOutputPort out = (UnitOutputPort) moduleIn.getUnitGenerator().getPortByName(outputPort);
		UnitInputPort in = (UnitInputPort) moduleIn.getUnitGenerator().getPortByName(inputPort);
		out.disconnect(in);
		
	}
	
	public void disconnectInputPort(IModelComponent module, String port) {
//		
//		UnitOutputPort out = (UnitOutputPort) moduleIn.getUnitGenerator().getPortByName(outputPort);
//		UnitInputPort in = (UnitInputPort) moduleIn.getUnitGenerator().getPortByName(inputPort);
//		out.disconnect(in);
		
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
