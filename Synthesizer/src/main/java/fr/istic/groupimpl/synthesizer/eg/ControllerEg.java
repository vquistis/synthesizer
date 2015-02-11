package fr.istic.groupimpl.synthesizer.eg;

import javafx.beans.property.DoubleProperty;
import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

public class ControllerEg extends ControllerComponent {

	private ModelEg model = new ModelEg();
	
	public ControllerEg() {
		ControllerGlobal.getInstance().registerUnitGenerator(model.getUnitGenerator());
	}
	
	/**
	 * Change listener attack.
	 * @param newVal - new value of attack in sec
	 */
	public void handleViewAttackChange(Number newVal) {
		model.setAttack((double) newVal);
	}
	
	/**
	 * Change listener decay.
	 * @param newVal - new value of decay in sec
	 */
	public void handleViewDecayChange(Number newVal) {
		model.setDecay((double) newVal);
	}
	
	/**
	 * Change listener sustain.
	 * @param newVal - new value of sustain in volt
	 */
	public void handleViewSustainChange(Number newVal) {
		model.setSustain((double) newVal);
	}
	
	/**
	 * Change listener release.
	 * @param newVal - new value of release in sec
	 */
	public void handleViewReleaseChange(Number newVal) {
		model.setRelease((double) newVal);
	}

	/**
	 * Click listener for input port
	 * @param xCoord Coordinate of the clicked input port (x axis)
	 * @param yCoord Coordinate of the clicked input port (y axis)
	 */
	public void handleViewInputClick(DoubleProperty xCoord, DoubleProperty yCoord) {
		ControllerGlobal.getInstance().handleInputClicked(model.getInputPort(), xCoord, yCoord);
	}

	/**
	 * Click listener for output port
	 * @param xCoord Coordinate of the clicked output port (x axis)
	 * @param yCoord Coordinate of the clicked output port (y axis)
	 */
	public void handleViewOutputClick(DoubleProperty xCoord, DoubleProperty yCoord) {
		ControllerGlobal.getInstance().handleOutputClicked(model.getOutputPort(), xCoord, yCoord);
	}

	@Override
	public void handleViewClose() {
		ControllerGlobal.getInstance().removeAllConnections(model.getAllPorts());
		ControllerGlobal.getInstance().unregisterUnitGenerator(model.getUnitGenerator());
	}

	@Override
	public ModelComponent getModel() {
		// TODO Auto-generated method stub
		return model;
	}
}
