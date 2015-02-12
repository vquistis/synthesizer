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
