package fr.istic.groupimpl.synthesizer.linein;

import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

/**
 * 
 * Controller of Line In module
 *  
 * @author Team GroupImpl
 *
 */
public class ControllerLineIn extends ControllerComponent {

	/** The model. */
	private ModelLineIn model = new ModelLineIn();
	
	/**
	 * Constructor.
	 */
	public ControllerLineIn() {
		ControllerGlobal.getInstance().registerUnitGenerator(model.getUnitGenerator());
	}

	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.ControllerComponent#handleViewClose()
	 */
	@Override
	public void handleViewClose() {
		ControllerGlobal.getInstance().removeAllConnections(model.getAllPorts());
		ControllerGlobal.getInstance().unregisterUnitGenerator(model.getUnitGenerator());
	}

	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.ControllerComponent#getModel()
	 */
	@Override
	public ModelComponent getModel() {
		return model;
	}
}
