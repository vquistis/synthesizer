package fr.istic.groupimpl.synthesizer.vco;

import fr.istic.groupimpl.synthesizer.component.IControllerComponent;

public class ControllerVco implements IControllerComponent {
	
	private ModelVco modelVco;	
	
	public ControllerVco() {
		modelVco = new ModelVco();
		// Test, à suppr
		modelVco.setCommandProperty("octave", () -> handleModelOctaveChange());
	}
	
	
	/* =================== Event handles FROM the view TO the model =================== */
	
	public void handleViewOctaveChange(double octave, double precision) {
		System.err.println("Octave (view) Potentiomètre changed : " + octave + " / " + precision);
		modelVco.setValProperty("octave", octave + precision);
		modelVco.setJsynOctave(octave + precision);
	}
	
	
	/* =================== Event handles FROM the model TO the view =================== */
	
	/* (TEST, à suppr) */
	public void handleModelOctaveChange() {
		System.err.println("Octave (modèle) Potentiomètre changed : " + modelVco.getValProperty("octave"));
	}

}
