package fr.istic.groupimpl.synthesizer.vca;

import java.util.Collection;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.vca.jsyn.JSynVCA;

/**
 * The Class Model Vca.
 * 
 * @author Team GroupImpl
 * 
 */
public class ModelVca extends ModelComponent {

	/** The vca Jsyn. */
	private JSynVCA vcajSyn;

	/**
	 * Instantiates a new model vca.
	 */
	public ModelVca() {
		super();
		vcajSyn = new JSynVCA();
	}

	/**
	 * get VCA jsyn.
	 *
	 * @return the unit generator
	 */
	@Override
	public UnitGenerator getUnitGenerator() {
		return this.vcajSyn;
	}

	/**
	 * get input port.
	 *
	 * @param portName the port name
	 * @return the input port
	 */
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

	/**
	 * Get the jsyn output port.
	 * @return UnitOutputPort
	 */
	public UnitOutputPort getOutputPort() {
		return vcajSyn.getOutput();
	}

	/**
	 * get all port
	 */
	@Override
	public Collection<UnitPort> getAllPorts() {
		return this.vcajSyn.getPorts();
	}
	
	/**
	 * Sets the volt.
	 *
	 * @param volt the new volt
	 */
	protected void setVolt(double volt) {
		vcajSyn.getInputa0().set(volt);
	}

}
