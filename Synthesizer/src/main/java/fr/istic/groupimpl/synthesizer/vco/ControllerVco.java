package fr.istic.groupimpl.synthesizer.vco;

import fr.istic.groupimpl.synthesizer.component.IControllerComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

public class ControllerVco implements IControllerComponent {
	
	private ModelVco modelVco;
	private ControllerGlobal ctrlGlob;
	
	public ControllerVco() {
		modelVco = new ModelVco();
		ctrlGlob = ControllerGlobal.getInstance();
	}	
		
	@Override
	public void handleViewInputClick(String portName) {
		ctrlGlob.handleInputClicked(modelVco.getInputPort(portName));
	}

	@Override
	public void handleViewOutputClick(String portName) {
		ctrlGlob.handleOutputClicked(modelVco.getOutputPort(portName));
	}

	@Override
	public void handleViewClose() {
		ctrlGlob.removeAllConnections(modelVco.getAllPorts());
		ctrlGlob.unregisterUnitGenerator(modelVco.getUnitGenerator());
	}
	
	public void handleViewOctaveChange(double octave, double precision) {
		double realOctave = octave;
		// Test precision : No negative values
		if (octave + precision >= 0.0) {
			realOctave+= precision;
		}
		modelVco.setJsynOctave(realOctave);
	}

}
