package fr.istic.groupimpl.synthesizer.vco;

import javafx.beans.property.DoubleProperty;
import javafx.scene.control.Label;
import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

public class ControllerVco extends ControllerComponent {
	
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
	
	/**
	 * @param value The frequency to set (in Hz)
	 * Sets in the model the base frequency
	 */
	public void handleViewBaseFreqChange(double value) {
		modelVco.setBaseFreq(value);
	}
	/**
	 * @param value The amplitude to set (in V)
	 * Sets in the model the amplitude
	 */
	public void handleViewAmplitudeChange(double value) {
		modelVco.setAmplitude(value);
	}

	@Override
	public ModelComponent getModel() {
		// TODO Auto-generated method stub
		return modelVco;
	}

}
