package fr.istic.groupimpl.synthesizer.out;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ViewComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;
import fr.istic.groupimpl.synthesizer.util.DoubleStringConverter;
import fr.istic.groupimpl.synthesizer.util.Potentiometre;
import fr.istic.groupimpl.synthesizer.util.PotentiometreFactory;

/**
 * 
 * View of out module
 *  
 * @author Team GroupImpl
 */
public class ViewOut extends ViewComponent implements Initializable {

	/** The root module pane. */
	@FXML private Pane rootModulePane;
	
	/** The top. */
	@FXML private GridPane top;
	
	/** The knob volume pane. */
	@FXML private VBox knobVolumePane;
	
	/** The value volume fx. */
	@FXML private TextField valueVolumeFx;
	
	/** The mute volume fx. */
	@FXML private CheckBox muteVolumeFx;
	
	/** The input. */
	@FXML private ImageView input;

	/** The controller. */
	private ControllerOut controller;

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		((Label) top.lookup("#titleModule")).setText("OUT");
		
		// Creation du controller
		controller = new ControllerOut();
		
		addPort("out_input", input);
		
		PotentiometreFactory knobFact = PotentiometreFactory.getFactoryInstance();
		knobFact.setMinValue(-60);
		knobFact.setMaxValue(12);
		knobFact.setValueDef(0);
		knobFact.setNbSpins(3);
		knobFact.setRayon(32);
		Potentiometre knobVolume = knobFact.getPotentiometre();
		knobVolumePane.getChildren().add(1,knobVolume);
		
		// Bind knob value and text field value
		StringConverter<Number> converter = new DoubleStringConverter();
		Bindings.bindBidirectional(valueVolumeFx.textProperty(), knobVolume.valueProperty(), converter);

		// Listener volume
		knobVolume.valueProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewVolumeChange(newVal));
		// Listener mute
		muteVolumeFx.selectedProperty().addListener((obsVal, oldVal, newVal) ->{
			if (ControllerGlobal.getInstance().isRecordStarted()) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Synthesizer");
				alert.setHeaderText("Voulez-vous vraiment arrêter l'enregistrement de son ?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
					controller.handleViewMuteChange(newVal);
					ControllerGlobal.getInstance().handleStopView();
				} else {
					muteVolumeFx.setSelected(oldVal);;
				}
			} else {
				controller.handleViewMuteChange(newVal);
			}
		});

		// Listener close module
		top.lookup("#closeModuleFx").addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			if (ControllerGlobal.getInstance().isRecordStarted()) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Synthesizer");
				alert.setHeaderText("Voulez-vous vraiment arrêter l'enregistrement de son ?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
					handleCloseModule();
					ControllerGlobal.getInstance().handleStopView();
				}
			} else {
				handleCloseModule();
			}
		});

		addParameters("knobVolume", () -> knobVolume.getValue(), (val) -> knobVolume.setValue(val));
		addParameters("muteVolumeFx", () -> muteVolumeFx.selectedProperty().get() ? 1.0 : 0.0, (val) -> muteVolumeFx.setSelected(val==1));
	}

	/**
	 * Handle close module.
	 */
	private void handleCloseModule() {
		cleanup();			
		controller.handleViewClose();
		Pane parent = (Pane) rootModulePane.getParent();
		parent.getChildren().remove(rootModulePane);
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
		return "fxml/out.fxml";
	}
}
