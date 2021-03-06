package fr.istic.groupimpl.synthesizer.seq;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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

/**
 * View of Seq module.
 *
 * @author Team GroupImpl
 */
public class ViewSeq extends ViewComponent implements Initializable {

	/** The pane seq. */
	@FXML private BorderPane paneSeq;
	
	/** The top. */
	@FXML private GridPane top;
	
	/** The screen seq pane. */
	@FXML private VBox screenSeqPane;
	
	/** The gate. */
	@FXML private ImageView gate;
	
	/** The out. */
	@FXML private ImageView out;
	
	/** The grid seq. */
	@FXML private GridPane gridSeq;

	/** The controller. */
	private ControllerSeq controller;

	/**
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		((Label) top.lookup("#titleModule")).setText("SEQ");
		
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
					(p, oldVal, newVal) ->{
						double v=(double) newVal;
						if ( v > 1. ||  v <-1.) v = 0;
						controller.handleValueViewChange(indice, v);});
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
			
			addParameters("knob"+i, () -> knob.getValue(), (val) -> knob.setValue(val));
		}

		// Listener close module
		top.lookup("#closeModuleFx").addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			cleanup();
			controller.handleViewClose();
			Pane parent = (Pane) paneSeq.getParent();
			parent.getChildren().remove(paneSeq);
		});

		addPort("sequencer_gate",gate);
		addPort("sequencer_out",out);
	}

	/**
	 * @see fr.istic.groupimpl.synthesizer.component.ViewComponent#getComponentRoot()
	 */
	@Override
	protected Pane getComponentRoot() {
		return paneSeq;
	}
	
	/**
	 * Handles the click on the reset button.
	 */
	@FXML
	public void handleDebutClicked() {
		controller.handleViewBeginClicked();
	}

	/**
	 * @see fr.istic.groupimpl.synthesizer.component.ViewComponent#getController()
	 */
	@Override
	protected ControllerComponent getController() {
		return controller;
	}

	/**
	 * @see fr.istic.groupimpl.synthesizer.component.ViewComponent#getFilename()
	 */
	@Override
	public String getFilename() {
		return "fxml/seq.fxml";
	}
}
