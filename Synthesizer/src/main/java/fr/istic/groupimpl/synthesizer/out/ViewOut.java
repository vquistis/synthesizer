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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import fr.istic.groupimpl.synthesizer.component.ViewComponent;
import fr.istic.groupimpl.synthesizer.util.DoubleStringConverter;
import fr.istic.groupimpl.synthesizer.util.Potentiometre;
import fr.istic.groupimpl.synthesizer.util.PotentiometreFactory;

public class ViewOut extends ViewComponent implements Initializable {

	@FXML private Pane rootModulePane;
	@FXML private ImageView closeModuleFx;
	@FXML private VBox knobVolumePane;
	@FXML private TextField valueVolumeFx;
	@FXML private CheckBox muteVolumeFx;
	@FXML private ImageView input;

	private DoubleProperty inputX = new SimpleDoubleProperty(0);
	private DoubleProperty inputY = new SimpleDoubleProperty(0);

	@Override
	public void initialize(URL location, ResourceBundle resource) {
		PotentiometreFactory knobFact = PotentiometreFactory.getFactoryInstance();
		knobFact.setMinValue(-60);
		knobFact.setMaxValue(12);
		knobFact.setValueDef(0);
		knobFact.setNbSpins(3);
		knobFact.setRayon(32);
		Potentiometre knobVolume = knobFact.getPotentiometre();
		knobVolumePane.getChildren().add(knobVolume);
		
		// Bind knob value and text field value
		StringConverter<Number> converter = new DoubleStringConverter();
		Bindings.bindBidirectional(valueVolumeFx.textProperty(), knobVolume.valueProperty(), converter);

		// Creation du controller
		ControllerOut controller = new ControllerOut();
		// Listener volume
		knobVolume.valueProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewVolumeChange(newVal));
		// Listener mute
		muteVolumeFx.selectedProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewMuteChange(newVal));
		// Listener input
		input.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			controller.handleViewInputClick("out_input", inputX, inputY);
		});
		// Listener close module
		closeModuleFx.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			cleanupPorts();			
			controller.handleViewClose();
			Pane parent = (Pane) rootModulePane.getParent();
			parent.getChildren().remove(rootModulePane);
		});
		addPort(input, inputX, inputY);
	}

	@Override
	protected Pane getComponentRoot() {
		return rootModulePane;
	}
}
