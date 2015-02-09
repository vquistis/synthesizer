package fr.istic.groupimpl.synthesizer.eg;

import java.util.Collection;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.EnvelopeDAHDSR;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;

public class ModelEg extends ModelComponent {
	
	private EnvelopeDAHDSR adsr;
	
	public ModelEg() {
		super();
		adsr = new EnvelopeDAHDSR();
		adsr.delay.set(0.0);
		adsr.hold.set(0.0);
	}

	@Override
	public UnitGenerator getUnitGenerator() {
		return adsr;
	}

	@Override
	public UnitInputPort getInputPort(String portName) {
		return adsr.input;
	}

	@Override
	public UnitOutputPort getOutputPort(String portName) {
		return adsr.output;
	}

	@Override
	public Collection<UnitPort> getAllPorts() {
		return adsr.getPorts();
	}
	
	public void setAttack(double attack) {
		adsr.attack.set(attack);
	}
	
	public void setDecay(double decay) {
		adsr.decay.set(decay);
	}
	
	public void setSustain(double sustainDb) {
		adsr.sustain.set(Math.pow(2, sustainDb/6));
	}
	
	public void setRelease(double release) {
		adsr.release.set(release);
	}

}
