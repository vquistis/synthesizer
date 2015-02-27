package fr.istic.groupimpl.synthesizer.vco;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ViewComponent;
import fr.istic.groupimpl.synthesizer.util.Potentiometre;
import fr.istic.groupimpl.synthesizer.util.PotentiometreFactory;

/**
 * View of VCO module  .
 *
 * @authors GroupImpl
 */
public class ViewVco extends ViewComponent implements Initializable {

	/** The pane vco. */
	@FXML
	private BorderPane paneVco;
	
	/** The choice base freq. */
	@FXML
	private ChoiceBox<String> choiceBaseFreq;
	
	/** The choice amplitude. */
	@FXML
	private ChoiceBox<String> choiceAmplitude;
	
	/** The close vco. */
	@FXML
	private ImageView closeVco;
	
	/** The knob octave pane. */
	@FXML
	private VBox knobOctavePane;
	
	/** The knob freq pane. */
	@FXML
	private VBox knobFreqPane;
	
	/** The fm. */
	@FXML
	private ImageView fm;
	
	/** The out. */
	@FXML
	private ImageView out;
	
	/** The type output. */
	@FXML
	private ToggleGroup typeOutput;
	
	/** The display. */
	@FXML
	private Text display;
	
	/** The rg fm. */
	@FXML
	private Region rgFm;
	
	/** The freq label. */
	@FXML
	private Label freqLabel;

	/** The vco control. */
	private ControllerVco vcoControl;

	/**
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		PotentiometreFactory pf = PotentiometreFactory.getFactoryInstance();

		// Octave knob
		pf.setMinValue(0);
		pf.setMaxValue(9);
		pf.setDiscret(true);
		pf.setShowTickMarks(true);
		pf.setShowTickLabels(true);
		pf.setMajorTickUnit(1);
		pf.setNbSpins(0.88);
		pf.setRayon(32);
		pf.setValueDef(0);		
		Potentiometre octaveKnob = pf.getPotentiometre();
		knobOctavePane.getChildren().add(octaveKnob);

		// Precision knob
		pf.setNbSpins(0.80);
		pf.setDiscret(false);
		pf.setMinValue(-1);
		pf.setMaxValue(1);
		pf.setMinorTickUnit(1./12.);

		Potentiometre precisionKnob = pf.getPotentiometre();
		knobFreqPane.getChildren().add(precisionKnob);

		// VcoController creation and listeners on knob values
		vcoControl = new ControllerVco(freqLabel.textProperty());
		octaveKnob.valueProperty().addListener((p, oldVal, newVal) ->
		vcoControl.handleViewOctaveChange((double) newVal, precisionKnob.getValue()));
		precisionKnob.valueProperty().addListener((p, oldVal, newVal) ->
		vcoControl.handleViewOctaveChange(octaveKnob.getValue(), (double) newVal));

		typeOutput.selectedToggleProperty().addListener((obs, oldVal, newVal) ->
		vcoControl.handleViewOutputTypeChange(((RadioButton)newVal).getText()));
		
		// Listener close VCO
		closeVco.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			cleanup();
			vcoControl.handleViewClose();
			Pane parent = (Pane) paneVco.getParent();
			parent.getChildren().remove(paneVco);
		});
		
		// Choice base freq config + listener
		choiceBaseFreq.getItems().addAll("0.1 Hz", "1 Hz", "32 Hz", "1 kHz");
		choiceBaseFreq.getSelectionModel().select(1);
		choiceBaseFreq.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
				switch(newVal) {
					case "0.1 Hz":
						vcoControl.handleViewBaseFreqChange(0.1);
						break;
					case "1 Hz":
						vcoControl.handleViewBaseFreqChange(1.0);
						break;
					case "32 Hz":
						vcoControl.handleViewBaseFreqChange(32.0);
						break;
					case "1 kHz":
						vcoControl.handleViewBaseFreqChange(1000.0);
						break;
				}
		});
		// Choice amplitude
		choiceAmplitude.getItems().addAll("0.5 V", "1 V", "2 V", "5 V");
		choiceAmplitude.getSelectionModel().select(1);
		choiceAmplitude.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
				switch(newVal) {
					case "0.5 V":
						vcoControl.handleViewAmplitudeChange(0.5);
						break;
					case "1 V":
						vcoControl.handleViewAmplitudeChange(1.);
						break;
					case "2 V":
						vcoControl.handleViewAmplitudeChange(2.);
						break;
					case "5 V":
						vcoControl.handleViewAmplitudeChange(5.);
						break;
				}
		});

		addPort("vco_inputFm",fm);
		addPort("outputAmplitude",out);		
		
		addParameters("octave", () -> octaveKnob.getValue(), (val)-> octaveKnob.setValue(val));
		addParameters("precision", () -> precisionKnob.getValue(), (val) -> precisionKnob.setValue(val));
		
		addParameters("choiceBaseFreq", () -> (double) choiceBaseFreq.getSelectionModel().getSelectedIndex(), (val) -> choiceBaseFreq.getSelectionModel().select(val.intValue()));
		addParameters("choiceAmplitude", () -> (double) choiceAmplitude.getSelectionModel().getSelectedIndex(), (val) -> choiceAmplitude.getSelectionModel().select(val.intValue()));

		addParameters("typeOutput", () -> (double) typeOutput.getToggles().indexOf(typeOutput.getSelectedToggle()), (val) -> typeOutput.selectToggle(typeOutput.getToggles().get(val.intValue())));
	}

	/**
	 * @see fr.istic.groupimpl.synthesizer.component.ViewComponent#getComponentRoot()
	 */
	@Override
	protected Pane getComponentRoot() {
		return paneVco;
	}

	/**
	 * @see fr.istic.groupimpl.synthesizer.component.ViewComponent#getController()
	 */
	@Override
	protected ControllerComponent getController() {
		return vcoControl;
	}

	/**
	 * @see fr.istic.groupimpl.synthesizer.component.ViewComponent#getFilename()
	 */
	@Override
	public String getFilename() {
		return "fxml/vco.fxml";
	}

}
