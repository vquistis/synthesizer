package fr.istic.groupimpl.synthesizer.vca;

import javafx.beans.property.DoubleProperty;
import javafx.scene.control.Label;
import fr.istic.groupimpl.synthesizer.component.IControllerComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

public class ControllerVca implements IControllerComponent {
	
	private ModelVca modelVca;
	private ControllerGlobal ctrlGlob;
	
	public ControllerVca(Label uiFreqLabel) {
		modelVca = new ModelVca();
		modelVca.setCommandProperty("freq", () ->
			uiFreqLabel.setText(modelVca.getValProperty("freq") + " db")
		);
		modelVca.setOctave(0.0);
		ctrlGlob = ControllerGlobal.getInstance();
		ctrlGlob.registerUnitGenerator(modelVca.getUnitGenerator());
	}
		
	@Override
	public void handleViewInputClick(String portName, DoubleProperty xCoord, DoubleProperty yCoord) {
		ctrlGlob.handleInputClicked(modelVca.getInputPort(portName), xCoord, yCoord);
	}

	@Override
	public void handleViewOutputClick(String portName, DoubleProperty xCoord, DoubleProperty yCoord) {
		ctrlGlob.handleOutputClicked(modelVca.getOutputPort(portName), xCoord, yCoord);
	}

	@Override
	public void handleViewClose() {
		ctrlGlob.removeAllConnections(modelVca.getAllPorts());
		ctrlGlob.unregisterUnitGenerator(modelVca.getUnitGenerator());
	}

	public void handleViewOctaveChange(double octave, double precision) {
		modelVca.setOctave(octave + precision);
	}
}
