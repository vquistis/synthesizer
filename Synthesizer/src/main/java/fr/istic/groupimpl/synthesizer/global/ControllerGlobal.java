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

	private CableMode cableMode;
	
	private UnitPort currentPort;

	public void handleConnectModules() {
		//TODO notify the view to bind the cable.
	}

	public void handleDisconnectModules() {
		//TODO notify the view to unbind the cable.
	}

	public void registerUnitGenerator(UnitGenerator unitGen) {
		model.addUnitGenerator(unitGen);
	}
	
	public void unregisterUnitGenerator(UnitGenerator unitGen) {
		model.removeUnitGenerator(unitGen);
	}
	
	public void removeAllConnexions(Collection<UnitPort> unitports) {
		model.removeAllConnexions(unitports);
	}

	public void handleInputClicked(UnitInputPort port) {
		switch(cableMode) {
		case NONE_CONNECTED:
			cableMode = CableMode.IN_CONNECTED;
			currentPort = port;
			//TODO notify the view to create a new, unbound cable.
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

	public void handleOutputClicked(UnitOutputPort port) {
		switch(cableMode) {
		case NONE_CONNECTED:
			cableMode = CableMode.OUT_CONNECTED;
			currentPort = port;
			//TODO notify the view to create a new, unbound cable.
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

	public void handleLeftButtonClicked() {
		cableMode = CableMode.NONE_CONNECTED;
		currentPort = null;
		//TODO notify the view to stop displaying the cable.
	}

	private enum CableMode {
		NONE_CONNECTED,IN_CONNECTED,OUT_CONNECTED
	}
	
	

}
