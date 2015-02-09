package fr.istic.groupimpl.synthesizer.vca;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import fr.istic.groupimpl.synthesizer.component.ViewComponent;
import fr.istic.groupimpl.synthesizer.util.Potentiometre;
import fr.istic.groupimpl.synthesizer.util.PotentiometreFactory;

/**
 * @authors GroupImpl
 * VCA module - JavaFX Controller
 */
public class ViewVca extends ViewComponent implements Initializable {

	@FXML
	private BorderPane paneVca;
	@FXML
	private ImageView closeVca;
	@FXML
	private VBox decibelPane;

	@FXML
	private ImageView am;
	@FXML
	private ImageView out;
	@FXML 
	private ImageView input;

	@FXML
	private Label dbValue;

	private ControllerVca vcaControl;

	private DoubleProperty fmX = new SimpleDoubleProperty(0);
	private DoubleProperty fmY = new SimpleDoubleProperty(0);
	private DoubleProperty outX = new SimpleDoubleProperty(0);
	private DoubleProperty outY = new SimpleDoubleProperty(0);

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		PotentiometreFactory pf = PotentiometreFactory.getFactoryInstance();

		// Octave knob
		pf.setMinValue(-5);
		pf.setMaxValue(+5);
		pf.setDiscret(true);
		pf.setShowTickMarks(true);
		pf.setShowTickLabels(true);
		pf.setMajorTickUnit(1);
		pf.setNbSpins(0.88);
		pf.setRayon(32);
		pf.setValueDef(0);		
		Potentiometre octaveKnob = pf.getPotentiometre();
		decibelPane.getChildren().add(octaveKnob);

		// Precision knob
		pf.setNbSpins(0.80);
		pf.setDiscret(false);
		pf.setMinValue(-1);
		pf.setMaxValue(1);

		Potentiometre precisionKnob = pf.getPotentiometre();

		// VcaController creation and listeners on knob values
		vcaControl = new ControllerVca(dbValue);
		octaveKnob.valueProperty().addListener((p, oldVal, newVal) ->
		vcaControl.handleViewOctaveChange((double) newVal, precisionKnob.getValue()));
		precisionKnob.valueProperty().addListener((p, oldVal, newVal) ->
		vcaControl.handleViewOctaveChange(octaveKnob.getValue(), (double) newVal));

		
		// Listener close VCA
		closeVca.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			cleanupPorts();
			vcaControl.handleViewClose();
			Pane parent = (Pane) paneVca.getParent();
//			parent.getChildren().remove(paneVca);
		});

		addPort(am, fmX, fmY);
		addPort(out, outX, outY);
	}

	/**
	 * Handles the click on the FM input port
	 */
	public void handleFmClick() {
		vcaControl.handleViewInputClick("vca_inputFm", fmX, fmY);
	}

	/**
	 * Handles the click on the output port
	 */
	public void handleOutputClick() {
		vcaControl.handleViewOutputClick("vca_output", outX, outY);
	}

	/**
	 * Handles the click on the close button
	 */
	public void handleCloseClick() {
		vcaControl.handleViewClose();
	}

	@Override
	protected Pane getComponentRoot() {
		return paneVca;
	}

}
