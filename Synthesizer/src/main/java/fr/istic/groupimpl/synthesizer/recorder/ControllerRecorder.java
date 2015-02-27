package fr.istic.groupimpl.synthesizer.recorder;

import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

/**
 * 
 * Controller of Recorder module
 *  
 * @author Team GroupImpl
 *
 */
public class ControllerRecorder extends ControllerComponent {

	/** The model. */
	private ModelRecorder model = new ModelRecorder();
	
	/**
	 * Constructor.
	 *
	 * @param viewRecorder the view recorder
	 */
	public ControllerRecorder(ViewRecorder viewRecorder) {
		ControllerGlobal.getInstance().registerUnitGenerator(model.getUnitGenerator());
		viewRecorder.getFxSampleName().textProperty().bind(model.getSampleFileName());
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
	 * @see fr.istic.groupimpl.synthesizer.component.ControllerComponent#getModel()
	 */
	@Override
	public ModelComponent getModel() {
		return model;
	}
	
	/**
	 * Handle view start clicked.
	 */
	public void handleViewStartClicked() {
		model.start();
	}

	/**
	 * Handle view stop clicked.
	 */
	public void handleViewStopClicked() {
		model.stop();
	}
	
	/**
	 * Gets the sample file name.
	 *
	 * @return the sample file name
	 */
	public String getSampleFileName(){
		return model.getSampleFileName().get();
	}
	
	/**
	 * Prepare file.
	 *
	 * @param absolutePath the absolute path
	 */
	public void prepareFile(String absolutePath){
		model.prepareFile(absolutePath);
	}
}
