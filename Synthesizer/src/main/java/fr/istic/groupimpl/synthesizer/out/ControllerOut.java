package fr.istic.groupimpl.synthesizer.out;

import javafx.beans.property.DoubleProperty;
import fr.istic.groupimpl.synthesizer.component.IControllerComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

public class ControllerOut implements IControllerComponent {

	private ModelOut model = new ModelOut();
	
	public ControllerOut() {
		ControllerGlobal.getInstance().registerOutUnitGenerator(model.getUnitGenerator());
	}
	
	/**
	 * Change listener for sound attenuation (volume).
	 * @param newVal - new value of attenuation in db
	 */
	public void handleViewVolumeChange(Number newVal) {
		model.setAttenuation((double) newVal);
	}

	/**
	 * Change listener for mute choice
	 * @param newVal - true for mute
	 */
	public void handleViewMuteChange(Boolean newVal) {
		if (newVal) {
			model.getUnitGenerator().stop();
		} else {
			model.getUnitGenerator().start();
		}
	}

	@Override
	public void handleViewInputClick(String portName, DoubleProperty xCoord, DoubleProperty yCoord) {
		ControllerGlobal.getInstance().handleInputClicked(model.getInputPort(portName), xCoord, yCoord);
	}

	@Override
	public void handleViewOutputClick(String portName, DoubleProperty xCoord, DoubleProperty yCoord) {
		//This module doesn't have output port
	}

	@Override
	public void handleViewClose() {
		ControllerGlobal.getInstance().removeAllConnections(model.getAllPorts());
		ControllerGlobal.getInstance().unregisterOutUnitGenerator(model.getUnitGenerator());
	}
}
