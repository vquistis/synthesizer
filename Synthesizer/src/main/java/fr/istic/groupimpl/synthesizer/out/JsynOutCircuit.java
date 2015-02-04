package fr.istic.groupimpl.synthesizer.out;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.LineOut;

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

	public JsynOutCircuit() {
		/* Create various unit generators. */
		attenuator = new JsynAttenuationFilter();
		out = new LineOut();
		
		/* Add unit generators to circuit. */
		add(attenuator);
		add(out);

		/* Connect SynthUnits to make control signal path. */
		out.input.connect(attenuator.output);
	}
	
	/**
	 * Get the input of OUT module.
	 */
	public UnitInputPort getInput() {
		return attenuator.input;
	}

	/**
	 * Set an attenuation decibel value
	 */
	public void setAttenuation(double dbValue) {
		attenuator.set(dbValue);
	}
	
//	public void start() {
//		out.start();
//	}
//	public void stop() {
//		out.stop();
//	}
}
