package fr.istic.groupimpl.synthesizer.recorder;

import java.io.File;

import javafx.stage.FileChooser;
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

	private ModelRecorder model = new ModelRecorder();
	/**
	 * Constructor
	 */
	public ControllerRecorder(ViewRecorder viewRecorder) {
		ControllerGlobal.getInstance().registerUnitGenerator(model.getUnitGenerator());
		viewRecorder.getFxSampleName().textProperty().bind(model.getSampleFileName());
	}

	@Override
	public void handleViewClose() {
		ControllerGlobal.getInstance().removeAllConnections(model.getAllPorts());
		ControllerGlobal.getInstance().unregisterUnitGenerator(model.getUnitGenerator());
	}

	@Override
	public ModelComponent getModel() {
		return model;
	}
	
	public void handleViewStartClicked() {
		model.start();
	}

	public void handleViewStopClicked() {
		model.stop();
	}
	
	public void prepare(){
		 FileChooser fileChooser = new FileChooser();         
	     //Set extension filter
	     FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("music wave file (*.wav)", "*.wav");
	     fileChooser.getExtensionFilters().add(extFilter);
	     fileChooser.setInitialFileName(model.getSampleFileName().get());
	     
	     //Show save file dialog
	     File file = fileChooser.showSaveDialog(null);
	     if(file != null){
	    	 model.prepareFile(file.getAbsolutePath());	 
	     }
	}
}
