package fr.istic.groupimpl.synthesizer.rep;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import fr.istic.groupimpl.synthesizer.component.ViewComponent;
import fr.istic.groupimpl.synthesizer.io.architecture.Configuration;

/**
 * @authors GroupImpl
 * Rep module - JavaFX Controller
 */
public class ViewRep extends ViewComponent implements Initializable{
	
	@FXML private Pane rootModulePane;
	@FXML private ImageView in;
	@FXML private ImageView out1, out2, out3;
	@FXML private ImageView close;
	
	private ControllerRep controller;
	
	private DoubleProperty inX = new SimpleDoubleProperty(0);
	private DoubleProperty inY = new SimpleDoubleProperty(0);
	private DoubleProperty outX1 = new SimpleDoubleProperty(0);
	private DoubleProperty outY1 = new SimpleDoubleProperty(0);
	private DoubleProperty outX2 = new SimpleDoubleProperty(0);
	private DoubleProperty outY2 = new SimpleDoubleProperty(0);
	private DoubleProperty outX3 = new SimpleDoubleProperty(0);
	private DoubleProperty outY3 = new SimpleDoubleProperty(0);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		controller = new ControllerRep();
		
		// Listener close module
		close.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			cleanupPorts();			
			controller.handleViewClose();
			Pane parent = (Pane) rootModulePane.getParent();
			parent.getChildren().remove(rootModulePane);
		});
		
		addPort(in, inX, inY);
		addPort(out1, outX1, outY1);
		addPort(out2, outX2, outY2);
		addPort(out3, outX3, outY3);
	}
	
	/**
	 * Handles the click on the input port
	 */
	@FXML
	public void handleInputClick() {
		controller.handleViewInputClick(inX, inY);
	}

	/**
	 * Handles the click on the first output port
	 */
	@FXML
	public void handleOutputClick1() {
		controller.handleViewOutputClick("rep_out1", outX1, outY1);
	}
	
	/**
	 * Handles the click on the second output port
	 */
	@FXML
	public void handleOutputClick2() {
		controller.handleViewOutputClick("rep_out2", outX2, outY2);
	}
	
	/**
	 * Handles the click on the third output port
	 */
	@FXML
	public void handleOutputClick3() {
		controller.handleViewOutputClick("rep_out3", outX3, outY3);
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
	protected Configuration getConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}
