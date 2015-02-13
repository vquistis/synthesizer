package fr.istic.groupimpl.synthesizer.seq;

import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;
import fr.istic.groupimpl.synthesizer.logger.Log;

public class ControllerSeq  extends ControllerComponent
{
	static final int NB_BUTTONS=8;
	
	private final ModelSeq model = new ModelSeq(NB_BUTTONS);
	
	public ControllerSeq() {	
		
		ControllerGlobal.getInstance().registerUnitGenerator(model.getUnitGenerator());
	}

	@Override
	public void handleViewClose() {
		ControllerGlobal.getInstance().removeAllConnections(model.getAllPorts());
		ControllerGlobal.getInstance().unregisterUnitGenerator(model.getUnitGenerator());		
	}
	

	/**
	 *  * Methode appelée sur un changement de valeur d'un potentiometre
	 * @param indice
	 * 		indice de la valeur
	 * @param newVal
	 * 		nouvelle valeur
	 */
	public void handleValueViewChange(int indice, Number newVal) {
		Log.getInstance().trace("indice="+indice+" newVal="+newVal);
		model.setValue(indice, (Double)newVal);
	}
	
	/**
	 * this method is called when a mouse is clicked on Begin button 
	 */
	public void handleViewBeginClicked()
	{
		model.initBegin();
	}

	@Override
	public ModelComponent getModel() {
		// TODO Auto-generated method stub
		return model;
	}

}
