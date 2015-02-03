package fr.istic.groupimpl.synthesizer.out;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import fr.istic.groupimpl.synthesizer.component.IViewComponent;
import fr.istic.groupimpl.synthesizer.util.Potentiometre;

public class ViewOut implements IViewComponent, Initializable {

	@FXML private VBox knobVolumePane;
	@FXML private TextField valueVolumeFx;
	@FXML private CheckBox muteVolumeFx;
	@FXML private ImageView input;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		Potentiometre volumeKnob = new Potentiometre("");
		volumeKnob.setMin(0);
		volumeKnob.setMax(12);
		knobVolumePane.getChildren().add(volumeKnob);

		// Bind knob value and text field value
		StringConverter<Number> converter = new NumberStringConverter();
		Bindings.bindBidirectional(valueVolumeFx.textProperty(), volumeKnob.valueProperty(), converter);
		
		// Listener volume & mute
		ControllerOut controller = new ControllerOut();
		volumeKnob.valueProperty().addListener((obsVal, newVal, oldVal) -> controller.handleViewVolumeChange(newVal));
		muteVolumeFx.selectedProperty().addListener((obsVal, newVal, oldVal) -> controller.handleViewMuteChange(newVal));
		input.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> controller.handleViewInputClick("input_out"));
	}
}
