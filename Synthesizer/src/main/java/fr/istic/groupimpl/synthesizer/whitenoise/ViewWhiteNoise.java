package fr.istic.groupimpl.synthesizer.whitenoise;

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

public class ViewWhiteNoise extends ViewComponent implements Initializable {

	@FXML private Pane rootModulePane;
	@FXML private GridPane top;
	@FXML private ImageView output;

	private DoubleProperty outputX = new SimpleDoubleProperty(0);
	private DoubleProperty outputY = new SimpleDoubleProperty(0);
	private ControllerWhiteNoise controller;

	@Override
	public void initialize(URL location, ResourceBundle resource) {
		((Label) top.lookup("#titleModule")).setText("White noise");
		
		addPort(output, outputX, outputY);
		
		// implementation of controller
		controller = new ControllerWhiteNoise();
		// Listener output
		output.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> controller.handleViewOutputClick(outputX, outputY));
		// Listener close module
		top.lookup("#closeModuleFx").addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			cleanupPorts();			
			controller.handleViewClose();
			Pane parent = (Pane) rootModulePane.getParent();
			parent.getChildren().remove(rootModulePane);
		});
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
		return "fxml/whiteNoise.fxml";
	}
}
