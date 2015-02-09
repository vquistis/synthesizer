package fr.istic.groupimpl.synthesizer.rep;

import javafx.beans.property.DoubleProperty;
import fr.istic.groupimpl.synthesizer.component.IControllerComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

public class ControllerRep implements IControllerComponent{
	
	private ModelRep modelRep;
	private ControllerGlobal ctrlGlob;

	public ControllerRep(){
		modelRep = new ModelRep();
		ctrlGlob = ControllerGlobal.getInstance();
		ctrlGlob.registerOutUnitGenerator(modelRep.getUnitGenerator());
	}
	
	@Override
	public void handleViewInputClick(String portName, DoubleProperty xCoord,
			DoubleProperty yCoord) {
		ctrlGlob.handleInputClicked(modelRep.getInputPort(portName), xCoord, yCoord);		
	}

	@Override
	public void handleViewOutputClick(String portName, DoubleProperty xCoord,
			DoubleProperty yCoord) {
		ctrlGlob.handleOutputClicked(modelRep.getOutputPort(portName), xCoord, yCoord);		
	}

	@Override
	public void handleViewClose() {
		ctrlGlob.removeAllConnections(modelRep.getAllPorts());
		ctrlGlob.unregisterUnitGenerator(modelRep.getUnitGenerator());		
	}

}
