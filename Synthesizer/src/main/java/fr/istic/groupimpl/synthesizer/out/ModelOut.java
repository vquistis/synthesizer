package fr.istic.groupimpl.synthesizer.out;

import java.util.Collection;
import java.util.Map;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.io.architecture.Module;
import fr.istic.groupimpl.synthesizer.out.jsyn.JsynOutCircuit;

public class ModelOut extends ModelComponent {

	private JsynOutCircuit out;
	
	public ModelOut() {
		super();

		out = new JsynOutCircuit();
		setAttenuation(0); // Default value
	}

	/**
	 * Set an attenuation to the output signal
	 * @param value - attenuation in db
	 */
	public void setAttenuation(double dbValue) {
		out.setAttenuation(dbValue);
	}

	@Override
	public UnitGenerator getUnitGenerator() {
		return out;
	}

	/**
	 * Get the jsyn input port.
	 * @return UnitInputPort
	 */
	public UnitInputPort getInputPort() {
		return out.getInput();
	}

	@Override
	public Collection<UnitPort> getAllPorts() {
		return out.getPorts();
	}

	@Override
	public Module getModule() {
		Module module= new Module();
		Map<String, String>parameters = module.getParameters();
		parameters.put("attenuation", String.valueOf(out.getFrameRate()));		
		return module;
	}

}
