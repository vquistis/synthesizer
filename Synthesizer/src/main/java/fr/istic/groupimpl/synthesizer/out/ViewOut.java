package fr.istic.groupimpl.synthesizer.out;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
import fr.istic.groupimpl.synthesizer.util.PotentiometreFactory;

public class ViewOut implements IViewComponent, Initializable {

	@FXML private ImageView closeModuleFx;
	@FXML private VBox knobVolumePane;
	@FXML private TextField valueVolumeFx;
	@FXML private CheckBox muteVolumeFx;
	@FXML private ImageView input;
	
	/**
	 * Converter -12 / +12 to -Inf / +12
	 * @param val - value between -12db and +12db
	 * @return value between -Inf and +12db
	 */
	private Double convVal(Double val){
		if (val >= 0) {
			return val;
		}
		if (val < -11.9) {
			val = -11.9;
		}
		return (-1./(12.+val)+1./12.)*132 ;
	}
	private DoubleProperty knobInfValue = new SimpleDoubleProperty();
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		PotentiometreFactory knobFact = PotentiometreFactory.getFactoryInstance();
		knobFact.setMinValue(-12);
		knobFact.setMaxValue(12);
		Potentiometre volumeKnob = knobFact.getPotentiometre();
		volumeKnob.valueProperty().addListener((obsVal, oldVal, newVal) -> knobInfValue.set(convVal((Double)newVal)));
		knobVolumePane.getChildren().add(volumeKnob);
		
		// Bind knob value and text field value
		StringConverter<Number> converter = new NumberStringConverter();
		Bindings.bindBidirectional(valueVolumeFx.textProperty(), knobInfValue, converter);

		// Creation du controller
		ControllerOut controller = new ControllerOut();
		// Listener volume
		knobInfValue.addListener((obsVal, oldVal, newVal) -> controller.handleViewVolumeChange(newVal));
		// Listener mute
		muteVolumeFx.selectedProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewMuteChange(newVal));
		// Listener input
		input.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> controller.handleViewInputClick("input_out"));
		// Listener close module
		closeModuleFx.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> controller.handleViewClose());
	}
}
