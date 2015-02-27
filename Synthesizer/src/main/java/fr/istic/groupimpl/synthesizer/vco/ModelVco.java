package fr.istic.groupimpl.synthesizer.vco;

import java.util.Collection;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.util.DoubleStringConverter;
import fr.istic.groupimpl.synthesizer.vco.jsyn.VCOCircuit;

/**
 * Model of vco module.
 *
 * @author Team GroupImpl
 */
public class ModelVco extends ModelComponent {

	/** The vco circ. */
	private VCOCircuit vcoCirc;
	
	/** The f0. */
	private double f0 = 1.0;

	/**
	 * Constructor.
	 */
	public ModelVco() {
		super();		
		vcoCirc = new VCOCircuit();
		vcoCirc.getInputF0().set(f0); // Default F0
		vcoCirc.getInputShape().set(3); // Default type : square
	}

	/**
	 * @see fr.istic.groupimpl.synthesizer.component.IModelComponent#getUnitGenerator()
	 */
	@Override
	public UnitGenerator getUnitGenerator() {
		return this.vcoCirc;
	}

	/**
	 * Sets the octave.
	 *
	 * @param octave Sets the octave value to the VCO Circuit
	 */
	protected void setOctave(double octave) {
		vcoCirc.getInputOctave().set(octave);
		computeFrequency(octave);
	}

	/**
	 * Sets the output type.
	 *
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

	/**
	 * Get the jsyn input port.
	 * @return UnitInputPort
	 */
	public UnitInputPort getInputPort() {
		return vcoCirc.getInputFM();
	}

	/**
	 * Get the jsyn output port.
	 * @return UnitOutputPort
	 */
	public UnitOutputPort getOutputPort() {
		return vcoCirc.getOutput();
	}

	/**
	 * @see fr.istic.groupimpl.synthesizer.component.IModelComponent#getAllPorts()
	 */
	@Override
	public Collection<UnitPort> getAllPorts() {
		return this.vcoCirc.getPorts();
	}

	/**
	 * Compute frequency.
	 *
	 * @param octave the octave
	 */
	private void computeFrequency(double octave) {
		double frequency = f0 * Math.pow(2, octave);
		DoubleStringConverter dsc = new DoubleStringConverter();
		this.setValProperty("freq", dsc.toString(frequency));
	}

	/**
	 * Sets the base freq.
	 *
	 * @param value The frequency to set (in Hz)
	 * Sets the base frequency of the VCO
	 */
	public void setBaseFreq(double value) {
		f0 = value;
		vcoCirc.getInputF0().set(value);
		computeFrequency(vcoCirc.getInputOctave().get());
	}
	
	/**
	 * Sets the amplitude.
	 *
	 * @param value The amplitude in volt
	 * Sets the amplitude of signal
	 */
	public void setAmplitude(double value) {
		vcoCirc.setAmplitude(value);
	}

}
