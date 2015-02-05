package fr.istic.groupimpl.synthesizer.vco;

import java.util.Collection;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.vco.jsyn.VCOCircuit;

public class ModelVco extends ModelComponent {
	
	private VCOCircuit vcoCirc;
		
	public ModelVco() {
		super();		
		vcoCirc = new VCOCircuit();
		vcoCirc.getInputF0().set(32); // Default F0
		vcoCirc.getInputOctave().set(0.0);
		vcoCirc.getInputShape().set(3); // Default type : square
	}

	@Override
	public UnitGenerator getUnitGenerator() {
		return this.vcoCirc;
	}
	
	/**
	 * @param octave
	 * Sets the octave value to the VCO Circuit
	 */
	protected void setOctave(double octave) {
		vcoCirc.getInputOctave().set(octave);
	}
	
	/**
	 * @param typeName The name of the output type : square | triangle | sawtooth
	 * Sets the type of output signal
	 */
	protected void setOutputType(String typeName) {		
		switch(typeName) {
			case "square":
				vcoCirc.getInputShape().set(3);
				break;
			case "triangle":
				vcoCirc.getInputShape().set(1);
				break;
			case "sawtooth":
				vcoCirc.getInputShape().set(2);
				break;
		}
	}

	@Override
	public UnitInputPort getInputPort(String portName) {
		if (portName.equals("vco_inputFm")) {
			return vcoCirc.getInputFM();
		}
		return null;
	}

	@Override
	public UnitOutputPort getOutputPort(String portName) {
		if (portName.equals("vco_output")) {
			return vcoCirc.getOutput();
		}
		return null;
	}

	@Override
	public Collection<UnitPort> getAllPorts() {
		return this.vcoCirc.getPorts();
	}
		
}
