package fr.istic.groupimpl.synthesizer.eg;

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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import fr.istic.groupimpl.synthesizer.component.ViewComponent;
import fr.istic.groupimpl.synthesizer.io.architecture.Module;
import fr.istic.groupimpl.synthesizer.util.DoubleStringConverter;
import fr.istic.groupimpl.synthesizer.util.Potentiometre;
import fr.istic.groupimpl.synthesizer.util.PotentiometreFactory;

public class ViewEg extends ViewComponent implements Initializable {

	@FXML private Pane rootModulePane;
	@FXML private ImageView closeModuleFx;
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

	private DoubleProperty inputX = new SimpleDoubleProperty(0);
	private DoubleProperty inputY = new SimpleDoubleProperty(0);
	private DoubleProperty outputX = new SimpleDoubleProperty(0);
	private DoubleProperty outputY = new SimpleDoubleProperty(0);
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		addPort(input, inputX, inputY);
		addPort(output, outputX, outputY);
		
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
		ControllerEg controller = new ControllerEg();

		// Listener parameters
		knobAttack.valueProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewAttackChange(newVal));
		knobDecay.valueProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewDecayChange(newVal));
		knobSustain.valueProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewSustainChange(newVal));
		knobRelease.valueProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewReleaseChange(newVal));
		
		// Listener input & output
		input.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> controller.handleViewInputClick(inputX, inputY));
		output.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> controller.handleViewOutputClick(outputX, outputY));

		// Listener close module
		closeModuleFx.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			cleanupPorts();			
			controller.handleViewClose();
			Pane parent = (Pane) rootModulePane.getParent();
			parent.getChildren().remove(rootModulePane);
		});
	}

	@Override
	protected Pane getComponentRoot() {
		return rootModulePane;
	}

	@Override
	protected Module getConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}
}
