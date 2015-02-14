package fr.istic.groupimpl.synthesizer.player;

import java.io.File;

import javafx.stage.FileChooser;
import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

/**
 * 
 * Controller of Reader module
 *  
 * @author Team GroupImpl
 *
 */
public class ControllerPlayer extends ControllerComponent {

	private ModelPlayer model = new ModelPlayer();
	/**
	 * Constructor
	 */
	public ControllerPlayer(ViewPlayer viewPlayer) {
		ControllerGlobal.getInstance().registerUnitGenerator(model.getUnitGenerator());
		viewPlayer.getFxSampleName().textProperty().bind(model.getSampleFileName());
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
	
	public void handleViewPlayClicked() {
		model.play();
	}

	public void handleViewStopClicked() {
		model.stop();
	}
	
	public void loadSample(){
		 FileChooser fileChooser = new FileChooser();         
	     //Set extension filter
	     FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("music wave file (*.wav)", "*.wav");
	     fileChooser.getExtensionFilters().add(extFilter);
	     
	     //Show save file dialog
	     File file = fileChooser.showOpenDialog(null);
	     if(file != null){
	    	 model.loadSample(file.getAbsolutePath());	 
	     }
	}
}
