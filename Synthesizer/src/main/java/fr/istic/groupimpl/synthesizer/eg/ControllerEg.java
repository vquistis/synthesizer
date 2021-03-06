package fr.istic.groupimpl.synthesizer.eg;

import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

// TODO: Auto-generated Javadoc
/**
 * The Class ControllerEg : controller of EG component.
 *
 * @author Team groupImpl
 */
public class ControllerEg extends ControllerComponent {

	/** The model. */
	private ModelEg model = new ModelEg();
	
	/**
	 * Constructor.
	 */
	public ControllerEg() {
		ControllerGlobal.getInstance().registerUnitGenerator(model.getUnitGenerator());
	}
	
	/**
	 * Change listener attack.
	 * @param newVal - new value of attack in sec
	 */
	public void handleViewAttackChange(Number newVal) {
		model.setAttack((double) newVal);
	}
	
	/**
	 * Change listener decay.
	 * @param newVal - new value of decay in sec
	 */
	public void handleViewDecayChange(Number newVal) {
		model.setDecay((double) newVal);
	}
	
	/**
	 * Change listener sustain.
	 * @param newVal - new value of sustain in volt
	 */
	public void handleViewSustainChange(Number newVal) {
		model.setSustain((double) newVal);
	}
	
	/**
	 * Change listener release.
	 * @param newVal - new value of release in sec
	 */
	public void handleViewReleaseChange(Number newVal) {
		model.setRelease((double) newVal);
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
