package fr.istic.groupimpl.synthesizer.keyboard;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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
	
	 final EventHandler<KeyEvent> keyEventHandler =
		        new EventHandler<KeyEvent>() {
		            public void handle(final KeyEvent keyEvent) {
		            	System.out.println("keyEvent.getCode()" + keyEvent.getCode() );
		            	System.out.println("keyEvent.getEventType()" + keyEvent.getEventType() );
		            	System.out.println("KeyEvent.KEY_PRESSED" + KeyEvent.KEY_PRESSED );
		                    System.out.println("key pressed : " + keyEvent.getCode().toString() + " " + keyEvent.getCharacter());
		                    keyEvent.consume();
		                }
		            };
		      

	@Override
	public void initialize(URL location, ResourceBundle resource) {
		((Label) top.lookup("#titleModule")).setText("Keyboard");

		addPort("output", output);

		// implementation of controller
		controller = new ControllerKeyboard();

		// Listener close module
		top.lookup("#closeModuleFx").addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			cleanupPorts();			
			controller.handleViewClose();
			Pane parent = (Pane) rootModulePane.getParent();
			parent.getChildren().remove(rootModulePane);
		});

		ControllerGlobal.getInstance().getStage().addEventHandler(KeyEvent.KEY_PRESSED, (event) ->  {
				System.out.println("!! " + event.getCode().toString());
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
