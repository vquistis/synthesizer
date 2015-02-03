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

public class ModelGlobal {

	private ControllerGlobal controller;

//	private List<IModelComponent> modules = new ArrayList<IModelComponent>();
	private List<UnitGenerator> unitGenerators = new ArrayList<UnitGenerator>();
	
	private Synthesizer synth;

	/*
	 * f(output) -> input
	 */
	private Map<UnitGenerator,Map<String,Pair<UnitGenerator,String>>> outputConnexions;
	/*
	 * f(input) -> output
	 */
	private Map<UnitGenerator,Map<String,Pair<UnitGenerator,String>>> inputConnexions;

	public ModelGlobal(ControllerGlobal controller) {
		this.controller = controller;
		this.synth = JSyn.createSynthesizer();
		this.synth.start();
	}

//	public void addModule(IModelComponent module) {
//		modules.add(module);
//		UnitGenerator unitGen = module.getUnitGenerator();
//		synth.add(unitGen);
//		unitGen.start();
//	}
	public void addUnitGenerator(UnitGenerator unitGen) {
		unitGenerators.add(unitGen);
		synth.add(unitGen);
	}
	
	public void addOutUnit(UnitGenerator unitGen) {
		unitGenerators.add(unitGen);
		synth.add(unitGen);
		unitGen.start();
	}

	public void removeModule(UnitGenerator unitGen) {
		/* 
		 * TODO disconnect every modules from this one before removing it:
		 * forall input of the module -> search modules connected to the input in the inputConnexions map and disconnect them
		 * forall output of the module -> search modules connected to the output in the outputConnexions map and disconnect them
		 */
		disconnectAllInputPort(unitGen);
		disconnectAllOutputPort(unitGen);
		unitGen.stop();
		synth.remove(unitGen);
		unitGenerators.remove(unitGen);
	}

	public void connectModules(UnitGenerator moduleOut, String outputPort, UnitGenerator moduleIn, String inputPort) {
		UnitOutputPort out = (UnitOutputPort) moduleOut.getUnitGenerator().getPortByName(outputPort);
		UnitInputPort in = (UnitInputPort) moduleIn.getUnitGenerator().getPortByName(inputPort);
		out.connect(in);
		putOutputConnexion(moduleOut, outputPort, moduleIn, inputPort);
		putInputConnexion(moduleIn, inputPort, moduleOut, outputPort);
		controller.handleConnectModules();
	}

	public void disconnectModules(UnitGenerator moduleOut, String outputPort, UnitGenerator moduleIn, String inputPort) {
		UnitOutputPort out = (UnitOutputPort) moduleOut.getUnitGenerator().getPortByName(outputPort);
		UnitInputPort in = (UnitInputPort) moduleIn.getUnitGenerator().getPortByName(inputPort);
		out.disconnect(in);
	}

	/*
	 * Called by the controller whenever a cable is disconnected but not deleted.
	 * Disconnects the output port connected to the given input port, if any.
	 */
	public void disconnectInputPort(UnitGenerator module, String port) {
		Map<String,Pair<UnitGenerator,String>> tmp = inputConnexions.get(module);
		if(tmp != null) {
			Pair<UnitGenerator,String> p = tmp.get(port);
			UnitOutputPort out = (UnitOutputPort) p.getKey().getUnitGenerator().getPortByName(p.getValue());
			UnitInputPort in = (UnitInputPort) module.getUnitGenerator().getPortByName(port);



			out.disconnect(in);
			tmp.remove(port);
			inputConnexions.put(module, tmp);
		}
	}

	public void disconnectAllInputPort(UnitGenerator module) {
		Map<String,Pair<UnitGenerator,String>> tmp = inputConnexions.get(module);
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
	public void disconnectOutputPort(UnitGenerator module, String port) {
		/*
		 * Retrieve all connexions from this module
		 */
		Map<String,Pair<UnitGenerator,String>> tmp = outputConnexions.get(module);
		if(tmp != null) {
			Pair<UnitGenerator,String> p = tmp.get(port);
			UnitOutputPort out = (UnitOutputPort) module.getUnitGenerator().getPortByName(port);
			UnitInputPort in = (UnitInputPort) p.getKey().getUnitGenerator().getPortByName(p.getValue());
			out.disconnect(in);
			tmp.remove(port);
			outputConnexions.put(module, tmp);
		}
	}

	public void disconnectAllOutputPort(UnitGenerator module) {
		Map<String,Pair<UnitGenerator,String>> tmp = outputConnexions.get(module);
		if(tmp != null) {
			tmp.forEach((s,p) -> {
				UnitOutputPort out = (UnitOutputPort) module.getUnitGenerator().getPortByName(s);
				UnitInputPort in = (UnitInputPort) p.getKey().getUnitGenerator().getPortByName(p.getValue());
				out.disconnect(in);
			});
			outputConnexions.remove(module);
		}
	}

	private void removeConnexion(UnitGenerator moduleOut, String outputPort, UnitGenerator moduleIn, String inputPort) {
		Map<String,Pair<UnitGenerator,String>> tmpOut = outputConnexions.get(moduleOut);
		if(tmpOut != null) {
			tmpOut.remove(outputPort);
			outputConnexions.put(moduleOut, tmpOut);
		}
		Map<String,Pair<UnitGenerator,String>> tmpIn = inputConnexions.get(moduleIn);
		if(tmpIn != null) {
			tmpIn.remove(inputPort);
			inputConnexions.put(moduleIn, tmpIn);
		}

	}

	private void putOutputConnexion(UnitGenerator moduleOut, String outputPort, UnitGenerator moduleIn, String inputPort) {
		Map<String,Pair<UnitGenerator,String>> tmp = outputConnexions.get(moduleOut);
		if(tmp == null) {
			tmp = new HashMap<String,Pair<UnitGenerator,String>>();
		}
		tmp.put(outputPort, new Pair<UnitGenerator,String>(moduleIn,inputPort));
	}

	private void putInputConnexion(UnitGenerator moduleIn, String inputPort, UnitGenerator moduleOut, String outputPort) {
		Map<String,Pair<UnitGenerator,String>> tmp = inputConnexions.get(moduleIn);
		if(tmp == null) {
			tmp = new HashMap<String,Pair<UnitGenerator,String>>();
		}
		tmp.put(inputPort, new Pair<UnitGenerator,String>(moduleOut,outputPort));
	}
}
