package fr.istic.groupimpl.synthesizer.player;

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
 * View of player module
 *  
 * @author Team GroupImpl
 */
public class ViewPlayer extends ViewComponent implements Initializable {

	@FXML private Pane rootModulePane;
	@FXML private GridPane top;
	@FXML private ImageView output;
	@FXML private ImageView gate;
	@FXML private Label fxSampleName;
	@FXML private Button fxBtnLoad;
	@FXML private Button fxBtnPlay;
	@FXML private Button fxBtnStop;

	private ControllerPlayer controller;

	@Override
	public void initialize(URL location, ResourceBundle resource) {
		((Label) top.lookup("#titleModule")).setText("Player");
		
		// implementation of controller
		controller = new ControllerPlayer(this);
		
		addPort("player_output", output);	
		addPort("player_gate", gate);	
		
		// Listener close module
		top.lookup("#closeModuleFx").addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			cleanup();			
			controller.handleViewClose();
			Pane parent = (Pane) rootModulePane.getParent();
			parent.getChildren().remove(rootModulePane);
		});
		
   	 	fxBtnPlay.setDisable(true);
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
		return "fxml/player.fxml";
	}
	
	/**
	 * Handles the click on the play button
	 */
	@FXML
	public void handlePlayClicked() {
		controller.handleViewPlayClicked();
	}

	/**
	 * Handles the click on the stop button
	 */
	@FXML
	public void handleStopClicked() {
		controller.handleViewStopClicked();
	}
	
	/**
	 * Handles the click on the load button
	 */
	@FXML
	public void handleLoadClicked() {
   	 	controller.loadSample();
   	 	fxBtnPlay.setDisable(false);
   	 	fxBtnStop.setDisable(false);
	}
	
	
	public Label getFxSampleName() {
		return fxSampleName;
	}
}
