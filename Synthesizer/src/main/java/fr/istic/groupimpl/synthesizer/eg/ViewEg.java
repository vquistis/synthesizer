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

public class ViewEg extends ViewComponent implements Initializable {

	@FXML private Pane rootModulePane;
	@FXML private GridPane top;
	@FXML private VBox knobAttackPane;
	@FXML private VBox knobDecayPane;
	@FXML private VBox knobSustainPane;
	@FXML private VBox knobReleasePane;
	@FXML private TextField valueAttackFx;
	@FXML private TextField valueDecayFx;
	@FXML private TextField valueSustainFx;
	@FXML private TextField valueReleaseFx;
	@FXML private ImageView input;
	@FXML private ImageView output;

	private ControllerEg controller;
	
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
			cleanupPorts();			
			controller.handleViewClose();
			Pane parent = (Pane) rootModulePane.getParent();
			parent.getChildren().remove(rootModulePane);
		});

		addPort("eg_input", input);
		addPort("eg_output", output);
	}

	@Override
	protected Pane getComponentRoot() {
		return rootModulePane;
	}

	@Override
	protected ControllerComponent getController() {
		return controller;
	}

	@Override
	public String getFilename() {
		return "fxml/eg.fxml";
	}
}
