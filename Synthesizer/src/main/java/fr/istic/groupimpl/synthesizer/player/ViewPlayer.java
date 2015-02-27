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

	/** The root module pane. */
	@FXML private Pane rootModulePane;
	
	/** The top. */
	@FXML private GridPane top;
	
	/** The output. */
	@FXML private ImageView output;
	
	/** The gate. */
	@FXML private ImageView gate;
	
	/** The fx sample name. */
	@FXML private Label fxSampleName;
	
	/** The fx btn load. */
	@FXML private Button fxBtnLoad;
	
	/** The fx btn play. */
	@FXML private Button fxBtnPlay;
	
	/** The fx btn stop. */
	@FXML private Button fxBtnStop;

	/** The controller. */
	private ControllerPlayer controller;

	/**
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
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

	/**
	 * @see fr.istic.groupimpl.synthesizer.component.ViewComponent#getComponentRoot()
	 */
	@Override
	protected Pane getComponentRoot() {
		return rootModulePane;
	}

	/**
	 * @see fr.istic.groupimpl.synthesizer.component.ViewComponent#getController()
	 */
	@Override
	protected ControllerComponent getController() {
		return controller;
	}

	/**
	 * @see fr.istic.groupimpl.synthesizer.component.ViewComponent#getFilename()
	 */
	@Override
	public String getFilename() {
		return "fxml/player.fxml";
	}
	
	/**
	 * Handles the click on the play button.
	 */
	@FXML
	public void handlePlayClicked() {
		controller.handleViewPlayClicked();
	}

	/**
	 * Handles the click on the stop button.
	 */
	@FXML
	public void handleStopClicked() {
		controller.handleViewStopClicked();
	}
	
	/**
	 * Handles the click on the load button.
	 */
	@FXML
	public void handleLoadClicked() {
   	 	controller.loadSample();
   	 	if (fxSampleName.getText()!=null) {
	   	 	fxBtnPlay.setDisable(false);
	   	 	fxBtnStop.setDisable(false);
   	 	}
	}
	
	/**
	 * Gets the fx sample name.
	 *
	 * @return the fx sample name
	 */
	public Label getFxSampleName() {
		return fxSampleName;
	}
}
