package fr.istic.groupimpl.synthesizer.keyboard;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ViewComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

public class ViewKeyboard extends ViewComponent implements Initializable {

	@FXML private Pane rootModulePane;
	@FXML private ImageView closeModuleFx;
	@FXML private GridPane top;
	@FXML private ImageView output;
	@FXML private Label keyboard;


	private ControllerKeyboard controller;

	@Override
	public void initialize(URL location, ResourceBundle resource) {
		((Label) top.lookup("#titleModule")).setText("Keyboard");

		// implementation of controller
		controller = new ControllerKeyboard(); 

		// add ports
		addPort("output", output);

		// Listener close module
		top.lookup("#closeModuleFx").addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			cleanupPorts();			
			controller.handleViewClose();
			Pane parent = (Pane) rootModulePane.getParent();
			parent.getChildren().remove(rootModulePane);
		});

		// Recovery keyboards keys when key is pressed
		ControllerGlobal.getInstance().getStage().addEventHandler(KeyEvent.KEY_PRESSED, (event) ->  {
			controller.handleViewkeyEvent(event.getCode().toString());

		}); 
		// Recovery keyboards keys when key is pressed
		ControllerGlobal.getInstance().getStage().addEventHandler(KeyEvent.KEY_RELEASED, (event) ->  {
			controller.handleViewkeyEvent("B");

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
		return "fxml/keyboard.fxml";
	}
}
