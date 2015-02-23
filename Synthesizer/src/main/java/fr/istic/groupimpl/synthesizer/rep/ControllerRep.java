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
	
	/** The model rep. */
	private ModelRep modelRep;
	
	/** The ctrl glob. */
	private ControllerGlobal ctrlGlob;

	/**
	 * Constructor.
	 */
	public ControllerRep(){
		modelRep = new ModelRep();
		ctrlGlob = ControllerGlobal.getInstance();
		ctrlGlob.registerUnitGenerator(modelRep.getUnitGenerator());
	}

	/**
	 * @see fr.istic.groupimpl.synthesizer.component.ControllerComponent#handleViewClose()
	 */
	@Override
	public void handleViewClose() {
		ctrlGlob.removeAllConnections(modelRep.getAllPorts());
		ctrlGlob.unregisterUnitGenerator(modelRep.getUnitGenerator());		
	}

	/**
	 * @see fr.istic.groupimpl.synthesizer.component.ControllerComponent#getModel()
	 */
	@Override
	public ModelComponent getModel() {
		return modelRep;
	}

}
