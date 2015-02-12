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
		System.out.println("key" + key);
		switch (key) {
		case "A":
			model.setKey(1);
			break;
		case "Z":
			model.setKey(2);
			break;
		case "E":
			model.setKey(3);
			break;
		case "R":
			model.setKey(4);
			break;
		case "T":
			model.setKey(5);
			break;
		case "Y":
			model.setKey(6);
			break;
		case "U":
			model.setKey(7);
			break;
		case "I":
			model.setKey(8);
			break;
		case "O":
			model.setKey(9);
			break;
		case "P":
			model.setKey(10);
			break;
		case "Q":
			model.setKey(11);
			break;
		case "S":
			model.setKey(12);
			break;
			
		default:
			break;
		} 
	}
	
	@Override
	public ModelComponent getModel() {
		return model;
	}
}
