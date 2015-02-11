package fr.istic.groupimpl.synthesizer.vco;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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
import fr.istic.groupimpl.synthesizer.component.ViewComponent;
import fr.istic.groupimpl.synthesizer.io.architecture.Configuration;
import fr.istic.groupimpl.synthesizer.io.architecture.Module;
import fr.istic.groupimpl.synthesizer.util.Potentiometre;
import fr.istic.groupimpl.synthesizer.util.PotentiometreFactory;

/**
 * @authors GroupImpl
 * VCO module - JavaFX Controller
 */
public class ViewVco extends ViewComponent implements Initializable {

	@FXML
	private BorderPane paneVco;
	@FXML
	private ChoiceBox<String> choiceBaseFreq;
	@FXML
	private ImageView closeVco;
	@FXML
	private VBox knobOctavePane;
	@FXML
	private VBox knobFreqPane;
	@FXML
	private ImageView fm;
	@FXML
	private ImageView out;
	@FXML
	private ToggleGroup typeOutput;
	@FXML
	private Text display;
	@FXML
	private Region rgFm;
	@FXML
	private Label freqLabel;

	private ControllerVco vcoControl;

	private DoubleProperty fmX = new SimpleDoubleProperty(0);
	private DoubleProperty fmY = new SimpleDoubleProperty(0);

	private DoubleProperty outX = new SimpleDoubleProperty(0);
	private DoubleProperty outY = new SimpleDoubleProperty(0);

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
		vcoControl = new ControllerVco(freqLabel);
		octaveKnob.valueProperty().addListener((p, oldVal, newVal) ->
		vcoControl.handleViewOctaveChange((double) newVal, precisionKnob.getValue()));
		precisionKnob.valueProperty().addListener((p, oldVal, newVal) ->
		vcoControl.handleViewOctaveChange(octaveKnob.getValue(), (double) newVal));

		typeOutput.selectedToggleProperty().addListener((obs, oldVal, newVal) ->
		vcoControl.handleViewOutputTypeChange(((RadioButton)newVal).getText()));
		
		// Listener close VCO
		closeVco.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			cleanupPorts();
			vcoControl.handleViewClose();
			Pane parent = (Pane) paneVco.getParent();
			parent.getChildren().remove(paneVco);
		});
		
		// Choice base freq config + listener
		choiceBaseFreq.getItems().addAll("1 Hz", "32 Hz", "1 KHz");
		choiceBaseFreq.getSelectionModel().selectFirst();
		choiceBaseFreq.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
				switch(newVal) {
					case "1 Hz":
						vcoControl.handleViewBaseFreqChange(1.0);
						break;
					case "32 Hz":
						vcoControl.handleViewBaseFreqChange(32.0);
						break;
					case "1 KHz":
						vcoControl.handleViewBaseFreqChange(1000.0);
						break;
				}
		});

		addPort(fm, fmX, fmY);
		addPort(out, outX, outY);
	}

	/**
	 * Handles the click on the FM input port
	 */
	@FXML
	public void handleFmClick() {
		vcoControl.handleViewInputClick(fmX, fmY);
	}

	/**
	 * Handles the click on the output port
	 */
	@FXML
	public void handleOutputClick() {
		vcoControl.handleViewOutputClick(outX, outY);
	}

	@Override
	protected Pane getComponentRoot() {
		return paneVco;
	}

	@Override
	protected Module getConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}
