package fr.istic.groupimpl.synthesizer.keyboard;

import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

/**
 * 
 * Controller of keyboard module
 *  
 * @author Team GroupImpl
 *
 */
public class ControllerKeyboard extends ControllerComponent {

	private ModelKeyboard model = new ModelKeyboard();
	
	/**
	 * Constructor
	 */
	public ControllerKeyboard() {
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
