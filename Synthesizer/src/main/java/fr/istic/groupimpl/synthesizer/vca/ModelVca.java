package fr.istic.groupimpl.synthesizer.vca;

import java.util.Collection;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.util.DoubleStringConverter;
import fr.istic.groupimpl.synthesizer.vca.jsyn.VCACircuit;

/**
 * The Class ModelVca.
 */
public class ModelVca extends ModelComponent {
	
	/** The vca circ. */
	private VCACircuit vcaCirc;
	
	/** The a0. */
	private int a0 = 32;
	
	/** The am. */
	private int am;
		
	/**
	 * Instantiates a new model vca.
	 */
	public ModelVca() {
		super();				
		vcaCirc = new VCACircuit();
	}

	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.IModelComponent#getUnitGenerator()
	 */
	@Override
	public UnitGenerator getUnitGenerator() {
		return this.vcaCirc;
	}
	
	/**
	 * Sets the octave.
	 *
	 * @param octave Sets the octave value to the VCA Circuit
	 */
	protected void setOctave(double octave) {
		vcaCirc.getInputOctave().set(octave);
		computeFrequency(octave);
	}

	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.IModelComponent#getInputPort(java.lang.String)
	 */
	@Override
	public UnitInputPort getInputPort(String portName) {
		if (portName.equals("vca_inputFm")) {
			return vcaCirc.getInputFM();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.IModelComponent#getOutputPort(java.lang.String)
	 */
	@Override
	public UnitOutputPort getOutputPort(String portName) {
		if (portName.equals("vca_output")) {
			return vcaCirc.getOutput();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.IModelComponent#getAllPorts()
	 */
	@Override
	public Collection<UnitPort> getAllPorts() {
		return this.vcaCirc.getPorts();
	}
	
	/**
	 * Compute frequency.
	 *
	 * @param octave the octave
	 */
	private void computeFrequency(double octave) {
		double frequency = a0 * Math.pow(2, octave);
		DoubleStringConverter dsc = new DoubleStringConverter();
		this.setValProperty("freq", dsc.toString(frequency));
	}
		
}
