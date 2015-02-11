package fr.istic.groupimpl.synthesizer.seq;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ViewComponent;
import fr.istic.groupimpl.synthesizer.io.architecture.Module;
import fr.istic.groupimpl.synthesizer.util.Potentiometre;
import fr.istic.groupimpl.synthesizer.util.PotentiometreFactory;

public class ViewSeq extends ViewComponent implements Initializable {

	@FXML
	private BorderPane paneSeq;
	@FXML
	private ImageView closeSeq;
	@FXML
	private VBox screenSeqPane;
	@FXML
	private ImageView gate;
	@FXML
	private ImageView out;
	@FXML
	private GridPane gridSeq;

	private DoubleProperty gateX = new SimpleDoubleProperty(0);
	private DoubleProperty gateY = new SimpleDoubleProperty(0);
	private DoubleProperty outX = new SimpleDoubleProperty(0);
	private DoubleProperty outY = new SimpleDoubleProperty(0);

	private ControllerSeq controller;

	@Override
	public void initialize(URL location, ResourceBundle resource) {

		PotentiometreFactory potFact = PotentiometreFactory
				.getFactoryInstance();

		potFact.setRayon(25);
		potFact.setMinValue(-1);
		potFact.setMaxValue(1);
		potFact.setMajorTickUnit(1);
		potFact.setMinorTickUnit(0.1);

		for (int i = 0; i < ControllerSeq.NB_BUTTONS; i++) {
			final int indice = i;
			potFact.setTitle("" + (i + 1));
			Potentiometre potentiometre = potFact.getPotentiometre();

			gridSeq.add(potentiometre, i % 4, i / 2);

			potentiometre.valueProperty().addListener(
					(p, oldVal, newVal) -> controller.handleValueViewChange(
							indice, (double) newVal));

		}

		// Listener in & out
		// gate.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->
		// controller.handleViewGateClick(gateX, gateY));
		// out.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->
		// controller.handleViewOutputClick(outX, outY));

		// Listener close module
		closeSeq.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			cleanupPorts();
			controller.handleViewClose();
			Pane parent = (Pane) paneSeq.getParent();
			parent.getChildren().remove(paneSeq);
		});

		addPort(gate, gateX, gateY);
		addPort(out, outX, outY);
	}

	@Override
	protected Pane getComponentRoot() {
		return paneSeq;
	}

	@Override
	protected Module getConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Handles the click on the gate port
	 */
	@FXML
	public void handleGateClick() {
		controller.handleViewGateClick(gateX, gateY);
	}

	/**
	 * Handles the click on the output port
	 */
	@FXML
	public void handleOutputClick() {
		controller.handleViewOutputClick(outX, outY);
	}

	@Override
	protected ControllerComponent getController() {
		// TODO Auto-generated method stub
		return controller;
	}
}
