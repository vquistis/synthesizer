package fr.istic.groupimpl.synthesizer.whitenoise;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ViewComponent;

/**
 * View of White noise module.
 *
 * @author Team GroupImpl
 */
public class ViewWhiteNoise extends ViewComponent implements Initializable {

	/** The root module pane. */
	@FXML private Pane rootModulePane;
	
	/** The top. */
	@FXML private GridPane top;
	
	/** The output. */
	@FXML private ImageView output;

	/** The controller. */
	private ControllerWhiteNoise controller;

	/**
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		((Label) top.lookup("#titleModule")).setText("White noise");
		
		// implementation of controller
		controller = new ControllerWhiteNoise();
		
		addPort("noise_output", output);	

		// Listener close module
		top.lookup("#closeModuleFx").addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			cleanup();			
			controller.handleViewClose();
			Pane parent = (Pane) rootModulePane.getParent();
			parent.getChildren().remove(rootModulePane);
		});
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
		return "fxml/whiteNoise.fxml";
	}
}
