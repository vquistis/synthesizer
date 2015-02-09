package fr.istic.groupimpl.synthesizer.eg;

import javafx.beans.property.DoubleProperty;
import fr.istic.groupimpl.synthesizer.component.IControllerComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

public class ControllerEg implements IControllerComponent {

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
		model.setSustain((double) newVal/5.0);
	}
	
	/**
	 * Change listener release.
	 * @param newVal - new value of release in sec
	 */
	public void handleViewReleaseChange(Number newVal) {
		model.setRelease((double) newVal);
	}
	
	@Override
	public void handleViewInputClick(String portName, DoubleProperty xCoord, DoubleProperty yCoord) {
		ControllerGlobal.getInstance().handleInputClicked(model.getInputPort(portName), xCoord, yCoord);
	}

	@Override
	public void handleViewOutputClick(String portName, DoubleProperty xCoord, DoubleProperty yCoord) {
		ControllerGlobal.getInstance().handleOutputClicked(model.getOutputPort(portName), xCoord, yCoord);
	}

	@Override
	public void handleViewClose() {
		ControllerGlobal.getInstance().removeAllConnections(model.getAllPorts());
		ControllerGlobal.getInstance().unregisterUnitGenerator(model.getUnitGenerator());
	}
}
