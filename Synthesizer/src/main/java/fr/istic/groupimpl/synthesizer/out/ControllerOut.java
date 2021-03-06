package fr.istic.groupimpl.synthesizer.out;

import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

// TODO: Auto-generated Javadoc
/**
 * 
 * Controller of out module
 *  
 * @author Team GroupImpl
 *
 */
public class ControllerOut extends ControllerComponent {

	/** The model. */
	private ModelOut model = new ModelOut();
	
	/**
	 * Constructor.
	 */
	public ControllerOut() {
		ControllerGlobal.getInstance().registerOutUnitGenerator(model.getUnitGenerator());
	}
	
	/**
	 * Change listener for sound attenuation (volume).
	 * @param newVal - new value of attenuation in db
	 */
	public void handleViewVolumeChange(Number newVal) {
		model.setAttenuation((double) newVal);
	}

	/**
	 * Change listener for mute choice.
	 *
	 * @param newVal - true for mute
	 */
	public void handleViewMuteChange(Boolean newVal) {
		if (newVal) {
			model.getUnitGenerator().stop();
		} else {
			model.getUnitGenerator().start();
		}
	}

	/**
	 * @see fr.istic.groupimpl.synthesizer.component.ControllerComponent#handleViewClose()
	 */
	public void handleViewClose() {
		ControllerGlobal.getInstance().removeAllConnections(model.getAllPorts());
		ControllerGlobal.getInstance().unregisterOutUnitGenerator(model.getUnitGenerator());
	}

	/**
	 * @see fr.istic.groupimpl.synthesizer.component.ControllerComponent#getModel()
	 */
	@Override
	public ModelComponent getModel() {
		return model;
	}
	
}
