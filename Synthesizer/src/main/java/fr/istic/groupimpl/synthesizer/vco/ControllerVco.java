package fr.istic.groupimpl.synthesizer.vco;

import fr.istic.groupimpl.synthesizer.component.IControllerComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

public class ControllerVco implements IControllerComponent {
	
	private ModelVco modelVco;
	private ControllerGlobal ctrlGlob;
	
	public ControllerVco() {
		modelVco = new ModelVco();
		ctrlGlob = ControllerGlobal.getInstance();
		ctrlGlob.registerUnitGenerator(modelVco.getUnitGenerator());
	}
		
	@Override
	public void handleViewInputClick(String portName) {
		System.err.println(modelVco.getInputPort(portName));
		ctrlGlob.handleInputClicked(modelVco.getInputPort(portName));
	}

	@Override
	public void handleViewOutputClick(String portName) {
		System.err.println(modelVco.getOutputPort(portName));
		ctrlGlob.handleOutputClicked(modelVco.getOutputPort(portName));
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
		double realOctave = octave;
		// Test precision : No negative values
		if (octave + precision >= 0.0) {
			realOctave+= precision;
		}
		modelVco.setOctave(realOctave);
	}

}
