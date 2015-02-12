package fr.istic.groupimpl.synthesizer.seq;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ViewComponent;
import fr.istic.groupimpl.synthesizer.util.DoubleDuodecimalStringConverter;
import fr.istic.groupimpl.synthesizer.util.DoubleStringConverter;
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
		
		controller = new ControllerSeq();
		
		PotentiometreFactory potFact = PotentiometreFactory
				.getFactoryInstance();

		potFact.setRayon(25);
		potFact.setMinValue(-1);
		potFact.setMaxValue(1);
		potFact.setMajorTickUnit(1);
		potFact.setNbSpins(0.8);
		potFact.setMinorTickUnit(1./12.);
		potFact.setShowTickLabels(true);
		potFact.setShowTickMarks(true);
		
		// to Bind knob value and text field values
		StringConverter<Number> converter = new DoubleStringConverter();
		StringConverter<Number> converter12 = new DoubleDuodecimalStringConverter();

		for (int i = 0; i < ControllerSeq.NB_BUTTONS; i++) {
			final int indice = i;

			Potentiometre knob = potFact.getPotentiometre();

			gridSeq.add(knob, i , 0);

			knob.valueProperty().addListener(
					(p, oldVal, newVal) -> controller.handleValueViewChange(
							indice, (double) newVal));
			HBox rg1 = new HBox();
			HBox rg2 = new HBox();
			TextField tf1 = new TextField("0");
			TextField tf2 = new TextField("0");
			tf1.setPrefWidth(60);
			tf2.setPrefWidth(60);
			tf1.setMaxWidth(60);
			tf2.setMaxWidth(60);
			tf1.setTranslateX(10);
			tf2.setTranslateX(10);
			tf2.setStyle("-fx-text-fill: blue ;");
			rg1.getChildren().add(tf1);
			rg2.getChildren().add(tf2);
			gridSeq.add(rg1,i,1);
			gridSeq.add(rg2,i,2);
						
			Bindings.bindBidirectional(tf1.textProperty(), knob.valueProperty(), converter);
			Bindings.bindBidirectional(tf2.textProperty(), knob.valueProperty(), converter12);

		}

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
	/**
	 * Handles the click on the reset button
	 */
	@FXML
	public void handleDebutClicked() {
		controller.handleViewBeginClicked();
	}

	@Override
	protected ControllerComponent getController() {
		return controller;
	}

	@Override
	public String getFilename() {
		return "fxml/seq.fxml";
	}
}
