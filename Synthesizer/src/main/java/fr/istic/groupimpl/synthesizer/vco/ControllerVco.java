package fr.istic.groupimpl.synthesizer.vco;

import fr.istic.groupimpl.synthesizer.component.IControllerComponent;

public class ControllerVco implements IControllerComponent {
	
	private ModelVco modelVco;	
	
	public ControllerVco() {
		modelVco = new ModelVco();
		// Test, à suppr
		modelVco.setCommandProperty("octave", () -> handleModelOctaveChange());
	}
	
	
	/* =================== Event handles FROM the view =================== */
	
	public void handleViewOctaveChange(Number value) {
		System.err.println("Octave (view) Potentiomètre changed : " + value);
		modelVco.setValProperty("octave", value);
	}
	
	
	/* =================== Event handles FROM the model =================== */
	
	/* (TEST, à suppr) */
	public void handleModelOctaveChange() {
		System.err.println("Octave (modèle) Potentiomètre changed : " + modelVco.getValProperty("octave"));
	}

}
