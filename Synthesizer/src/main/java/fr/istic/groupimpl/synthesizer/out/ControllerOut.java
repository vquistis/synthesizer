package fr.istic.groupimpl.synthesizer.out;

import fr.istic.groupimpl.synthesizer.component.IControllerComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

public class ControllerOut implements IControllerComponent{

	private ModelOut model = new ModelOut();
	
	public ControllerOut() {
		//ControllerGlobal.registerUnitGenerator(model.getUnitGenerator());
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
			model.stop();
		} else {
			model.start();
		}
	}

	@Override
	public void handleViewInputClick(String portName) {
		//ControllerGlobal.handleInputClick(model.getInputPort(portName));
	}

	@Override
	public void handleViewOutputClick(String portName) {
		//This module doesn't have output port
	}

	@Override
	public void handleViewClose() {
		//ControllerGlobal.handleInputClick(model.getAllPorts());
	}
}
