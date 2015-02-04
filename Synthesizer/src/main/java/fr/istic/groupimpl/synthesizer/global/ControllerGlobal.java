package fr.istic.groupimpl.synthesizer.global;

import java.util.Collection;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.logger.Log;

public class ControllerGlobal {

	private static ControllerGlobal instance;

	public static ControllerGlobal getInstance() {
		if(instance == null) {
			instance = new ControllerGlobal();
		}
		return instance;
	}

	private ModelGlobal model;
	private ViewGlobal view;

	private CableMode cableMode = CableMode.NONE_CONNECTED;
	/*
	 * By default, when the user clicks on a port, a new cable is created.
	 * However, if the user clicks on an already connected port, the existing
	 * cable should be in a "move" state.
	 */
	private UnitPort previousPort;

	private UnitPort currentPort;

	private ControllerGlobal() {
		model = new ModelGlobal(this);
	}

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

	public void registerOutUnitGenerator(UnitGenerator unitGen) {
		model.addOutUnit(unitGen);
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
			/*
			 * No connection is currently in the process of creation.
			 * If the input port that was clicked is already part of a connection,
			 * we should enter in a "move" move.
			 * If not, we should enter in a "create" mode.
			 */
			if(model.isPortConnected(port)) {
				cableMode = CableMode.OUT_CONNECTED;
				currentPort = model.getConnectedPort(currentPort);
				model.disconnectInputPort(port);
				previousPort = port;
				//TODO notify the view to bind the input end of the cable to the mouse.
			} else {
				cableMode = CableMode.IN_CONNECTED;
				currentPort = port;
				//TODO notify the view to create a new, unbound cable originating from the given port.
			}
			break;
		case IN_CONNECTED:
			/*
			 * An input port is connected to the cable. Clicking on another (or the same) input port
			 * should not have any effect.
			 */
			break;
		case OUT_CONNECTED:
			/*
			 * An output port is connected to the cable. By clicking on an input port,
			 * we should ask the model whether this input port is already part of a connection
			 * or not. If it is, do nothing. If it isn't, create a new connection.
			 */
			if(!model.isPortConnected(port)) {
				cableMode = CableMode.NONE_CONNECTED;
				this.model.connectPorts(currentPort, port);
				currentPort = null;
				previousPort = null;
			}
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
			previousPort = null;
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
		if(previousPort != null) {
			if(previousPort instanceof UnitInputPort) {
				this.model.connectPorts(currentPort, previousPort);
			} else {
				this.model.connectPorts(previousPort, currentPort);
			}
		}
		currentPort = null;
		previousPort = null;
		
		//TODO notify the view to stop displaying the cable.
	}

	public void setView(ViewGlobal view) {
		this.view = view;
	}

	private enum CableMode {
		NONE_CONNECTED,IN_CONNECTED,OUT_CONNECTED
	}

}
