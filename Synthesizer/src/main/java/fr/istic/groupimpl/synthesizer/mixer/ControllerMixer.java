package fr.istic.groupimpl.synthesizer.mixer;

import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

/**
 * Controller of mixer module.
 *
 * @author Team GroupImpl
 */
public class ControllerMixer extends ControllerComponent {
	
	/** The model. */
	private ModelMixer model;
	
	/**
	 * Constructor.
	 *
	 * @param view the view
	 * @param NumberOfInputPort the number of input port
	 */
	
	public ControllerMixer(ViewMixer view, Integer NumberOfInputPort) {
		model = new ModelMixer(NumberOfInputPort);
		ControllerGlobal.getInstance().registerUnitGenerator(model.getUnitGenerator());
		
		model.setCommandProperty("OutputGaugeBar", () -> {
			view.getOutputGauge().setProgress((double) model.getValProperty("OutputGaugeBar"));
		});		
		model.start();
	}
	
	/**
	 * Get the number of input port.
	 *
	 * @return Integer
	 */
	public Integer getNumberOfInputPort() {
		return model.getNumberOfInputPort();
	}
	
	/**
	 * Change listener for sound attenuation (volume).
	 * @param index
	 * 		index input port
	 * @param newVal - new value of attenuation in db
	 */
	public void handleViewVolumeChange(Integer index, Number newVal) {
		model.setAttenuation(index, (double) newVal);
	}

	/**
	 * Change listener for mute choice.
	 *
	 * @param index 		index input port
	 * @param newVal - true for mute
	 */
	public void handleViewMuteChange(Integer index, Boolean newVal) {
		model.setMute(index, newVal);
	}
	
	/**
	 * @see fr.istic.groupimpl.synthesizer.component.ControllerComponent#handleViewClose()
	 */
	@Override
	public void handleViewClose() {
		model.stop();
		ControllerGlobal.getInstance().removeAllConnections(model.getAllPorts());
		ControllerGlobal.getInstance().unregisterOutUnitGenerator(model.getUnitGenerator());
	}
	
	/**
	 * @see fr.istic.groupimpl.synthesizer.component.ControllerComponent#getModel()
	 */
	@Override
	public ModelComponent getModel() {
		return model;
	}
}
