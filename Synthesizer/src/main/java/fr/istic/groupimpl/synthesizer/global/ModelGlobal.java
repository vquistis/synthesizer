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
	private Map<UnitOutputPort, UnitInputPort> outputConnections;
	/*
	 * f(input) -> output
	 */
	private Map<UnitInputPort, UnitOutputPort> inputConnections;

	/**
	 * Instanciates the underlying JSyn synthesizer and starts it.
	 * @param controller
	 */
	public ModelGlobal(ControllerGlobal controller) {
		this.controller = controller;
		this.synth = JSyn.createSynthesizer();
		this.synth.start();
	}

	/**
	 * Adds the given UnitGenerator to the underlying JSyn synthesizer.
	 * @param unitGen
	 */
	public void addUnitGenerator(UnitGenerator unitGen) {
		unitGenerators.add(unitGen);
		synth.add(unitGen);
	}
	
	/**
	 * Adds the given UnitGenerator as an output module to the underlying
	 * JSyn synthesizer.
	 * @param unitGen
	 */
	public void addOutUnit(UnitGenerator unitGen) {
		unitGenerators.add(unitGen);
		synth.add(unitGen);
		unitGen.start();
	}

	/**
	 * Removes the given UnitGenerator from the underlying JSyn synthesizer.
	 * @param unitGen
	 */
	public void removeUnitGenerator(UnitGenerator unitGen) {
		synth.remove(unitGen);
		unitGenerators.remove(unitGen);
	}

	/**
	 * Connects the two given output and input ports.
	 * @param outputPort
	 * @param inputPort
	 */
	public void connectPorts(UnitPort outputPort, UnitPort inputPort) {
		UnitOutputPort out = (UnitOutputPort) outputPort;
		UnitInputPort in = (UnitInputPort) inputPort;
		out.connect(in);
		putOutputConnection(out, in);
		putInputConnection(in, out);
		controller.handleConnectModules();
	}

	/**
	 * Retrieve the output port connected to the given input port
	 * and disconnects them.
	 * @param port
	 */
	public void disconnectInputPort(UnitInputPort port) {
		UnitOutputPort out = inputConnections.get(port);
		out.disconnect(port);
		inputConnections.remove(port);
		outputConnections.remove(out);
	}

	/**
	 * Retrieve the input port connected to the given output port
	 * and disconnects them.
	 * @param port
	 */
	public void disconnectOutputPort(UnitOutputPort port) {
		UnitInputPort in = outputConnections.get(port);
		port.disconnect(in);
		inputConnections.remove(in);
		outputConnections.remove(port);
	}

	/**
	 * Adds an entry in the output connexions map with the output port as a key
	 * and the input port as a value.
	 * @param outputPort
	 * @param inputPort
	 */
	private void putOutputConnection(UnitOutputPort outputPort, UnitInputPort inputPort) {
		outputConnections.put(outputPort, inputPort);
	}

	/**
	 * Adds an entry in the input connexions map with the output port as a key
	 * and the output port as a value.
	 * @param inputPort
	 * @param outputPort
	 */
	private void putInputConnection(UnitInputPort inputPort, UnitOutputPort outputPort) {
		inputConnections.put(inputPort, outputPort);
	}
	
	/**
	 * Removes every connection originating from each port in the given list.
	 * @param unitports
	 */
	public void removeAllConnections(Collection<UnitPort> unitports) {
		unitports.forEach((p1) -> {
			if(inputConnections.containsKey(p1)) {
				UnitPort p2 = inputConnections.get(p1);
				inputConnections.remove(p1);
				outputConnections.remove(p2);
			} else if(outputConnections.containsKey(p1)) {
				UnitPort p2 = outputConnections.get(p1);
				outputConnections.remove(p1);
				inputConnections.remove(p2);
			}
		});
	}
}
