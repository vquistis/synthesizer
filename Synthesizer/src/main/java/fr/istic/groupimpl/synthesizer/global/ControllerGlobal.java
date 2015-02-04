package fr.istic.groupimpl.synthesizer.global;

import java.util.Collection;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

public class ControllerGlobal {
	
	private static ControllerGlobal instance;
	
	private ControllerGlobal() {}
	
	public static ControllerGlobal getInstance() {
		if(instance == null) {
			instance = new ControllerGlobal();
		}
		return instance;
	}

	private ModelGlobal model;
	private ViewGlobal view;

	private CableMode cableMode = CableMode.NONE_CONNECTED;
	
	private UnitPort currentPort;

	/**
	 * Signals the view that a visual connection must be made between
	 * two ports.
	 */
	public void handleConnectModules() {
		//TODO notify the view to bind the cable.
	}

	/**
	 * Signals the view that the visual connection must between two ports
	 * must be removed.
	 */
	public void handleDisconnectModules() {
		//TODO notify the view to unbind the cable.
	}

	/**
	 * Signals the model that the given UnitGenerator must be added
	 * to the synthesizer.
	 * @param unitGen
	 */
	public void registerUnitGenerator(UnitGenerator unitGen) {
		model.addUnitGenerator(unitGen);
	}
	
	/**
	 * Signals the model that the given UnitGenerator must be removed
	 * from the synthesizer.
	 * @param unitGen
	 */
	public void unregisterUnitGenerator(UnitGenerator unitGen) {
		model.removeUnitGenerator(unitGen);
	}
	
	/**
	 * Signals the model that any connection originating from each port in
	 * the given collection must be removed.
	 * @param unitports
	 */
	public void removeAllConnections(Collection<UnitPort> unitports) {
		model.removeAllConnections(unitports);
	}

	/**
	 * Handles the process of connection creation when an input port is clicked.
	 * @param port
	 */
	public void handleInputClicked(UnitInputPort port) {
		switch(cableMode) {
		case NONE_CONNECTED:
			cableMode = CableMode.IN_CONNECTED;
			currentPort = port;
			//TODO notify the view to create a new, unbound cable originating from the given port.
			break;
		case IN_CONNECTED:
			break;
		case OUT_CONNECTED:
			cableMode = CableMode.NONE_CONNECTED;
			this.model.connectPorts(currentPort, port);
			currentPort = null;
			break;
		default:
		}
	}

	/**
	 * Handles the process of connection creation when an output port is clicked.
	 * @param port
	 */
	public void handleOutputClicked(UnitOutputPort port) {
		switch(cableMode) {
		case NONE_CONNECTED:
			cableMode = CableMode.OUT_CONNECTED;
			currentPort = port;
			//TODO notify the view to create a new, unbound cable originating from the given port.
			break;
		case IN_CONNECTED:
			cableMode = CableMode.NONE_CONNECTED;
			this.model.connectPorts(port, currentPort);
			currentPort = null;
			break;
		case OUT_CONNECTED:
			break;
		default:
		}
	}

	/**
	 * Aborts the connection creation process.
	 */
	public void handleLeftButtonClicked() {
		cableMode = CableMode.NONE_CONNECTED;
		currentPort = null;
		//TODO notify the view to stop displaying the cable.
	}

	private enum CableMode {
		NONE_CONNECTED,IN_CONNECTED,OUT_CONNECTED
	}
	
}
