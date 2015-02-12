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
	public void handleViewkeyEvent(String key) {
		switch (key) {
		case "S":
			model.setKey(1);
			break;
		case "E":
			model.setKey(2);
			break;
		case "D":
			model.setKey(3);
			break;
		case "R":
			model.setKey(4);
			break;
		case "F":
			model.setKey(5);
			break;
		case "G":
			model.setKey(6);
			break;
		case "Y":
			model.setKey(7);
			break;
		case "H":
			model.setKey(8);
			break;
		case "U":
			model.setKey(9);
			break;
		case "J":
			model.setKey(10);
			break;
		case "I":
			model.setKey(11);
			break;
		case "K":
			model.setKey(12);
			break;
		default:
			model.setKey(0);
			break;
		} 
	}
	
	@Override
	public ModelComponent getModel() {
		return model;
	}
}
