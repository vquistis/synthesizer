package fr.istic.groupimpl.synthesizer.out;

import java.util.Collection;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;

public class ModelOut extends ModelComponent {

	private JsynAttenuationOut out;
	
	public ModelOut() {
		super();

		out = new JsynAttenuationOut();
		setAttenuation(0); // Default value
	}

	/**
	 * Set an attenuation to the output signal
	 * @param value - attenuation in db
	 */
	public void setAttenuation(double dbValue) {
		double coef = Math.pow(2, dbValue/6);
		out.set(coef);
	}

	/**
	 * Start sound emission
	 */
	public void start() {
		out.start();
	}
	
	/**
	 * Stop sound emission
	 */
	public void stop() {
		out.stop();
	}

	@Override
	public UnitGenerator getUnitGenerator() {
		return out;
	}
	
	@Override
	public UnitInputPort getInputPort(String portName) {
		return out.input;
	}

	@Override
	public UnitOutputPort getOutputPort(String portName) {
		//This module doesn't have output port
		return null;
	}

	@Override
	public Collection<UnitPort> getAllPorts() {
		return out.getPorts();
	}
}
