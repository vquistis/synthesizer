package fr.istic.groupimpl.synthesizer.keyboard;

import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

/**
 * Controller of keyboard module.
 *
 * @author Team GroupImpl
 */
public class ControllerKeyboard extends ControllerComponent {

	/** The model. */
	private ModelKeyboard model = new ModelKeyboard();

	/**
	 * Constructor.
	 */
	public ControllerKeyboard() {
		ControllerGlobal.getInstance().registerUnitGenerator(
				model.getUnitGenerator());
	}

	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.ControllerComponent#handleViewClose()
	 */
	@Override
	public void handleViewClose() {
		ControllerGlobal.getInstance()
				.removeAllConnections(model.getAllPorts());
		ControllerGlobal.getInstance().unregisterUnitGenerator(
				model.getUnitGenerator());
	}

	/**
	 * Release of key.
	 */
	public void handleViewkeyReleaseEvent() {
		model.setPress(false);
	}

	/**
	 * handle key pressed.
	 *
	 * @param key the key
	 */
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

	/**
	 * @see fr.istic.groupimpl.synthesizer.component.ControllerComponent#getModel()
	 */
	@Override
	public ModelComponent getModel() {
		return model;
	}
}
