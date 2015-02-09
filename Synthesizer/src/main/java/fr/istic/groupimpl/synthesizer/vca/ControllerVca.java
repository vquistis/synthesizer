package fr.istic.groupimpl.synthesizer.vca;

import javafx.beans.property.DoubleProperty;
import javafx.scene.control.Label;
import fr.istic.groupimpl.synthesizer.component.IControllerComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

/**
 * The Class ControllerVca.
 */
public class ControllerVca implements IControllerComponent {
	
	/** The model vca. */
	private ModelVca modelVca;
	
	/** The ctrl glob. */
	private ControllerGlobal ctrlGlob;
	
	/**
	 * Instantiates a new controller vca.
	 *
	 * @param uiFreqLabel the ui freq label
	 */
	public ControllerVca(Label uiFreqLabel) {
		modelVca = new ModelVca();
		modelVca.setCommandProperty("freq", () ->
			uiFreqLabel.setText(modelVca.getValProperty("freq") + " db")
		);
		ctrlGlob = ControllerGlobal.getInstance();
		ctrlGlob.registerUnitGenerator(modelVca.getUnitGenerator());
	}
		
	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.IControllerComponent#handleViewInputClick(java.lang.String, javafx.beans.property.DoubleProperty, javafx.beans.property.DoubleProperty)
	 */
	@Override
	public void handleViewInputClick(String portName, DoubleProperty xCoord, DoubleProperty yCoord) {
		ctrlGlob.handleInputClicked(modelVca.getInputPort(portName), xCoord, yCoord);
	}

	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.IControllerComponent#handleViewOutputClick(java.lang.String, javafx.beans.property.DoubleProperty, javafx.beans.property.DoubleProperty)
	 */
	@Override
	public void handleViewOutputClick(String portName, DoubleProperty xCoord, DoubleProperty yCoord) {
		ctrlGlob.handleOutputClicked(modelVca.getOutputPort(portName), xCoord, yCoord);
	}

	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.IControllerComponent#handleViewClose()
	 */
	@Override
	public void handleViewClose() {
		ctrlGlob.removeAllConnections(modelVca.getAllPorts());
		ctrlGlob.unregisterUnitGenerator(modelVca.getUnitGenerator());
	}

	/**
	 * Handle view octave change.
	 *
	 * @param octave the octave
	 * @param precision the precision
	 */
	public void handleViewAmplitudeChange(double octave, double precision) {
//		modelVca.setOctave(octave + precision);
	}
}
