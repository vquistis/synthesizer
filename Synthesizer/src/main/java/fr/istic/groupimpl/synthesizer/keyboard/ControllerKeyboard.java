package fr.istic.groupimpl.synthesizer.keyboard;

import javafx.beans.property.DoubleProperty;
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
	}

	@Override
	public ModelComponent getModel() {
		return model;
	}
}
