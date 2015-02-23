package fr.istic.groupimpl.synthesizer.echo;

import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;
import fr.istic.groupimpl.synthesizer.logger.Log;

/**
 * 
 * Controller of Seq module
 * 
 * @author Team GroupImpl
 *
 */
public class ControllerEcho  extends ControllerComponent
{
	
	private final double maxPeriod=10.;
	
	double getMaxPeriod()
	{
		return maxPeriod;
	}
	
	private final ModelEcho model = new ModelEcho(maxPeriod);
	
	/**
	 * Constructor
	 */
	public ControllerEcho() {	
		
		ControllerGlobal.getInstance().registerUnitGenerator(model.getUnitGenerator());
	}

	@Override
	public void handleViewClose() {
		ControllerGlobal.getInstance().removeAllConnections(model.getAllPorts());
		ControllerGlobal.getInstance().unregisterUnitGenerator(model.getUnitGenerator());		
	}
	

	/**
	 *  this method is called when period value changes
	 * @param newVal
	 * 		new data
	 */
	public void handlePeriodViewChange( Number newVal) {
		Log.getInstance().trace("handlePeriodViewChange newVal="+newVal);
		model.setPeriodValue((Double)newVal);
	}
	
	/**
	 *  this method is called when attenuation value changes
	 * @param newVal
	 * 		new data
	 */
	public void handleAttenuationViewChange( Number newVal) {
		Log.getInstance().trace("handleAttenuationViewChange newVal="+newVal);
		model.setAttenuationValue((Double)newVal);
	}
	

	@Override
	public ModelComponent getModel() {
		return model;
	}

}
