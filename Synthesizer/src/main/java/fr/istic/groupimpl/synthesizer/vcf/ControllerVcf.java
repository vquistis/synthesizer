package fr.istic.groupimpl.synthesizer.vcf;

import javafx.beans.property.DoubleProperty;
import fr.istic.groupimpl.synthesizer.component.IControllerComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

public class ControllerVcf implements IControllerComponent {

	private ModelVcf model = new ModelVcf();
	
	public ControllerVcf() {
		ControllerGlobal.getInstance().registerUnitGenerator(model.getUnitGenerator());
	}
	
	/**
	 * Change listener frequency cutoff.
	 * @param newVal - new frequency value
	 */
	public void handleViewCutoffChange(Number newVal) {
		model.setCutFrequency((double) newVal);
	}
	
	/**
	 * Change listener resonance.
	 * @param newVal - new resonance value
	 */
	public void handleViewResonanceChange(Number newVal) {
		model.setResonance((double) newVal);
	}
	
	@Override
	public void handleViewInputClick(String portName, DoubleProperty xCoord, DoubleProperty yCoord) {
		ControllerGlobal.getInstance().handleInputClicked(model.getInputPort(portName), xCoord, yCoord);
	}
	
	public void handleViewFmClick(DoubleProperty xCoord, DoubleProperty yCoord) {
		ControllerGlobal.getInstance().handleInputClicked(model.getFmPort(), xCoord, yCoord);
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
