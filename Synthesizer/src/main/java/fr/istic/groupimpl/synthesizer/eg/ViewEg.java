package fr.istic.groupimpl.synthesizer.eg;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
 * The Class ViewEg : view of EG component.
 *
 * @author Team groupImpl
 */
public class ViewEg extends ViewComponent implements Initializable {

	/** The root module pane. */
	@FXML private Pane rootModulePane;
	
	/** The top. */
	@FXML private GridPane top;
	
	/** The knob attack pane. */
	@FXML private VBox knobAttackPane;
	
	/** The knob decay pane. */
	@FXML private VBox knobDecayPane;
	
	/** The knob sustain pane. */
	@FXML private VBox knobSustainPane;
	
	/** The knob release pane. */
	@FXML private VBox knobReleasePane;
	
	/** The value attack fx. */
	@FXML private TextField valueAttackFx;
	
	/** The value decay fx. */
	@FXML private TextField valueDecayFx;
	
	/** The value sustain fx. */
	@FXML private TextField valueSustainFx;
	
	/** The value release fx. */
	@FXML private TextField valueReleaseFx;
	
	/** The input. */
	@FXML private ImageView input;
	
	/** The output. */
	@FXML private ImageView output;

	/** The controller. */
	private ControllerEg controller;
	
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		((Label) top.lookup("#titleModule")).setText("EG");
		
		PotentiometreFactory knobFact = PotentiometreFactory.getFactoryInstance();
		knobFact.setMinValue(0);
		knobFact.setMaxValue(1);
		knobFact.setValueDef(0);
		knobFact.setNbSpins(1);
		knobFact.setRayon(32);
		
		// Add knob to view
		Potentiometre knobAttack = knobFact.getPotentiometre();
		knobAttackPane.getChildren().add(1,knobAttack);
		Potentiometre knobDecay = knobFact.getPotentiometre();
		knobDecayPane.getChildren().add(1,knobDecay);
		knobFact.setMaxValue(5);
		Potentiometre knobRelease = knobFact.getPotentiometre();
		knobReleasePane.getChildren().add(1,knobRelease);
		knobFact.setMinValue(-60);
		knobFact.setMaxValue(0);
		Potentiometre knobSustain = knobFact.getPotentiometre();
		knobSustainPane.getChildren().add(1,knobSustain);
		
		// Bind knob value and text field value
		StringConverter<Number> converter = new DoubleStringConverter();
		Bindings.bindBidirectional(valueAttackFx.textProperty(), knobAttack.valueProperty(), converter);
		Bindings.bindBidirectional(valueDecayFx.textProperty(), knobDecay.valueProperty(), converter);
		Bindings.bindBidirectional(valueSustainFx.textProperty(), knobSustain.valueProperty(), converter);
		Bindings.bindBidirectional(valueReleaseFx.textProperty(), knobRelease.valueProperty(), converter);

		// Creation du controller
		controller = new ControllerEg();

		// Listener parameters
		knobAttack.valueProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewAttackChange(newVal));
		knobDecay.valueProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewDecayChange(newVal));
		knobSustain.valueProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewSustainChange(newVal));
		knobRelease.valueProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewReleaseChange(newVal));
		
		// Listener close module
		top.lookup("#closeModuleFx").addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			cleanup();			
			controller.handleViewClose();
			Pane parent = (Pane) rootModulePane.getParent();
			parent.getChildren().remove(rootModulePane);
		});

		addPort("eg_input", input);
		addPort("eg_output", output);
		
		addParameters("knobAttack", () -> knobAttack.getValue(), (val) -> knobAttack.setValue(val));
		addParameters("knobDecay", () -> knobDecay.getValue(), (val) -> knobDecay.setValue(val));
		addParameters("knobSustain", () -> knobSustain.getValue(), (val) -> knobSustain.setValue(val));
		addParameters("knobRelease", () -> knobRelease.getValue(), (val) -> knobRelease.setValue(val));
	}

	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.ViewComponent#getComponentRoot()
	 */
	@Override
	protected Pane getComponentRoot() {
		return rootModulePane;
	}

	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.ViewComponent#getController()
	 */
	@Override
	protected ControllerComponent getController() {
		return controller;
	}

	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.ViewComponent#getFilename()
	 */
	@Override
	public String getFilename() {
		return "fxml/eg.fxml";
	}
}
