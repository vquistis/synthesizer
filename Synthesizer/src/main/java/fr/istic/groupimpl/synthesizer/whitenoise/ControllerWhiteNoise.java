package fr.istic.groupimpl.synthesizer.whitenoise;

import javafx.beans.property.DoubleProperty;
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

	private ModelWhiteNoise model = new ModelWhiteNoise();
	
	/**
	 * Constructor
	 */
	public ControllerWhiteNoise() {
		ControllerGlobal.getInstance().registerUnitGenerator(model.getUnitGenerator());
	}

	@Override
	public void handleViewClose() {
		ControllerGlobal.getInstance().removeAllConnections(model.getAllPorts());
		ControllerGlobal.getInstance().unregisterUnitGenerator(model.getUnitGenerator());
	}

	@Override
	public ModelComponent getModel() {
		// TODO Auto-generated method stub
		return model;
	}
}
