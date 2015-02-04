package fr.istic.groupimpl.synthesizer.vco;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.vco.jsyn.VCOCircuit;

public class ModelVco extends ModelComponent {
	
	private VCOCircuit vcoCirc;
		
	public ModelVco() {
		super();		
		vcoCirc = new VCOCircuit();
		vcoCirc.getInputF0().set(440); // Default F0
		vcoCirc.getInputOctave().set(0.0);
		vcoCirc.getOutputSquare().setName("square_vco");
		vcoCirc.getOutputTriangle().setName("triangle_vco");
		vcoCirc.getOutputSawtooth().setName("sawtooth_vco");
	}

	@Override
	public UnitGenerator getUnitGenerator() {
		return this.vcoCirc;
	}
	
	protected void setJsynOctave(double octave) {
		vcoCirc.getInputOctave().set(octave);
	}

	@Override
	public UnitInputPort getInputPort(String portName) {
		return (UnitInputPort) vcoCirc.getPortByName(portName);
	}

	@Override
	public UnitOutputPort getOutputPort(String portName) {
		return (UnitOutputPort) vcoCirc.getPortByName(portName);
	}
		
}
