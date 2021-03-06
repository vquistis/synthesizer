package fr.istic.groupimpl.synthesizer.rep;

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
 * View of Rep module .
 *
 * @author Team GroupImpl
 */
public class ViewRep extends ViewComponent implements Initializable{
	
	/** The root module pane. */
	@FXML private Pane rootModulePane;
	
	/** The top. */
	@FXML private GridPane top;
	
	/** The in. */
	@FXML private ImageView in;
	
	/** The out3. */
	@FXML private ImageView out1, out2, out3;
	
	/** The controller. */
	private ControllerRep controller;

	/**
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		((Label) top.lookup("#titleModule")).setText("REP");
		
		controller = new ControllerRep();
		
		// Listener close module
		top.lookup("#closeModuleFx").addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			cleanup();			
			controller.handleViewClose();
			Pane parent = (Pane) rootModulePane.getParent();
			parent.getChildren().remove(rootModulePane);
		});
		
		addPort("rep_in", in);
		addPort("rep_out1", out1);
		addPort("rep_out2",out2);
		addPort("rep_out3", out3);
	}
	
	/**
	 * Handles the click on the close button.
	 */
	public void handleCloseClick() {
		controller.handleViewClose();
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
		return "fxml/rep.fxml";
	}

}
