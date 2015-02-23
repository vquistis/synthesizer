package fr.istic.groupimpl.synthesizer.vcf;

import java.util.Collection;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.vcf.jsyn.JsynVcfCircuit;

/**
 * Model of vcf module.
 *
 * @author Team GroupImpl
 */
public class ModelVcf extends ModelComponent {
	
	/** The circuit. */
	private JsynVcfCircuit circuit;
	
	/**
	 * The Enum Type.
	 */
	public static enum Type { 
 /** The L p24. */
 LP24, 
 /** The H p12. */
 HP12 }
	
	/**
	 * Constructor.
	 *
	 * @param t : type
	 */
	public ModelVcf(Type t) {
		super();
		circuit = new JsynVcfCircuit(t);
	}

	/**
	 * Sets the cut frequency.
	 *
	 * @param freq the new cut frequency
	 */
	public void setCutFrequency(double freq) {
		circuit.setCutFrequency(freq);
	}
	
	/**
	 * Sets the resonance.
	 *
	 * @param res the new resonance
	 */
	public void setResonance(double res) {
		circuit.setResonance(res);
	}
	
	/**
	 * @see fr.istic.groupimpl.synthesizer.component.IModelComponent#getUnitGenerator()
	 */
	@Override
	public UnitGenerator getUnitGenerator() {
		return circuit;
	}

	/**
	 * Get the jsyn input port.
	 * @return UnitInputPort
	 */
	public UnitInputPort getInputPort() {
		return circuit.getInput();
	}

	/**
	 * Get the jsyn input fm port.
	 * @return UnitInputPort
	 */
	public UnitInputPort getFmPort() {
		return circuit.getFm();
	}

	/**
	 * Get the jsyn output port.
	 * @return UnitOutputPort
	 */
	public UnitOutputPort getOutputPort() {
		return circuit.getOutput();
	}

	/**
	 * @see fr.istic.groupimpl.synthesizer.component.IModelComponent#getAllPorts()
	 */
	@Override
	public Collection<UnitPort> getAllPorts() {
		return circuit.getPorts();
	}

}
