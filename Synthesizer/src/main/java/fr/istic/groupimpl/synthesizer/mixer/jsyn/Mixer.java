package fr.istic.groupimpl.synthesizer.mixer.jsyn;

import java.util.ArrayList;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.UnitSource;

import fr.istic.groupimpl.synthesizer.util.jsyn.JsynAttenuationFilter;

/**
 * MIXER Circuit with decibels attenuation input.
 * 
 * Input audio is sent to the mixer sum audio device with an attenuation factor.
 * 
 * UnitGenerator included :
 * - JsynAttenuationFilter
 * - MixerSum
 */
public class Mixer extends Circuit implements UnitSource {	
	/* Declare units that will be part of the circuit. */
	private ArrayList<JsynAttenuationFilter> attenuators = new ArrayList<JsynAttenuationFilter>();
	private MixerSum mixerSum;

	/* Declare ports. */
	private ArrayList<UnitInputPort> unitInputPorts = new ArrayList<UnitInputPort>();
	private UnitOutputPort output;

    /**
     * Get Number Of Input Port
     * @return UnitInputPort
     */
    public Integer getNumberOfInputPort() {
		return unitInputPorts.size();
	}
    
    /**
     * Get the input of Mixer module
     * 
     * Input(index) [first index = 0]
     * @return UnitInputPort
     */
    public UnitInputPort getInput(Integer index) {
		return unitInputPorts.get(index);
	}
	
	@Override
	/**
	 * Get the output of Mixer module.
	 */
	public UnitOutputPort getOutput() {
		return output;
	}
	
    /**
     * Constructor
     * 
     * @param NumberOfInputPort
     *   number of input port to instantiate
     */
	public Mixer(Integer NumberOfInputPort) {	
		/* Create various unit generators. */
    	Integer index;
        for(int i = 0; i <= NumberOfInputPort - 1; i++)
        {
        	index = i+1;
        	attenuators.add(new JsynAttenuationFilter());
        	
        	/* Add unit generators to circuit. */
        	add(attenuators.get(i));
        	
        	// default value of the attenuator
        	attenuators.get(i).set(0);
        } 
		mixerSum = new MixerSum(4);
		/* Add mixerSum unit to circuit. */
		add(mixerSum);
		
		/* Make ports on internal units appear as ports on circuit. */
		/* Optionally give some circuit ports more meaningful names. */	
        for(int i = 0; i <= NumberOfInputPort - 1; i++)
        {
        	index = i+1;
        	unitInputPorts.add((UnitInputPort) addNamedPort(attenuators.get(i).getInput(), "mixer_input" + index));
        } 
				
		/* Connect SynthUnits to make control signal path. */
        for(int i = 0; i <= NumberOfInputPort - 1; i++)
        {
    		mixerSum.getInput(i).connect(attenuators.get(i).output);
        }
		
		output = (UnitOutputPort) addNamedPort(mixerSum.getOutput(), "output");
	}
	
	/**
	 * Set an attenuation decibel value value to the input
	 * 
	 * @param index
	 * 		index of the atteanuator (first = 0)
	 * @param dbValue    
	 */
	public void setAttenuation(Integer index, double dbValue) {
		attenuators.get(index).set(dbValue);
	}

	/**
	 * Set Mute to the output signal of the index input
	 * @param index
	 * 		Input index
	 * @param value
	 *     true|false
	 */
	public void setMute(Integer index, boolean value) {
		attenuators.get(index).setMute(value);
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
