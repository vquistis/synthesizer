package fr.istic.groupimpl.synthesizer.vca;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
	private VBox voltPane;

	/** The am. */
	@FXML
	private ImageView am;
	
	/** The out. */
	@FXML
	private ImageView out;
	
	/** The input. */
	@FXML 
	private ImageView input;

	/** The vca control. */
	private ControllerVca vcaControl;
	
	/** The input x. */
	private DoubleProperty inputX = new SimpleDoubleProperty(0);
	
	/** The input y. */
	private DoubleProperty inputY = new SimpleDoubleProperty(0);

	/** The fm x. */
	private DoubleProperty amX = new SimpleDoubleProperty(0);
	
	/** The fm y. */
	private DoubleProperty amY = new SimpleDoubleProperty(0);
	
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

		// knob
		pf.setMinValue(-5);
		pf.setMaxValue(+5);
		pf.setDiscret(true);
		pf.setShowTickMarks(true);
		pf.setShowTickLabels(true);
		pf.setMajorTickUnit(1);
		pf.setNbSpins(0.88);
		pf.setRayon(32);
		pf.setValueDef(0);		
		Potentiometre amplitudeKnod = pf.getPotentiometre();
		voltPane.getChildren().add(amplitudeKnod);

		// Precision knob
		pf.setNbSpins(0.80);
		pf.setDiscret(false);
		pf.setMinValue(-1);
		pf.setMaxValue(1);

		// VcaController creation and listeners on knob values
		vcaControl = new ControllerVca();

		amplitudeKnod.valueProperty().addListener((p, oldVal, newVal) ->
		vcaControl.handleViewVoltChange((double) newVal));		
		// Listener close VCA
		closeVca.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			cleanupPorts();
			vcaControl.handleViewClose();
			Pane parent = (Pane) paneVca.getParent();
			parent.getChildren().remove(paneVca);
		});
		
		input.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			vcaControl.handleViewInputClick("vca_input", inputX, inputY);
		});

		addPort(input, inputX, inputY);
		addPort(am, amX, amY);
		addPort(out, outX, outY);
		
	}

	/**
	 * Handles the click on the FM input port.
	 */
	public void handleamClick() {
		vcaControl.handleViewInputClick("vca_inputam", amX, amY);
	}

	/**
	 * Handles the click on the output port.
	 */
	public void handleOutputClick() {
		vcaControl.handleViewOutputClick("vca_output", outX, outY);
	}


	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.ViewComponent#getComponentRoot()
	 */
	@Override
	protected Pane getComponentRoot() {
		return paneVca;
	}

}
