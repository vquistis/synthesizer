package fr.istic.groupimpl.synthesizer.global;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

public class ModelGlobal {

	private ControllerGlobal controller;

	private List<UnitGenerator> unitGenerators = new ArrayList<UnitGenerator>();
	
	private Synthesizer synth;

	/*
	 * f(output) -> input
	 */
	private Map<UnitOutputPort, UnitInputPort> outputConnexions;
	/*
	 * f(input) -> output
	 */
	private Map<UnitInputPort, UnitOutputPort> inputConnexions;

	public ModelGlobal(ControllerGlobal controller) {
		this.controller = controller;
		this.synth = JSyn.createSynthesizer();
		this.synth.start();
	}
	
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
		unitGen.stop();
		synth.remove(unitGen);
		unitGenerators.remove(unitGen);
	}

	public void connectPorts(UnitPort outputPort, UnitPort inputPort) {
		UnitOutputPort out = (UnitOutputPort) outputPort;
		UnitInputPort in = (UnitInputPort) inputPort;
		out.connect(in);
		putOutputConnexion(out, in);
		putInputConnexion(in, out);
		controller.handleConnectModules();
	}

	/*
	 * Called by the controller whenever a cable is disconnected but not deleted.
	 * Disconnects the output port connected to the given input port, if any.
	 */
	public void disconnectInputPort(UnitInputPort port) {
		UnitOutputPort out = inputConnexions.get(port);
		out.disconnect(port);
		inputConnexions.remove(port);
		outputConnexions.remove(out);
	}

	/*
	 * Called by the controller whenever a cable is disconnected but not deleted.
	 */
	public void disconnectOutputPort(UnitOutputPort port) {
		UnitInputPort in = outputConnexions.get(port);
		port.disconnect(in);
		inputConnexions.remove(in);
		outputConnexions.remove(port);
	}

	private void putOutputConnexion(UnitOutputPort outputPort, UnitInputPort inputPort) {
		outputConnexions.put(outputPort, inputPort);
	}

	private void putInputConnexion(UnitInputPort inputPort, UnitOutputPort outputPort) {
		inputConnexions.put(inputPort, outputPort);
	}
	
	public void removeAllConnexions(Collection<UnitPort> unitports) {
		unitports.forEach((p1) -> {
			if(inputConnexions.containsKey(p1)) {
				UnitPort p2 = inputConnexions.get(p1);
				inputConnexions.remove(p1);
				outputConnexions.remove(p2);
			} else if(outputConnexions.containsKey(p1)) {
				UnitPort p2 = outputConnexions.get(p1);
				outputConnexions.remove(p1);
				inputConnexions.remove(p2);
			}
		});
	}
}
