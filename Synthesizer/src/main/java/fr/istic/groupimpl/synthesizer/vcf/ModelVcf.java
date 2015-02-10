package fr.istic.groupimpl.synthesizer.vcf;

import java.util.Collection;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.vcf.jsyn.JsynVcfCircuit;

public class ModelVcf extends ModelComponent {
	
	private JsynVcfCircuit circuit;
	
	public ModelVcf() {
		super();
		circuit = new JsynVcfCircuit();
	}

	public void setCutFrequency(double freq) {
		circuit.setCutFrequency(freq);
	}
	
	public void setResonance(double res) {
		circuit.setResonance(res);
	}
	
	@Override
	public UnitGenerator getUnitGenerator() {
		return circuit;
	}

	@Override
	public UnitInputPort getInputPort(String portName) {
		return circuit.getInput();
	}
	
	public UnitInputPort getFmPort() {
		return circuit.getFm();
	}

	@Override
	public UnitOutputPort getOutputPort(String portName) {
		return circuit.getOutput();
	}

	@Override
	public Collection<UnitPort> getAllPorts() {
		return circuit.getPorts();
	}
}
