package fr.istic.groupimpl.synthesizer.vcf.jsyn;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.FilterBiquadCommon;
import com.jsyn.unitgen.FilterHighPass;
import com.jsyn.unitgen.FilterLowPass;

import fr.istic.groupimpl.synthesizer.util.jsyn.JsynFrequencyModulation;
import fr.istic.groupimpl.synthesizer.vcf.ModelVcf;

/**
 * VCF Circuit with filter low pass and fm input.
 * 
 * UnitGenerator included :
 * - JsynFrequencyModulation
 * - FilterBiquadCommon : FilterLowPass or FilterHighPass
 * 
 * @author Team GroupImpl
 * 
 */
public class JsynVcfCircuit extends Circuit {
	/* Declare units that will be part of the circuit. */
	/** The compute freq. */
	private JsynFrequencyModulation computeFreq;
	
	/** The filter1. */
	private FilterBiquadCommon filter1; // filter LP or HP : 12dB/octave
	
	/** The filter2. */
	private FilterBiquadCommon filter2; // filter LP or HP : 12dB/octave

	/* Declare ports. */
	/** The input. */
	private UnitInputPort input;
	
	/** The fm. */
	private UnitInputPort fm;
	
	/** The output. */
	private UnitOutputPort output;
	
	/**
	 * Gets the input.
	 *
	 * @return the input
	 */
	public UnitInputPort getInput() {
		return input;
	}
	
	/**
	 * Gets the fm.
	 *
	 * @return the fm
	 */
	public UnitInputPort getFm() {
		return fm;
	}
	
	/**
	 * Gets the output.
	 *
	 * @return the output
	 */
	public UnitOutputPort getOutput() {
		return output;
	}
	
	/**
	 * Instantiates a new jsyn vcf circuit.
	 *
	 * @param t the t
	 */
	public JsynVcfCircuit(ModelVcf.Type t) {
		/* Create various unit generators. */
		computeFreq = new JsynFrequencyModulation();
		
		/* Octave is not necessary, 0 is the neutral element */
		computeFreq.getInputOctave().set(0);
		
		/* Add unit generators to circuit. */
		add(computeFreq);
		switch (t) {
			case LP24:
				filter1 = new FilterLowPass();
				filter2 = new FilterLowPass();
				
				add(filter1);
				add(filter2);

				/* Connect SynthUnits to make control signal path. */
				input = (UnitInputPort) addNamedPort(filter1.input, "vcf_input");
				filter1.output.connect(filter2.input);
				break;
			case HP12:
				filter1 = new FilterHighPass();
				filter2 = new FilterHighPass();
				
				add(filter2);
	
				/* Connect SynthUnits to make control signal path. */
				input = (UnitInputPort) addNamedPort(filter2.input, "vcf_input");
				break;
		}
		/* Connect SynthUnits to make control signal path. */
		fm = (UnitInputPort) addNamedPort(computeFreq.getInputfm(), "vcf_fm");
		output = (UnitOutputPort) addNamedPort(filter2.output, "vcf_output");

		filter1.frequency.connect(computeFreq.getOutput());
		filter2.frequency.connect(computeFreq.getOutput());
	}
	
	/**
	 * Sets the cut frequency.
	 *
	 * @param value the new cut frequency
	 */
	public void setCutFrequency(double value) {
		computeFreq.getInputf0().set(value);
	}
	
	/**
	 * Sets the resonance.
	 *
	 * @param value the new resonance
	 */
	public void setResonance(double value) {
		filter1.Q.set(value);
		filter2.Q.set(value);
	}
	
	/**
	 * add a named port to the circuit and return its instance.
	 *
	 * @param UnitPort   instance to add
	 * @param name   Port Name
	 * @return   Instance named port
	 */
	private UnitPort addNamedPort(UnitPort UnitPort, String name) {
		addPort(UnitPort, name);
		return getPortByName(name);
	}
}
