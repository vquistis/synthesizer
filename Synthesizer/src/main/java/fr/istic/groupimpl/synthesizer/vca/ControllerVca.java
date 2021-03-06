package fr.istic.groupimpl.synthesizer.vca;

import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

/**
 * Controller of Vca module
 * 
 * @author Team GroupImpl
 * 
 */
public class ControllerVca extends ControllerComponent {
	
	/** The model vca. */
	private ModelVca modelVca;
	
	/** The ctrl glob. */
	private ControllerGlobal ctrlGlob;
	
	/**
	 * Instantiates a new controller vca.
	 */
	public ControllerVca() {
		modelVca = new ModelVca();
		ctrlGlob = ControllerGlobal.getInstance();
		ctrlGlob.registerUnitGenerator(modelVca.getUnitGenerator());
	}

	/**
	 * handle view close
	 */
	@Override
	public void handleViewClose() {
		ctrlGlob.removeAllConnections(modelVca.getAllPorts());
		ctrlGlob.unregisterUnitGenerator(modelVca.getUnitGenerator());
	}

	/**
	 * Handle view volt amplitude change.
	 *
	 * @param volt the volt
	 */
	public void handleViewVoltChange(double volt) {
		modelVca.setVolt(volt);
	}


	@Override
	public ModelComponent getModel() {
		return modelVca;
	}
}
