package fr.istic.groupimpl.synthesizer.mixer;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ViewComponent;
import fr.istic.groupimpl.synthesizer.util.DoubleStringConverter;
import fr.istic.groupimpl.synthesizer.util.Potentiometre;
import fr.istic.groupimpl.synthesizer.util.PotentiometreFactory;

/**
 * View of mixer module.
 *
 * @author Team GroupImpl
 */
public class ViewMixer extends ViewComponent implements Initializable {
	
	/** The Constant RED_BAR. */
	private static final String RED_BAR    = "red-bar";
	
	/** The Constant GREEN_BAR. */
	private static final String GREEN_BAR  = "green-bar";
	
	/** The Constant barColorStyleClasses. */
	private static final String[] barColorStyleClasses = { RED_BAR, GREEN_BAR };
	  
	/** The Number of input port. */
	private Integer NumberOfInputPort = 4;
	
	/** The root module pane. */
	@FXML private Pane rootModulePane;
	
	/** The input h box. */
	@FXML private HBox inputHBox;
	
	/** The close module fx. */
	@FXML private ImageView closeModuleFx;
	
	/** The top. */
	@FXML private GridPane top;
	
	/** The fx output. */
	private ImageView fxOutput;
	
	/** The output gauge. */
	private ProgressBar outputGauge;
	
	/** The controller. */
	private ControllerMixer controller;
	
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resource) {	
		configurate();
	}

	/**
	 * Set the number of input port generated.
	 *
	 * @return the number of input port
	 */
	public Integer getNumberOfInputPort() {
		return NumberOfInputPort;
	}

	/**
	 * Set the number of input port to generate.
	 *
	 * @param numberOfInputPort the new number of input port
	 */
	public void setNumberOfInputPort(Integer numberOfInputPort) {
		NumberOfInputPort = numberOfInputPort;
	}
	
	/**
	 * Get instance of the ouput gauge.
	 *
	 * @return ProgressBar
	 */
	public ProgressBar getOutputGauge() {
		return outputGauge;
	}
	
	/**
	 * Method to configurate the view.
	 */
	public void configurate() {
		((Label) top.lookup("#titleModule")).setText("MIXER");
		
		outputGauge = configureGaugeBar("#fxOutputGauge"); 
		
		// Creation du controller
		controller = new ControllerMixer(this, NumberOfInputPort);
		
		PotentiometreFactory knobFact = PotentiometreFactory.getFactoryInstance();
		knobFact.setMinValue(-60);
		knobFact.setMaxValue(12);
		knobFact.setValueDef(0);
		knobFact.setNbSpins(3);
		knobFact.setRayon(32);
		
		// Generate input port
        for(int i = 0; i < controller.getNumberOfInputPort(); i++)
        {
    		// Add the input view to Mixer
    		inputHBox.getChildren().add((HBox) createViewMixerInput(i, controller, knobFact));
        }
        
		// Listener output
        fxOutput = (ImageView) inputHBox.lookup("#fxOutput");
        addPort("output",fxOutput);
		top.lookup("#closeModuleFx").addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			cleanup();
			controller.handleViewClose();
			Pane parent = (Pane) rootModulePane.getParent();
			parent.getChildren().remove(rootModulePane);
		});
	}
	
	/**
	 *  Configure Gauge Bar.
	 *
	 * @param fxmlNodeName   Name of the xml node
	 * @return ProgressBar
	 */
	private ProgressBar configureGaugeBar(String fxmlNodeName) {
		ProgressBar gaugeBar;
		gaugeBar = (ProgressBar) inputHBox.lookup(fxmlNodeName);
		gaugeBar.progressProperty().addListener(new ChangeListener<Number>() {
	        @Override public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
	          double progress = newValue == null ? 0 : newValue.doubleValue();
	          if (progress < 0.85) {
	            setBarStyleClass(gaugeBar, GREEN_BAR);
	          } else {
	            setBarStyleClass(gaugeBar, RED_BAR);
	          }
	        }

	        private void setBarStyleClass(ProgressBar bar, String barStyleClass) {
	          bar.getStyleClass().removeAll(barColorStyleClasses);
	          bar.getStyleClass().add(barStyleClass);
	        }
	      });
		return gaugeBar;
	}
	
	/**
	 * Generate the input view.
	 *
	 * @param index 	index of the input port
	 * @param controller the controller
	 * @param knobFact  PotentiometreFactory
	 * @return the view mixer input
	 */
	private ViewMixerInput createViewMixerInput(Integer index, ControllerMixer controller, PotentiometreFactory knobFact) {
		// faire table de knobVolume
		Potentiometre knobVolume = knobFact.getPotentiometre();
	 
		// Generate the input view
		ViewMixerInput inputView = new ViewMixerInput("in-" + (index + 1));
		// Add the knobVolume to the input view
		inputView.getFxKnobVolumePane().getChildren().add(1,knobVolume);
		
		// Add port to the ViewComponent
		addPort("mixer_input"+index,inputView.getFxInput());
		// Bind knob value and text field value
		StringConverter<Number> converter = new DoubleStringConverter();
		Bindings.bindBidirectional(inputView.getFxValueVolume().textProperty(), knobVolume.valueProperty(), converter);
		// Listener volume
		knobVolume.valueProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewVolumeChange(index, newVal));
		// Listener mute
		inputView.getFxMuteVolume().selectedProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewMuteChange(index, newVal));
		// Listener input
		//inputView.getFxInput().addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> controller.handleViewInputClick(index, inputX, inputY));
		
		addParameters("knobVolume" + (index + 1), () -> knobVolume.getValue(), (val) -> knobVolume.setValue(val));
		addParameters("muteVolumeFx"+ (index + 1), () -> inputView.getFxMuteVolume().selectedProperty().get() ? 1.0 : 0.0, (val) -> inputView.getFxMuteVolume().setSelected(val==1));	
		
		return inputView;
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
		return "fxml/mixer.fxml";
	}
}
