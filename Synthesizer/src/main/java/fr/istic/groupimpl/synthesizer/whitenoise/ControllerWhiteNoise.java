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
	
	/**
	 * Click listener for output port
	 * @param xCoord Coordinate of the clicked output port (x axis)
	 * @param yCoord Coordinate of the clicked output port (y axis)
	 */
	public void handleViewOutputClick(DoubleProperty xCoord, DoubleProperty yCoord) {
		ControllerGlobal.getInstance().handleOutputClicked(model.getOutputPort(), xCoord, yCoord);
	}

	@Override
	public void handleViewClose() {
		ControllerGlobal.getInstance().removeAllConnections(model.getAllPorts());
		ControllerGlobal.getInstance().unregisterUnitGenerator(model.getUnitGenerator());
<<<<<<< HEAD
	}

	@Override
	public ModelComponent getModel() {
		// TODO Auto-generated method stub
		return model;
=======
>>>>>>> branch 'master' of https://kheireddinebourahli@bitbucket.org/groupimpl/projectsynthesizer.git
	}
}
