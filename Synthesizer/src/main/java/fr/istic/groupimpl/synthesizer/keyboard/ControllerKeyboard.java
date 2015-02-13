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
		ControllerGlobal.getInstance().registerUnitGenerator(
				model.getUnitGenerator());
	}

	@Override
	public void handleViewClose() {
		ControllerGlobal.getInstance()
				.removeAllConnections(model.getAllPorts());
		ControllerGlobal.getInstance().unregisterUnitGenerator(
				model.getUnitGenerator());
	}

	public void handleViewkeyReleaseEvent() {
		model.setPress(false);
	}

	public void handleViewkeyEvent(String key) {

		String str = "QZSEDFTGYHUJK";

		int indKey = str.indexOf(key);
		if (indKey >= 0) {
			model.setKey(indKey);
			model.setPress(true);
		} else {
			if (key.equals("X")) {
				model.incOctave();
			}
			if (key.equals("W")) {
				model.decOctave();
			}
		}
	}

	@Override
	public ModelComponent getModel() {
		return model;
	}
}
