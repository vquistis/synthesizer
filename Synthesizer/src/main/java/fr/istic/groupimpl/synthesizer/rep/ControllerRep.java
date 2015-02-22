package fr.istic.groupimpl.synthesizer.rep;

import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

/**
 * 
 * Controller of REP module
 *  
 * @author Team GroupImpl
 */
public class ControllerRep extends ControllerComponent{
	
	private ModelRep modelRep;
	private ControllerGlobal ctrlGlob;

	/**
	 * Constructor
	 */
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
		return modelRep;
	}

}
