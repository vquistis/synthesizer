package fr.istic.groupimpl.synthesizer.vcf;

import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

/**
 * Controller of vcf module.
 *
 * @author Team GroupImpl
 */
public class ControllerVcf extends ControllerComponent {

	/** The model. */
	private ModelVcf model;
	
	/**
	 * Constructor.
	 *
	 * @param t the t
	 */
	public ControllerVcf(ModelVcf.Type t) {
		model = new ModelVcf(t);
		ControllerGlobal.getInstance().registerUnitGenerator(model.getUnitGenerator());
	}
	
	/**
	 * Change listener frequency cutoff.
	 * @param newVal - new frequency value
	 */
	public void handleViewCutoffChange(Number newVal) {
		model.setCutFrequency((double) newVal);
	}
	
	/**
	 * Change listener resonance.
	 * @param newVal - new resonance value
	 */
	public void handleViewResonanceChange(Number newVal) {
		model.setResonance((double) newVal);
	}

	/**
	 * @see fr.istic.groupimpl.synthesizer.component.ControllerComponent#handleViewClose()
	 */
	@Override
	public void handleViewClose() {
		ControllerGlobal.getInstance().removeAllConnections(model.getAllPorts());
		ControllerGlobal.getInstance().unregisterUnitGenerator(model.getUnitGenerator());
	}

	/**
	 * @see fr.istic.groupimpl.synthesizer.component.ControllerComponent#getModel()
	 */
	@Override
	public ModelComponent getModel() {
		return model;
	}
}
