package fr.istic.groupimpl.synthesizer.whitenoise;

import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

/**
 * 
 * Controller of White Noise module
 *  
 * @author Team GroupImpl
 *
 */
public class ControllerWhiteNoise extends ControllerComponent {

	/** The model. */
	private ModelWhiteNoise model = new ModelWhiteNoise();
	
	/**
	 * Constructor.
	 */
	public ControllerWhiteNoise() {
		ControllerGlobal.getInstance().registerUnitGenerator(model.getUnitGenerator());
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
