package fr.istic.groupimpl.synthesizer.vco;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import fr.istic.groupimpl.synthesizer.component.IControllerComponent;

public class ControllerVco implements IControllerComponent {
	
	private ModelVco modelVco;	
	
	public ControllerVco() {
		modelVco = new ModelVco();
		// Test, à suppr
		modelVco.setCommandProperty("octave", () -> handleModelOctaveChange());
	}
	
	
	/* =================== Event handles FROM the view TO the model =================== */
	
	@Override
	public void handleViewInputClick(String portName) {
		// TODO Auto-generated method stub
		throw new NotImplementedException();
	}


	@Override
	public void handleViewOutpuClick(String portName) {
		// TODO Auto-generated method stub
		throw new NotImplementedException();
	}
	
	public void handleViewOctaveChange(double octave, double precision) {
		double realOctave = octave;
		// Test precision : No negative values
		if (octave >= 1.0 || (octave <= 1.0 && precision >= 0.0)) {
			realOctave+= precision;
		}
		System.err.println("Octave (view) Potentiomètre changed : " + realOctave);
		modelVco.setValProperty("octave", realOctave);
		modelVco.setJsynOctave(realOctave);
	}
	
	
	
	
	/* =================== Event handles FROM the model TO the view =================== */
	
	/* (TEST, à suppr) */
	public void handleModelOctaveChange() {
		System.err.println("Octave (modèle) Potentiomètre changed : " + modelVco.getValProperty("octave"));
	}

}
