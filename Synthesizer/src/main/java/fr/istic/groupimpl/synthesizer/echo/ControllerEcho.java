package fr.istic.groupimpl.synthesizer.echo;

import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;
import fr.istic.groupimpl.synthesizer.logger.Log;

/**
 * 
 * Controller of Echo module
 * 
 * @author Team GroupImpl
 *
 */
public class ControllerEcho  extends ControllerComponent
{
	
	private final double maxPeriod=10.;
	
	/**
	 * getter for the maximum period
	 * @return the maximum period
	 */
	public double getMaxPeriod()
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
	 * 		new period in seconds
	 */
	public void handlePeriodViewChange( Number newVal) {
		Log.getInstance().trace("handlePeriodViewChange newVal="+newVal);
		model.setPeriodValue((Double)newVal);
	}
	
	/**
	 *  this method is called when attenuation value changes
	 * @param newVal
	 * 		new value in decibel
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
