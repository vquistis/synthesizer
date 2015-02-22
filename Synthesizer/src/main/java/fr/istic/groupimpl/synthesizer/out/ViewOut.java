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

	@FXML private Pane rootModulePane;
	@FXML private GridPane top;
	@FXML private VBox knobVolumePane;
	@FXML private TextField valueVolumeFx;
	@FXML private CheckBox muteVolumeFx;
	@FXML private ImageView input;

	private ControllerOut controller;

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

	private void handleCloseModule() {
		cleanup();			
		controller.handleViewClose();
		Pane parent = (Pane) rootModulePane.getParent();
		parent.getChildren().remove(rootModulePane);
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
		return "fxml/out.fxml";
	}
}
