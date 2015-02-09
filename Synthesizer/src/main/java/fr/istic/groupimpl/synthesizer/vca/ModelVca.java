package fr.istic.groupimpl.synthesizer.vca;

import java.util.Collection;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.util.DoubleStringConverter;
import fr.istic.groupimpl.synthesizer.vco.jsyn.VCOCircuit;

public class ModelVca extends ModelComponent {
	
	private VCOCircuit vcaCirc;
	private int a0 = 32;
	private int am;
		
	public ModelVca() {
		super();				
		vcaCirc = new VCOCircuit();
	}

	@Override
	public UnitGenerator getUnitGenerator() {
		return this.vcaCirc;
	}
	
	/**
	 * @param octave
	 * Sets the octave value to the VCA Circuit
	 */
	protected void setOctave(double octave) {
		vcaCirc.getInputOctave().set(octave);
		computeFrequency(octave);
	}

	@Override
	public UnitInputPort getInputPort(String portName) {
		if (portName.equals("vca_inputFm")) {
			return vcaCirc.getInputFM();
		}
		return null;
	}

	@Override
	public UnitOutputPort getOutputPort(String portName) {
		if (portName.equals("vca_output")) {
			return vcaCirc.getOutput();
		}
		return null;
	}

	@Override
	public Collection<UnitPort> getAllPorts() {
		return this.vcaCirc.getPorts();
	}
	
	private void computeFrequency(double octave) {
		double frequency = a0 * Math.pow(2, octave);
		DoubleStringConverter dsc = new DoubleStringConverter();
		this.setValProperty("freq", dsc.toString(frequency));
	}
		
}
