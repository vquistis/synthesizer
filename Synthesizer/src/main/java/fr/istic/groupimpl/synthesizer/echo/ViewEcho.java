package fr.istic.groupimpl.synthesizer.echo;

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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ViewComponent;
import fr.istic.groupimpl.synthesizer.util.DoubleStringConverter;
import fr.istic.groupimpl.synthesizer.util.Potentiometre;
import fr.istic.groupimpl.synthesizer.util.PotentiometreFactory;

/**
 * @authors GroupImpl
 * ECHO module - JavaFX Controller
 */
public class ViewEcho extends ViewComponent implements Initializable {

	@FXML
	private BorderPane paneEcho;
	/** The top. */
	@FXML private GridPane top;
	@FXML
	private VBox knobPeriod;
	@FXML
	private VBox knobAttenuation;
	@FXML
	private TextField tfPeriod;
	@FXML
	private TextField tfAttenuation;
	@FXML
	private ImageView inEcho;
	@FXML
	private ImageView outEcho;

	private ControllerEcho echoController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		((Label) top.lookup("#titleModule")).setText("Echo chamber");
		
		// EchoController creation and listeners on knob values
		echoController = new ControllerEcho();

		
		PotentiometreFactory pf = PotentiometreFactory.getFactoryInstance();

		// Period knob
		pf.setMinValue(0);
		pf.setMaxValue(echoController.getMaxPeriod());
		pf.setDiscret(false);
		pf.setNbSpins(2);
		pf.setRayon(32);
		pf.setValueDef(1.);		
		Potentiometre periodKnob = pf.getPotentiometre();
		knobPeriod.getChildren().add(periodKnob);

		// Attenuation knob
		pf.setNbSpins(2);
		pf.setMinValue(-24);
		pf.setMaxValue(0);
		pf.setValueDef(-6);

		Potentiometre attenuationKnob = pf.getPotentiometre();
		knobAttenuation.getChildren().add(attenuationKnob);
		
		// to Bind knob value and text field values
		StringConverter<Number> converter = new DoubleStringConverter();

		periodKnob.valueProperty().addListener(
				(p, oldVal, newVal) ->{
					echoController.handlePeriodViewChange( newVal );});
		attenuationKnob.valueProperty().addListener(
				(p, oldVal, newVal) ->{
					echoController.handleAttenuationViewChange( newVal );});

		Bindings.bindBidirectional(tfPeriod.textProperty(), periodKnob.valueProperty(), converter);
		Bindings.bindBidirectional(tfAttenuation.textProperty(), attenuationKnob.valueProperty(), converter);
		
		addParameters("knobEchoPeriod", () -> periodKnob.getValue(), (val) -> periodKnob.setValue(val));
		addParameters("knobEchoAttenuation", () -> attenuationKnob.getValue(), (val) -> attenuationKnob.setValue(val));

		
		// Listener close module
		top.lookup("#closeModuleFx").addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			cleanup();			
			echoController.handleViewClose();
			Pane parent = (Pane) paneEcho.getParent();
			parent.getChildren().remove(paneEcho);
		});
		

		addPort("echo_in",inEcho);
		addPort("echo_out",outEcho);		
	}

	@Override
	protected Pane getComponentRoot() {
		return paneEcho;
	}

	@Override
	protected ControllerComponent getController() {
		return echoController;
	}

	@Override
	public String getFilename() {
		return "fxml/echo.fxml";
	}

}
