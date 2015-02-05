package fr.istic.groupimpl.synthesizer.vco;

import javafx.beans.property.DoubleProperty;
import javafx.scene.control.Label;
import fr.istic.groupimpl.synthesizer.component.IControllerComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

public class ControllerVco implements IControllerComponent {
	
	private ModelVco modelVco;
	private ControllerGlobal ctrlGlob;
	
	public ControllerVco(Label uiFreqLabel) {
		modelVco = new ModelVco();
		modelVco.setCommandProperty("freq", () ->
			uiFreqLabel.setText(modelVco.getValProperty("freq") + " Hz")
		);
		modelVco.setOctave(0.0);
		ctrlGlob = ControllerGlobal.getInstance();
		ctrlGlob.registerUnitGenerator(modelVco.getUnitGenerator());
	}
		
	@Override
	public void handleViewInputClick(String portName, DoubleProperty xCoord, DoubleProperty yCoord) {
		ctrlGlob.handleInputClicked(modelVco.getInputPort(portName), xCoord, yCoord);
	}

	@Override
	public void handleViewOutputClick(String portName, DoubleProperty xCoord, DoubleProperty yCoord) {
		ctrlGlob.handleOutputClicked(modelVco.getOutputPort(portName), xCoord, yCoord);
	}

	@Override
	public void handleViewClose() {
		ctrlGlob.removeAllConnections(modelVco.getAllPorts());
		ctrlGlob.unregisterUnitGenerator(modelVco.getUnitGenerator());
	}
	
	/**
	 * @param octave The octave value (1 : 8) from the octave knob
	 * @param precision The precision value (-1 : 1) from the precision knob
	 * Computes the total octave value and changes it in the model
	 */
	public void handleViewOctaveChange(double octave, double precision) {
		modelVco.setOctave(octave + precision);
	}
	
	/**
	 * @param typeName : square | triangle | sawtooth
	 * Sets in the model the type of output signal
	 */
	public void handleViewOutputTypeChange(String typeName) {
		modelVco.setOutputType(typeName);
	}

}
