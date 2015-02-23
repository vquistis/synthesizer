package fr.istic.groupimpl.synthesizer.eg;

import java.util.Collection;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.eg.jsyn.JsynEnvelopeADSR;

/**
 * The Class ModelEg : model of EG component.
 *
 * @author Team groupImpl
 */
public class ModelEg extends ModelComponent {

	/** The adsr. */
	private JsynEnvelopeADSR adsr;

	/**
	 * Constructor.
	 */
	public ModelEg() {
		super();
		adsr = new JsynEnvelopeADSR();
		adsr.input.setName("eg_input");
		adsr.output.setName("eg_output");
	}

	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.IModelComponent#getUnitGenerator()
	 */
	@Override
	public UnitGenerator getUnitGenerator() {
		return adsr;
	}

	/**
	 * Get the jsyn input port.
	 * @return UnitInputPort
	 */
	public UnitInputPort getInputPort() {
		return adsr.input;
	}

	/**
	 * Gets the output port.
	 *
	 * @return the output port
	 */
	public UnitOutputPort getOutputPort() {
		return adsr.output;
	}

	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.IModelComponent#getAllPorts()
	 */
	@Override
	public Collection<UnitPort> getAllPorts() {
		return adsr.getPorts();
	}

	/**
	 * Sets the attack.
	 *
	 * @param attack Delay in seconds of attack
	 * Sets the attack time to the ADSR
	 */
	public void setAttack(double attack) {
		adsr.attack.set(attack);
	}

	/**
	 * Sets the decay.
	 *
	 * @param decay Delay in seconds of the decay
	 * Sets THE decay time to the ADSR
	 */
	public void setDecay(double decay) {
		adsr.decay.set(decay);
	}

	/**
	 * Sets the sustain.
	 *
	 * @param sustainDb Value in dB of the sustain
	 * Sets the decibels sustain to the ADSR
	 */
	public void setSustain(double sustainDb) {
		adsr.sustain.set(Math.pow(2, sustainDb/6));
	}

	/**
	 * Sets the release.
	 *
	 * @param release Delay in seconds of the release
	 * Sets the release time to the ADSR
	 */
	public void setRelease(double release) {
		adsr.release.set(release);
	}
}
