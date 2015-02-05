package fr.istic.groupimpl.synthesizer.vco;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
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
import fr.istic.groupimpl.synthesizer.component.CopyOfIViewComponent;
import fr.istic.groupimpl.synthesizer.util.Potentiometre;
import fr.istic.groupimpl.synthesizer.util.PotentiometreFactory;

/**
 * @authors GroupImpl
 * VCO module - JavaFX Controller
 */
public class ViewVco extends CopyOfIViewComponent implements Initializable {

	@FXML
	private BorderPane paneVco;
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
		pf.setMaxValue(8);
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

//		ChangeListener posChangeListener = ((a,b,c) -> {
//			Log.getInstance().debug("PARENT CHANGE : Bounds = " + paneVco.boundsInParentProperty().get());
//			fmX.set(computeFmX());
//			fmY.set(computeFmY());
//			outX.set(computeOutX());
//			outY.set(computeOutY());
//		});
		
		// Listener close VCO
		closeVco.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
//			paneVco.parentProperty().removeListener(posChangeListener);
//			paneVco.boundsInParentProperty().removeListener(posChangeListener);

			//---
			cleanupPorts();
			//---
			vcoControl.handleViewClose();
			Pane parent = (Pane) paneVco.getParent();
			parent.getChildren().remove(paneVco);
		});

		//---
		addPort(fm, fmX, fmY);
		addPort(out, outX, outY);
		//---
//		paneVco.parentProperty().addListener(posChangeListener);
//		paneVco.boundsInParentProperty().addListener(posChangeListener);
	}

	private double computeFmX() {
		Bounds b1 = fm.getParent().localToParent(fm.getBoundsInParent());
		Bounds b2 = fm.getParent().getParent().localToParent(b1);
		Bounds b3 = fm.getParent().getParent().getParent().localToParent(b2);
		Bounds b4 = fm.getParent().getParent().getParent().getParent().localToParent(b3);
		return b4.getMinX() + b4.getWidth() / 2;
	}

	private double computeFmY() {
		Bounds b1 = fm.getParent().localToParent(fm.getBoundsInParent());
		Bounds b2 = fm.getParent().getParent().localToParent(b1);
		Bounds b3 = fm.getParent().getParent().getParent().localToParent(b2);
		Bounds b4 = fm.getParent().getParent().getParent().getParent().localToParent(b3);
		return b4.getMinY() + b4.getHeight() / 2;
	}

	private double computeOutX() {
		Bounds b1 = out.getParent().localToParent(out.getBoundsInParent());
		Bounds b2 = out.getParent().getParent().localToParent(b1);
		Bounds b3 = out.getParent().getParent().getParent().localToParent(b2);
		Bounds b4 = out.getParent().getParent().getParent().getParent().localToParent(b3);
		return b4.getMinX() + b4.getWidth() / 2;
	}

	private double computeOutY() {
		Bounds b1 = out.getParent().localToParent(out.getBoundsInParent());
		Bounds b2 = out.getParent().getParent().localToParent(b1);
		Bounds b3 = out.getParent().getParent().getParent().localToParent(b2);
		Bounds b4 = out.getParent().getParent().getParent().getParent().localToParent(b3);
		return b4.getMinY() + b4.getHeight() / 2;
	}

	/**
	 * Handles the click on the FM input port
	 */
	public void handleFmClick() {
		vcoControl.handleViewInputClick("vco_inputFm", fmX, fmY);
	}

	/**
	 * Handles the click on the output port
	 */
	public void handleOutputClick() {
		vcoControl.handleViewOutputClick("vco_output", outX, outY);
	}

	/**
	 * Handles the click on the close button
	 */
	public void handleCloseClick() {
		vcoControl.handleViewClose();
	}

	@Override
	protected Pane getComponentRoot() {
		return paneVco;
	}

}
