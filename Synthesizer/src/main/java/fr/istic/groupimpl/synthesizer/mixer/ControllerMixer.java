package fr.istic.groupimpl.synthesizer.mixer;

import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

public class ControllerMixer extends ControllerComponent {
	private ModelMixer model;
	
	public ControllerMixer(Integer NumberOfInputPort) {
		model = new ModelMixer(NumberOfInputPort);
		ControllerGlobal.getInstance().registerUnitGenerator(model.getUnitGenerator());
	}

	/**
	 * Get the number of input port
	 * 
	 * @return Integer
	 */
	public Integer getNumberOfInputPort() {
		return model.getNumberOfInputPort();
	}
	
	/**
	 * Change listener for sound attenuation (volume).
	 * @param index
	 * 		index input port
	 * @param newVal - new value of attenuation in db
	 */
	public void handleViewVolumeChange(Integer index, Number newVal) {
		model.setAttenuation(index, (double) newVal);
	}

	/**
	 * Change listener for mute choice
	 * @param index
	 * 		index input port
	 * @param newVal - true for mute
	 */
	public void handleViewMuteChange(Integer index, Boolean newVal) {
		model.setMute(index, newVal);
	}
	
	@Override
	public void handleViewClose() {
		ControllerGlobal.getInstance().removeAllConnections(model.getAllPorts());
		ControllerGlobal.getInstance().unregisterOutUnitGenerator(model.getUnitGenerator());
	}

	@Override
	public ModelComponent getModel() {
		// TODO Auto-generated method stub
		return model;
	}
}
