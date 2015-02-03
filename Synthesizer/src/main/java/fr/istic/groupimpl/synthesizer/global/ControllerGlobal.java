package fr.istic.groupimpl.synthesizer.global;

import com.jsyn.unitgen.UnitGenerator;

public class ControllerGlobal {

	private ModelGlobal model;
	private ViewGlobal view;

	private CableMode cableMode;
	
	private UnitGenerator currentUnitGen;
	private String currentPort;

	public void handleConnectModules() {
		//TODO notify the view to bind the cable.
	}

	public void handleDisconnectModules() {
		//TODO notify the view to unbind the cable.
	}

	public void registerUnitGenerator(UnitGenerator unitGen) {
		model.addUnitGenerator(unitGen);
	}

	public void handleInputClicked(UnitGenerator unitGen, String port) {
		switch(cableMode) {
		case NONE_CONNECTED:
			cableMode = CableMode.IN_CONNECTED;
			currentUnitGen = unitGen;
			//TODO notify the view to create a new, unbound cable.
			break;
		case IN_CONNECTED:
			break;
		case OUT_CONNECTED:
			cableMode = CableMode.NONE_CONNECTED;
			this.model.connectModules(currentUnitGen, currentPort, unitGen, port);
			currentUnitGen = null; currentPort = null;
			break;
		default:
		}
	}

	public void handleOutputClicked(UnitGenerator unitGen, String port) {
		switch(cableMode) {
		case NONE_CONNECTED:
			cableMode = CableMode.OUT_CONNECTED;
			currentUnitGen = unitGen;
			//TODO notify the view to create a new, unbound cable.
			break;
		case IN_CONNECTED:
			cableMode = CableMode.NONE_CONNECTED;
			this.model.connectModules(unitGen, port, currentUnitGen, currentPort);
			currentUnitGen = null; currentPort = null;
			break;
		case OUT_CONNECTED:
			break;
		default:
		}
	}

	public void handleLeftButtonClicked() {
		cableMode = CableMode.NONE_CONNECTED;
		currentUnitGen = null; currentPort = null;
		//TODO notify the view to stop displaying the cable.
	}

	private enum CableMode {
		NONE_CONNECTED,IN_CONNECTED,OUT_CONNECTED
	}

}
