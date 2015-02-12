package fr.istic.groupimpl.synthesizer.vca;

import java.util.Collection;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.logger.Log;
import fr.istic.groupimpl.synthesizer.vca.jsyn.JSynVCA;

/**
 * The Class Model Vca.
 */
public class ModelVca extends ModelComponent {

	/** The vcaj syn. */
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
	 * Sets the a0.
	 *
	 * @param a0 the new a0
	 */
	protected void seta0(double a0) {
		vcajSyn.getInputa0().set(a0);
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
			Log.getInstance().debug("!!!!!!");
			return vcajSyn.getInput();
		case "vca_inputam":
			Log.getInstance().debug("????");
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
