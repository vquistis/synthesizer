package fr.istic.groupimpl.synthesizer.seq;

import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;
import fr.istic.groupimpl.synthesizer.logger.Log;

/**
 * Controller of Seq module.
 *
 * @author Team GroupImpl
 */
public class ControllerSeq  extends ControllerComponent
{
	
	/** The Constant NB_BUTTONS. */
	static final int NB_BUTTONS=8;
	
	/** The model. */
	private final ModelSeq model = new ModelSeq(NB_BUTTONS);
	
	/**
	 * Constructor.
	 */
	public ControllerSeq() {	
		
		ControllerGlobal.getInstance().registerUnitGenerator(model.getUnitGenerator());
	}

	/**
	 * @see fr.istic.groupimpl.synthesizer.component.ControllerComponent#handleViewClose()
	 */
	@Override
	public void handleViewClose() {
		ControllerGlobal.getInstance().removeAllConnections(model.getAllPorts());
		ControllerGlobal.getInstance().unregisterUnitGenerator(model.getUnitGenerator());		
	}
	

	/**
	 *  this method is called when a value of a potentiometer changes.
	 *
	 * @param indice 		index of data
	 * @param newVal 		new data
	 */
	public void handleValueViewChange(int indice, Number newVal) {
		Log.getInstance().trace("indice="+indice+" newVal="+newVal);
		model.setValue(indice, (Double)newVal);
	}
	
	/**
	 * this method is called when a mouse is clicked on Begin button.
	 */
	public void handleViewBeginClicked()
	{
		model.initBegin();
	}

	/**
	 * @see fr.istic.groupimpl.synthesizer.component.ControllerComponent#getModel()
	 */
	@Override
	public ModelComponent getModel() {
		return model;
	}

}
