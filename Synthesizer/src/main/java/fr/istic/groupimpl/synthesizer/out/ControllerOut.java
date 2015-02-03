package fr.istic.groupimpl.synthesizer.out;

import fr.istic.groupimpl.synthesizer.component.IControllerComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

public class ControllerOut implements IControllerComponent{

	private ModelOut model = new ModelOut();
	
	public ControllerOut() {
		//ControllerGlobal.registerUnitGenerator(model.getUnitGenerator());
	}
	
	public void handleViewVolumeChange(Number newVal) {
		model.setAttenuation((double) newVal);
	}

	public void handleViewMuteChange(Boolean newVal) {
		if (newVal) {
			model.start();
		} else {
			model.stop();
		}
	}

	@Override
	public void handleViewInputClick(String portName) {
		//ControllerGlobal.handleInputClick(model.getInputPort(portName));
	}

	@Override
	public void handleViewOutpuClick(String portName) {
		//This module doesn't have output port
	}
}
