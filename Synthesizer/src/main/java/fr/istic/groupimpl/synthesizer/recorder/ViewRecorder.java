package fr.istic.groupimpl.synthesizer.recorder;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ViewComponent;

/**
 * 
 * View of recorder module
 *  
 * @author Team GroupImpl
 */
public class ViewRecorder extends ViewComponent implements Initializable {

	@FXML private Pane rootModulePane;
	@FXML private GridPane top;
	@FXML private ImageView input;
	@FXML private Label fxSampleName;
	@FXML private Button fxBtnPrepare;
	@FXML private Button fxBtnStart;
	@FXML private Button fxBtnStop;

	private ControllerRecorder controller;

	@Override
	public void initialize(URL location, ResourceBundle resource) {
		((Label) top.lookup("#titleModule")).setText("Recorder");
		
		// implementation of controller
		controller = new ControllerRecorder(this);
		
		addPort("player_input", input);		
		
		// Listener close module
		top.lookup("#closeModuleFx").addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			cleanup();			
			controller.handleViewClose();
			Pane parent = (Pane) rootModulePane.getParent();
			parent.getChildren().remove(rootModulePane);
		});
		
		fxBtnPrepare.setDisable(false);
   	 	fxBtnStart.setDisable(true);
   	 	fxBtnStop.setDisable(true);
	}

	@Override
	protected Pane getComponentRoot() {
		return rootModulePane;
	}

	@Override
	protected ControllerComponent getController() {
		return controller;
	}

	@Override
	public String getFilename() {
		return "fxml/recorder.fxml";
	}
	
	/**
	 * Handles the click on the start button
	 */
	@FXML
	public void handleStartClicked() {
		fxBtnPrepare.setDisable(true);
   	 	fxBtnStart.setDisable(true);
   	 	fxBtnStop.setDisable(false);
		controller.handleViewStartClicked();
	}

	/**
	 * Handles the click on the stop button
	 */
	@FXML
	public void handleStopClicked() {
		fxBtnPrepare.setDisable(false);
   	 	fxBtnStart.setDisable(true);
   	 	fxBtnStop.setDisable(true);
		controller.handleViewStopClicked();
	}
	
	/**
	 * Handles the click on the prepare button
	 */
	@FXML
	public void handlePrepareClicked() {
   	 	controller.prepare();
   	 	fxBtnStart.setDisable(false);
	}
	
	
	public Label getFxSampleName() {
		return fxSampleName;
	}
}
