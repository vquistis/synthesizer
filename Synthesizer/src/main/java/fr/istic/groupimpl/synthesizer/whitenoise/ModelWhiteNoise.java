package fr.istic.groupimpl.synthesizer.whitenoise;

import java.util.Collection;

import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;
import com.jsyn.unitgen.WhiteNoise;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;

/**
 * Model of White Noise module.
 *
 * @author Team GroupImpl
 */
public class ModelWhiteNoise extends ModelComponent {

	/** The white noise. */
	private WhiteNoise whiteNoise;
	
	/**
	 * Constructor.
	 */
	public ModelWhiteNoise() {
		super();
		whiteNoise = new WhiteNoise();
		whiteNoise.output.setName("noise_output");
	}
	
	/**
	 * @see fr.istic.groupimpl.synthesizer.component.IModelComponent#getUnitGenerator()
	 */
	@Override
	public UnitGenerator getUnitGenerator() {
		return whiteNoise;
	}

	/**
	 * Get the jsyn output port.
	 * @return UnitInputPort
	 */
	public UnitOutputPort getOutputPort() {
		return whiteNoise.getOutput();
	}

	/**
	 * @see fr.istic.groupimpl.synthesizer.component.IModelComponent#getAllPorts()
	 */
	@Override
	public Collection<UnitPort> getAllPorts() {
		return whiteNoise.getPorts();
	}

}
