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
import fr.istic.groupimpl.synthesizer.out.ControllerOut;
import fr.istic.groupimpl.synthesizer.util.Potentiometre;
import fr.istic.groupimpl.synthesizer.util.PotentiometreFactory;

/**
 * The Class ViewVca.
 *
 * @authors GroupImpl
 * VCA module - JavaFX Controller
 */
public class ViewVca extends ViewComponent implements Initializable {

	/** The pane vca. */
	@FXML
	private BorderPane paneVca;
	
	/** The close vca. */
	@FXML
	private ImageView closeVca;
	
	/** The decibel pane. */
	@FXML
	private VBox decibelPane;

	/** The am. */
	@FXML
	private ImageView am;
	
	/** The out. */
	@FXML
	private ImageView out;
	
	/** The input. */
	@FXML 
	private ImageView input;

	/** The db value. */
	@FXML
	private Label dbValue;

	/** The vca control. */
	private ControllerVca vcaControl;
	
	/** The input x. */
	private DoubleProperty inputX = new SimpleDoubleProperty(0);
	
	/** The input y. */
	private DoubleProperty inputY = new SimpleDoubleProperty(0);

	/** The fm x. */
	private DoubleProperty fmX = new SimpleDoubleProperty(0);
	
	/** The fm y. */
	private DoubleProperty fmY = new SimpleDoubleProperty(0);
	
	/** The out x. */
	private DoubleProperty outX = new SimpleDoubleProperty(0);
	
	/** The out y. */
	private DoubleProperty outY = new SimpleDoubleProperty(0);

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
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
			parent.getChildren().remove(paneVca);
		});
		
		// Creation du controller
		ControllerOut controller = new ControllerOut();
		input.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			controller.handleViewInputClick("vca_input", inputX, inputY);
		});

		addPort(am, fmX, fmY);
		addPort(out, outX, outY);
		addPort(input, inputX, inputY);
	}

	/**
	 * Handles the click on the FM input port.
	 */
	public void handleFmClick() {
		vcaControl.handleViewInputClick("vca_inputFm", fmX, fmY);
	}

	/**
	 * Handles the click on the output port.
	 */
	public void handleOutputClick() {
		vcaControl.handleViewOutputClick("vca_output", outX, outY);
	}

	/**
	 * Handles the click on the close button.
	 */
	public void handleCloseClick() {
		vcaControl.handleViewClose();
	}

	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.ViewComponent#getComponentRoot()
	 */
	@Override
	protected Pane getComponentRoot() {
		return paneVca;
	}

}
