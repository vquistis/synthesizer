package fr.istic.groupimpl.synthesizer.mixer;

import java.util.Collection;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.mixer.jsyn.Mixer;

public class ModelMixer extends ModelComponent {

	final Integer NumberOfInputPort = 4;
	private Mixer mixer;
	
	public ModelMixer() {
		super();
		mixer = new Mixer(NumberOfInputPort);
	}

	/**
	 * Get the number of input port
	 * 
	 * @return Integer
	 */
	public Integer getNumberOfInputPort() {
		return NumberOfInputPort;
	}

	
	@Override
	public UnitGenerator getUnitGenerator() {
		return mixer;
	}

	@Override
	public Collection<UnitPort> getAllPorts() {
		return mixer.getPorts();
	}
	
	/**
	 * Set an attenuation to the output signal of the index input
	 * @param index
	 * 		Input index
	 * @param dbValue
	 */
	public void setAttenuation(Integer index, double dbValue) {
		mixer.setAttenuation(index, dbValue);
	}

	/**
	 * Set Mute to the output signal of the index input
	 * @param index
	 * 		Input index
	 * @param value
	 *     true|false
	 */
	public void setMute(Integer index, boolean value) {
		mixer.setMute(index, value);
	}
	
	/**
	 * Get the jsyn output port.
	 * @return UnitOutputPort
	 */
	public UnitOutputPort getOutputPort() {
		return mixer.getOutput();
	}
	
	/**
	 * Get the jsyn input port.
	 * @param index
	 * 		Input index
	 * @return
	 */
	public UnitInputPort getInputPort(Integer index) {
		return mixer.getInput(index);
	}
}
