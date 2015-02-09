package fr.istic.groupimpl.synthesizer.vca;

import java.util.Collection;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.vca.jsyn.VCAJSyn;

/**
 * The Class ModelVca.
 */
public class ModelVca extends ModelComponent {

	private VCAJSyn vcajSyn;

	/**
	 * Instantiates a new model vca.
	 */
	public ModelVca() {
		super();
		vcajSyn = new VCAJSyn();
	}

	@Override
	public UnitGenerator getUnitGenerator() {
		return this.vcajSyn;
	}

	/**
	 * Sets the a0.
	 *
	 * @param a0
	 */
	protected void seta0(double a0) {
		vcajSyn.getInputa0().set(a0);
	}

	@Override
	public UnitInputPort getInputPort(String portName) {
		switch (portName) {
		case "vca_input":
			return vcajSyn.getInput();
		case "vca_inputam":
			return vcajSyn.getInputam();
		case "vca_inputa0":
			return vcajSyn.getInputa0();
		default:
			return null;
		}
	}

	@Override
	public UnitOutputPort getOutputPort(String portName) {
		if (portName.equals("vca_output")) {
			return vcajSyn.getOutput();
		}
		return null;
	}

	@Override
	public Collection<UnitPort> getAllPorts() {
		return this.vcajSyn.getPorts();
	}

}
