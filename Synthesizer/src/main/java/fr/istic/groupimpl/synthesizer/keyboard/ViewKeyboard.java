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

/**
 * View of keyboard module.
 *
 * @author Team GroupImpl
 */
public class ViewKeyboard extends ViewComponent implements Initializable {

	/** The root module pane. */
	@FXML private Pane rootModulePane;
	
	/** The close module fx. */
	@FXML private ImageView closeModuleFx;
	
	/** The top. */
	@FXML private GridPane top;
	
	/** The output key cv. */
	@FXML private ImageView outputKeyCV;
	
	/** The output key gate. */
	@FXML private ImageView outputKeyGate;
	
	/** The keyboard. */
	@FXML private Label keyboard;


	/** The controller. */
	private ControllerKeyboard controller;

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		((Label) top.lookup("#titleModule")).setText("Keyboard");

		// implementation of controller
		controller = new ControllerKeyboard(); 

		// add ports
		addPort("outputKeyCV", outputKeyCV);
		addPort("outputKeyGate", outputKeyGate);

		// Listener close module
		top.lookup("#closeModuleFx").addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			cleanup();			
			controller.handleViewClose();
			Pane parent = (Pane) rootModulePane.getParent();
			parent.getChildren().remove(rootModulePane);
		});

		// Recovery keyboards keys when key is pressed
		ControllerGlobal.getInstance().getStage().addEventHandler(KeyEvent.KEY_PRESSED, (event) ->  {
			controller.handleViewkeyEvent(event.getCode().toString());

		}); 
		// Recovery keyboards keys when key is released
		ControllerGlobal.getInstance().getStage().addEventHandler(KeyEvent.KEY_RELEASED, (event) ->  {
			controller.handleViewkeyReleaseEvent();

		}); 
	}
	
	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.ViewComponent#getComponentRoot()
	 */
	@Override
	protected Pane getComponentRoot() {
		return rootModulePane;
	}

	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.ViewComponent#getController()
	 */
	@Override
	protected ControllerComponent getController() {
		return controller;
	}

	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.ViewComponent#getFilename()
	 */
	@Override
	public String getFilename() {
		return "fxml/keyboard.fxml";
	}
}
