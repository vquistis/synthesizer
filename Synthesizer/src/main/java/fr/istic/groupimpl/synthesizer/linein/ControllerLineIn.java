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

	private ModelLineIn model = new ModelLineIn();
	
	/**
	 * Constructor
	 */
	public ControllerLineIn() {
		ControllerGlobal.getInstance().registerUnitGenerator(model.getUnitGenerator());
	}

	@Override
	public void handleViewClose() {
		ControllerGlobal.getInstance().removeAllConnections(model.getAllPorts());
		ControllerGlobal.getInstance().unregisterUnitGenerator(model.getUnitGenerator());
	}

	@Override
	public ModelComponent getModel() {
		return model;
	}
}
