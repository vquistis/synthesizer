package fr.istic.groupimpl.synthesizer.mixer;

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
import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ViewComponent;
import fr.istic.groupimpl.synthesizer.io.architecture.Module;
import fr.istic.groupimpl.synthesizer.util.DoubleStringConverter;
import fr.istic.groupimpl.synthesizer.util.Potentiometre;
import fr.istic.groupimpl.synthesizer.util.PotentiometreFactory;

public class ViewMixer extends ViewComponent implements Initializable {

	@FXML private Pane rootModulePane;
	@FXML private ImageView closeModuleFx;
	
	@FXML private VBox knobVolumePane1;
	@FXML private TextField valueVolumeFx1;
	@FXML private CheckBox muteVolumeFx1;
	@FXML private VBox knobVolumePane2;
	@FXML private TextField valueVolumeFx2;
	@FXML private CheckBox muteVolumeFx2;
	@FXML private VBox knobVolumePane3;
	@FXML private TextField valueVolumeFx3;
	@FXML private CheckBox muteVolumeFx3;
	@FXML private VBox knobVolumePane4;
	@FXML private TextField valueVolumeFx4;
	@FXML private CheckBox muteVolumeFx4;
	
	@FXML private ImageView input1;
	@FXML private ImageView input2;
	@FXML private ImageView input3;
	@FXML private ImageView input4;
	@FXML private ImageView output;

	private DoubleProperty inputX1 = new SimpleDoubleProperty(0);
	private DoubleProperty inputX2 = new SimpleDoubleProperty(0);
	private DoubleProperty inputX3 = new SimpleDoubleProperty(0);
	private DoubleProperty inputX4 = new SimpleDoubleProperty(0);
	private DoubleProperty inputY1 = new SimpleDoubleProperty(0);
	private DoubleProperty inputY2 = new SimpleDoubleProperty(0);
	private DoubleProperty inputY3 = new SimpleDoubleProperty(0);
	private DoubleProperty inputY4 = new SimpleDoubleProperty(0);
	private DoubleProperty outputX = new SimpleDoubleProperty(0);
	private DoubleProperty outputY = new SimpleDoubleProperty(0);
	private ControllerMixer controller;
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		addPort(input1, inputX1, inputY1);
		addPort(input2, inputX2, inputY2);
		addPort(input3, inputX3, inputY3);
		addPort(input4, inputX4, inputY4);
		addPort(output, outputX, outputY);
		
		PotentiometreFactory knobFact = PotentiometreFactory.getFactoryInstance();
		knobFact.setMinValue(-60);
		knobFact.setMaxValue(12);
		knobFact.setValueDef(0);
		knobFact.setNbSpins(3);
		knobFact.setRayon(32);
		
		Potentiometre knobVolume1 = knobFact.getPotentiometre();
		Potentiometre knobVolume2 = knobFact.getPotentiometre();
		Potentiometre knobVolume3 = knobFact.getPotentiometre();
		Potentiometre knobVolume4 = knobFact.getPotentiometre();
		
		knobVolumePane1.getChildren().add(1,knobVolume1);
		knobVolumePane2.getChildren().add(1,knobVolume2);
		knobVolumePane3.getChildren().add(1,knobVolume3);
		knobVolumePane4.getChildren().add(1,knobVolume4);
		
		// Bind knob value and text field value
		StringConverter<Number> converter1 = new DoubleStringConverter();
		Bindings.bindBidirectional(valueVolumeFx1.textProperty(), knobVolume1.valueProperty(), converter1);
		StringConverter<Number> converter2 = new DoubleStringConverter();
		Bindings.bindBidirectional(valueVolumeFx2.textProperty(), knobVolume2.valueProperty(), converter2);
		StringConverter<Number> converter3 = new DoubleStringConverter();
		Bindings.bindBidirectional(valueVolumeFx3.textProperty(), knobVolume3.valueProperty(), converter3);
		StringConverter<Number> converter4 = new DoubleStringConverter();
		Bindings.bindBidirectional(valueVolumeFx4.textProperty(), knobVolume4.valueProperty(), converter4);
		
		// Creation du controller
		controller = new ControllerMixer();
		// Listener volume
		knobVolume1.valueProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewVolumeChange(0, newVal));
		knobVolume2.valueProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewVolumeChange(1, newVal));
		knobVolume3.valueProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewVolumeChange(2, newVal));
		knobVolume4.valueProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewVolumeChange(3, newVal));
		// Listener mute
		muteVolumeFx1.selectedProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewMuteChange(0, newVal));
		muteVolumeFx2.selectedProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewMuteChange(1, newVal));
		muteVolumeFx3.selectedProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewMuteChange(2, newVal));
		muteVolumeFx4.selectedProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewMuteChange(3, newVal));
		// Listener input
		input1.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> controller.handleViewInputClick(0, inputX1, inputY1));
		input2.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> controller.handleViewInputClick(1, inputX2, inputY2));
		input3.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> controller.handleViewInputClick(2, inputX3, inputY3));
		input4.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> controller.handleViewInputClick(3, inputX4, inputY4));
		// Listener output
		output.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> controller.handleViewOutputClick(outputX, outputY));	
		// Listener close module
		closeModuleFx.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
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
	protected Module getConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ControllerComponent getController() {
		// TODO Auto-generated method stub
		return controller;
	}
}
