package fr.istic.groupimpl.synthesizer.out;

import javafx.beans.property.DoubleProperty;
import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

public class ControllerOut extends ControllerComponent {

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

	/**
	 * Click listener for input port
	 * @param xCoord Coordinate of the clicked input port (x axis)
	 * @param yCoord Coordinate of the clicked input port (y axis)
	 */
	public void handleViewInputClick(DoubleProperty xCoord, DoubleProperty yCoord) {
		ControllerGlobal.getInstance().handleInputClicked(model.getInputPort(), xCoord, yCoord);
	}

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
