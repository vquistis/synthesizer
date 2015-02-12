package fr.istic.groupimpl.synthesizer.mixer;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ViewComponent;
import fr.istic.groupimpl.synthesizer.io.architecture.Module;
import fr.istic.groupimpl.synthesizer.util.DoubleStringConverter;
import fr.istic.groupimpl.synthesizer.util.Potentiometre;
import fr.istic.groupimpl.synthesizer.util.PotentiometreFactory;

public class ViewMixer extends ViewComponent implements Initializable {
	final Integer NumberOfInputPort = 4;
	
	@FXML private Pane rootModulePane;
	@FXML private HBox inputHBox;
	@FXML private ImageView fxOutput;
	@FXML private ImageView closeModuleFx;

	private DoubleProperty outputX = new SimpleDoubleProperty(0);
	private DoubleProperty outputY = new SimpleDoubleProperty(0);
	private ControllerMixer controller;
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {	
		ConfigurateMixer(NumberOfInputPort);
	}

	public void ConfigurateMixer(Integer NumberOfInputPort) {
		// Creation du controller
		controller = new ControllerMixer(NumberOfInputPort);
		
		PotentiometreFactory knobFact = PotentiometreFactory.getFactoryInstance();
		knobFact.setMinValue(-60);
		knobFact.setMaxValue(12);
		knobFact.setValueDef(0);
		knobFact.setNbSpins(3);
		knobFact.setRayon(32);
		
		// Generate input port
        for(int i = 0; i <= controller.getNumberOfInputPort() - 1; i++)
        {
    		// Add the input view to Mixer
    		inputHBox.getChildren().add((HBox) createViewMixerInput(i, controller, knobFact));
        } 
        
		// Listener output
        addPort(fxOutput, outputX, outputY);
		fxOutput.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> controller.handleViewOutputClick(outputX, outputY));	
		// Listener close module
		closeModuleFx.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			cleanupPorts();			
			controller.handleViewClose();
			Pane parent = (Pane) rootModulePane.getParent();
			parent.getChildren().remove(rootModulePane);
		});
	}
	
	private ViewMixerInput createViewMixerInput(Integer index, ControllerMixer controler, PotentiometreFactory knobFact) {
		// faire table de knobVolume
		Potentiometre knobVolume = knobFact.getPotentiometre();
	 
		// Generate the input view
		ViewMixerInput inputView = new ViewMixerInput("in-" + (index + 1));
		// Add the knobVolume to the input view
		inputView.getFxKnobVolumePane().getChildren().add(1,knobVolume);
		
		// Add port to the ViewComponent
		DoubleProperty inputX = new SimpleDoubleProperty(0);
		DoubleProperty inputY = new SimpleDoubleProperty(0);
		addPort(inputView.getFxInput(), inputX, inputY);
		// Bind knob value and text field value
		StringConverter<Number> converter = new DoubleStringConverter();
		Bindings.bindBidirectional(inputView.getFxValueVolume().textProperty(), knobVolume.valueProperty(), converter);
		// Listener volume
		knobVolume.valueProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewVolumeChange(index, newVal));
		// Listener mute
		inputView.getFxMuteVolume().selectedProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewMuteChange(index, newVal));
		// Listener input
		inputView.getFxInput().addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> controller.handleViewInputClick(index, inputX, inputY));
		
		return inputView;
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
