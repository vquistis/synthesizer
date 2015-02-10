package fr.istic.groupimpl.synthesizer.vcf.jsyn;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.FilterLowPass;

import fr.istic.groupimpl.synthesizer.util.jsyn.JsynFrequencyModulation;

/**
 * VCF Circuit with filter low pass and fm input.
 * 
 * UnitGenerator included :
 * - JsynFrequencyModulation
 * - FilterLowPass
 */
public class JsynVcfCircuit extends Circuit {
	/* Declare units that will be part of the circuit. */
	private JsynFrequencyModulation computeFreq;
	private FilterLowPass filter;

	/* Declare ports. */
	private UnitInputPort input;
	private UnitInputPort fm;
	private UnitOutputPort output;
	
	public UnitInputPort getInput() {
		return input;
	}
	public UnitInputPort getFm() {
		return fm;
	}
	public UnitOutputPort getOutput() {
		return output;
	}
	
	public JsynVcfCircuit() {
		/* Create various unit generators. */
		computeFreq = new JsynFrequencyModulation();
		filter = new FilterLowPass();
		
		/* Octave is not necessary, 0 is the neutral element */
		computeFreq.getInputOctave().set(0);
		
		/* Add unit generators to circuit. */
		add(computeFreq);
		add(filter);

		/* Make ports on internal units appear as ports on circuit. */
		/* Optionally give some circuit ports more meaningful names. */	
		input = (UnitInputPort) addNamedPort(computeFreq.getInputf0(), "vcf_input");
		fm = (UnitInputPort) addNamedPort(computeFreq.getInputfm(), "vcf_fm");
		output = (UnitOutputPort) addNamedPort(filter.output, "vcf_output");
		
		/* Connect SynthUnits to make control signal path. */
		computeFreq.getOutput().connect(filter.input);
	}
	
	public void setCutFrequency(double value) {
		filter.frequency.set(value);
	}
	
	public void setResonance(double value) {
		filter.Q.set(value);
	}
	
	/**
	 * 
	 * add a named port to the circuit and return its instance
	 * 
	 * @param UnitPort
	 *   instance to add
	 * @param name
	 *   Port Name
	 * @return
	 *   Instance named port
	 */
	private UnitPort addNamedPort(UnitPort UnitPort, String name) {
		addPort(UnitPort, name);
		return getPortByName(name);
	}
}
