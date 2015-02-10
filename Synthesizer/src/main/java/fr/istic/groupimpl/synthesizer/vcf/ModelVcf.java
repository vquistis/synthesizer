package fr.istic.groupimpl.synthesizer.vcf;

import java.util.Collection;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.vcf.jsyn.FilterFourPoles;

public class ModelVcf extends ModelComponent {
	
	private FilterFourPoles filter;
	
	public ModelVcf() {
		super();
		filter = new FilterFourPoles();
		filter.input.setName("vcf_input");
		filter.fm.setName("vcf_fm");
	}

	@Override
	public UnitGenerator getUnitGenerator() {
		return filter;
	}

	@Override
	public UnitInputPort getInputPort(String portName) {
		return (UnitInputPort) filter.getPortByName(portName);
	}

	@Override
	public UnitOutputPort getOutputPort(String portName) {
		return filter.getOutput();
	}

	@Override
	public Collection<UnitPort> getAllPorts() {
		return filter.getPorts();
	}
	
	public void setCutFrequency(double freq) {
		filter.frequency.set(freq);
	}
	
	public void setResonance(double res) {
		filter.Q.set(res);
	}

}
