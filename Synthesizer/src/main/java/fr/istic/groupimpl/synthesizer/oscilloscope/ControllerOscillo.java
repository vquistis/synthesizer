package fr.istic.groupimpl.synthesizer.oscilloscope;

import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;
import fr.istic.groupimpl.synthesizer.util.Oscilloscope;

/**
 * 
 * Controller of oscilloscope module
 * 
 * @author Team GroupImpl
 *
 */
public class ControllerOscillo  extends ControllerComponent
{
	
	private ModelOscillo model = new ModelOscillo(Oscilloscope.SIZE_BUFFER_READ);
	private Oscilloscope scope;
	
	/**
	 * Constructor
	 * @param scope
	 */
	public ControllerOscillo(Oscilloscope scope) {
		this.scope = scope;
		ControllerGlobal.getInstance().registerOutUnitGenerator(model.getUnitGenerator());
	}

	@Override
	public void handleViewClose() {
		scope.stop(); // pour arreter le thread
		ControllerGlobal.getInstance().removeAllConnections(model.getAllPorts());
		ControllerGlobal.getInstance().unregisterOutUnitGenerator(model.getUnitGenerator());		
	}
	
	/**
	 * Transmits the data from the model
	 * @return data of buffer
	 * 	
	 */
	public double [] getbufferData()
	{
		return model.getBuffer();
	}


	/**
	 * handle change value 
	 * @param newVal
	 * 		
	 */
	public void handleRefreshPeriodViewChange(Number newVal) {
		scope.setRefreshPeriod((Double)newVal);
	}

	@Override
	public ModelComponent getModel() {
		return model;
	}

}
