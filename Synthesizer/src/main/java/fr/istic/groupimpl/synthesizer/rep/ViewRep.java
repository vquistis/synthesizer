package fr.istic.groupimpl.synthesizer.rep;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
 * @authors GroupImpl
 * Rep module - JavaFX Controller
 */
public class ViewRep extends ViewComponent implements Initializable{
	
	@FXML private Pane rootModulePane;
	@FXML private GridPane top;
	@FXML private ImageView in;
	@FXML private ImageView out1, out2, out3;
	
	private ControllerRep controller;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		((Label) top.lookup("#titleModule")).setText("REP");
		
		controller = new ControllerRep();
		
		// Listener close module
		top.lookup("#closeModuleFx").addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			cleanupPorts();			
			controller.handleViewClose();
			Pane parent = (Pane) rootModulePane.getParent();
			parent.getChildren().remove(rootModulePane);
		});
		
		addPort("in", in);
		addPort("out1", out1);
		addPort("out2",out2);
		addPort("out3", out3);
	}
	
	/**
	 * Handles the click on the close button
	 */
	public void handleCloseClick() {
		controller.handleViewClose();
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
		return "fxml/rep.fxml";
	}

}
