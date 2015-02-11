package fr.istic.groupimpl.synthesizer.rep;

import javafx.beans.property.DoubleProperty;
import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

public class ControllerRep extends ControllerComponent{
	
	private ModelRep modelRep;
	private ControllerGlobal ctrlGlob;

	public ControllerRep(){
		modelRep = new ModelRep();
		ctrlGlob = ControllerGlobal.getInstance();
		ctrlGlob.registerUnitGenerator(modelRep.getUnitGenerator());
	}

	/**
	 * Click listener for input port
	 * @param xCoord Coordinate of the clicked input port (x axis)
	 * @param yCoord Coordinate of the clicked input port (y axis)
	 */
	public void handleViewInputClick(DoubleProperty xCoord, DoubleProperty yCoord) {
		ctrlGlob.handleInputClicked(modelRep.getInputPort(), xCoord, yCoord);		
	}

	/**
	 * Click listener for output ports
	 * @param portName Name of the clicked output port
	 * @param xCoord Coordinate of the clicked output port (x axis)
	 * @param yCoord Coordinate of the clicked output port (y axis)
	 */
	public void handleViewOutputClick(String portName, DoubleProperty xCoord, DoubleProperty yCoord) {
		ctrlGlob.handleOutputClicked(modelRep.getOutputPort(portName), xCoord, yCoord);		
	}

	@Override
	public void handleViewClose() {
		ctrlGlob.removeAllConnections(modelRep.getAllPorts());
		ctrlGlob.unregisterUnitGenerator(modelRep.getUnitGenerator());		
	}

	@Override
	public ModelComponent getModel() {
		// TODO Auto-generated method stub
		return modelRep;
	}

}
