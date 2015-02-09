package fr.istic.groupimpl.synthesizer.out.jsyn;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.LineOut;

import fr.istic.groupimpl.synthesizer.util.jsyn.JsynAttenuationFilter;

/**
 * OUT Circuit with a decibel attenuation input.
 * 
 * Input audio is sent to the external audio output device with an attenuation factor.
 * 
 * UnitGenerator included :
 * - JsynAttenuationFilter
 * - LineOut
 */
public class JsynOutCircuit extends Circuit {
	/* Declare units that will be part of the circuit. */
	private JsynAttenuationFilter attenuator;
	private LineOut out;

	/* Declare ports. */
	private UnitInputPort input;
	
	/**
	 * Get the input of OUT module.
	 */
	public UnitInputPort getInput() {
		return input;
	}
	
	public JsynOutCircuit() {
		/* Create various unit generators. */
		attenuator = new JsynAttenuationFilter();
		out = new LineOut();
		
		/* Add unit generators to circuit. */
		add(attenuator);
		add(out);

		/* Make ports on internal units appear as ports on circuit. */
		/* Optionally give some circuit ports more meaningful names. */	
		input = (UnitInputPort) addNamedPort(attenuator.getInput(), "out_input");
		
		/* Connect SynthUnits to make control signal path. */
		out.input.connect(attenuator.output);
	}
	
	/**
	 * Set an attenuation decibel value
	 */
	public void setAttenuation(double dbValue) {
		attenuator.set(dbValue);
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
