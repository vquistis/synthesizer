package fr.istic.groupimpl.synthesizer.vco;

import javafx.beans.property.StringProperty;
import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

/**
 * Controller of vco module.
 *
 * @author Team GroupImpl
 */
public class ControllerVco extends ControllerComponent {

	/** The model vco. */
	private ModelVco modelVco;
	
	/** The ctrl glob. */
	private ControllerGlobal ctrlGlob;

	/**
	 * Constructor.
	 *
	 * @param uiFreqLabel the ui freq label
	 */
	public ControllerVco(StringProperty uiFreqLabel) {
		modelVco = new ModelVco();
		modelVco.setCommandProperty("freq", () ->
		uiFreqLabel.set(modelVco.getValProperty("freq") + " Hz")
				);
		modelVco.setOctave(0.0);
		ctrlGlob = ControllerGlobal.getInstance();
		ctrlGlob.registerUnitGenerator(modelVco.getUnitGenerator());
	}

	/**
	 * @see fr.istic.groupimpl.synthesizer.component.ControllerComponent#handleViewClose()
	 */
	@Override
	public void handleViewClose() {
		ctrlGlob.removeAllConnections(modelVco.getAllPorts());
		ctrlGlob.unregisterUnitGenerator(modelVco.getUnitGenerator());
	}

	/**
	 * Handle view octave change.
	 *
	 * @param octave The octave value (1 : 8) from the octave knob
	 * @param precision The precision value (-1 : 1) from the precision knob
	 * Computes the total octave value and changes it in the model
	 */
	public void handleViewOctaveChange(double octave, double precision) {
		modelVco.setOctave(octave + precision);
	}

	/**
	 * Handle view output type change.
	 *
	 * @param typeName : square | triangle | sawtooth
	 * Sets in the model the type of output signal
	 */
	public void handleViewOutputTypeChange(String typeName) {
		modelVco.setOutputType(typeName);
	}

	/**
	 * Handle view base freq change.
	 *
	 * @param value The frequency to set (in Hz)
	 * Sets in the model the base frequency
	 */
	public void handleViewBaseFreqChange(double value) {
		modelVco.setBaseFreq(value);
	}
	
	/**
	 * Handle view amplitude change.
	 *
	 * @param value The amplitude to set (in V)
	 * Sets in the model the amplitude
	 */
	public void handleViewAmplitudeChange(double value) {
		modelVco.setAmplitude(value);
	}

	/**
	 * @see fr.istic.groupimpl.synthesizer.component.ControllerComponent#getModel()
	 */
	@Override
	public ModelComponent getModel() {
		return modelVco;
	}

}
