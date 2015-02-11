package fr.istic.groupimpl.synthesizer.mixer;

import javafx.beans.property.DoubleProperty;
import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

public class ControllerMixer extends ControllerComponent {
	final Integer NumberOfInputPort = 4;
	
	private ModelMixer model = new ModelMixer(NumberOfInputPort);
	
	public ControllerMixer() {
		ControllerGlobal.getInstance().registerUnitGenerator(model.getUnitGenerator());
	}

	/**
	 * Get the number of input port
	 * 
	 * @return Integer
	 */
	public Integer getNumberOfInputPort() {
		return NumberOfInputPort;
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
	
	/**
	 * Click listener for input port index
	 * @param index of the input port
	 * @param xCoord Coordinate of the clicked input port (x axis)
	 * @param yCoord Coordinate of the clicked input port (y axis)
	 */
	public void handleViewInputClick(Integer index, DoubleProperty xCoord, DoubleProperty yCoord) {
		ControllerGlobal.getInstance().handleInputClicked(model.getInputPort(index), xCoord, yCoord);
	}
	
	/**
	 * Click listener for output ports
	 * @param xCoord Coordinate of the clicked output port (x axis)
	 * @param yCoord Coordinate of the clicked output port (y axis)
	 */
	public void handleViewOutputClick(DoubleProperty xCoord, DoubleProperty yCoord) {
		ControllerGlobal.getInstance().handleOutputClicked(model.getOutputPort(), xCoord, yCoord);
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
