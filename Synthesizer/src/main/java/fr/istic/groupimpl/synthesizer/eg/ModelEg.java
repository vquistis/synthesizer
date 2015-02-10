package fr.istic.groupimpl.synthesizer.eg;

import java.util.Collection;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.eg.jsyn.JsynEnvelopeADSR;

public class ModelEg extends ModelComponent {
	
	private JsynEnvelopeADSR adsr;
	
	public ModelEg() {
		super();
		adsr = new JsynEnvelopeADSR();
	}

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

	public UnitOutputPort getOutputPort() {
		return adsr.output;
	}

	@Override
	public Collection<UnitPort> getAllPorts() {
		return adsr.getPorts();
	}
	
	/**
	 * @param attack Delay in seconds of attack
	 * Sets the attack time to the ADSR
	 */
	public void setAttack(double attack) {
		adsr.attack.set(attack);
	}
	
	/**
	 * @param decay Delay in seconds of the decay
	 * Sets THE decay time to the ADSR
	 */
	public void setDecay(double decay) {
		adsr.decay.set(decay);
	}
	
	/**
	 * @param sustainDb Value in dB of the sustain
	 * Sets the decibels sustain to the ADSR
	 */
	public void setSustain(double sustainDb) {
		adsr.sustain.set(Math.pow(2, sustainDb/6));
	}
	
	/**
	 * @param release Delay in seconds of the release
	 * Sets the release time to the ADSR
	 */
	public void setRelease(double release) {
		adsr.release.set(release);
	}

	@Override
	public String saveModule() {
		// TODO Auto-generated method stub
		return null;
	}

}
