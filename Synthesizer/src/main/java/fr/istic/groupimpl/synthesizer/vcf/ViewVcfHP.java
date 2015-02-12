package fr.istic.groupimpl.synthesizer.vcf;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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

public class ViewVcfHP extends ViewComponent implements Initializable {

	@FXML private Pane rootModulePane;
	@FXML private GridPane top;
	@FXML private VBox knobCutoffPane;
	@FXML private VBox knobResonancePane;
	@FXML private TextField valueCutoffFx;
	@FXML private TextField valueResonanceFx;
	@FXML private ImageView input;
	@FXML private ImageView fm;
	@FXML private ImageView output;

	private DoubleProperty inputX = new SimpleDoubleProperty(0);
	private DoubleProperty inputY = new SimpleDoubleProperty(0);
	private DoubleProperty fmX = new SimpleDoubleProperty(0);
	private DoubleProperty fmY = new SimpleDoubleProperty(0);
	private DoubleProperty outputX = new SimpleDoubleProperty(0);
	private DoubleProperty outputY = new SimpleDoubleProperty(0);
	private ControllerVcf controller;
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		((Label) top.lookup("#titleModule")).setText("VCF - HP12");
		
		addPort(input, inputX, inputY);
		addPort(fm, fmX, fmY);
		addPort(output, outputX, outputY);
		
		PotentiometreFactory knobFact = PotentiometreFactory.getFactoryInstance();
		knobFact.setRayon(32);

		knobFact.setMinValue(10);
		knobFact.setMaxValue(22000);
		knobFact.setValueDef(22000);
		knobFact.setNbSpins(3);
		Potentiometre knobCutoff = knobFact.getPotentiometre();
		knobCutoffPane.getChildren().add(1,knobCutoff);
		
		knobFact.setMinValue(0);
		knobFact.setMaxValue(10);
		knobFact.setValueDef(1);
		knobFact.setNbSpins(1);
		Potentiometre knobResonance = knobFact.getPotentiometre();
		knobResonancePane.getChildren().add(1,knobResonance);
		
		// Bind knob value and text field value
		StringConverter<Number> converter = new DoubleStringConverter();
		Bindings.bindBidirectional(valueCutoffFx.textProperty(), knobCutoff.valueProperty(), converter);
		Bindings.bindBidirectional(valueResonanceFx.textProperty(), knobResonance.valueProperty(), converter);

		// Creation du controller
		controller = new ControllerVcf(ModelVcf.Type.HP12);

		// Listener parameters
		knobCutoff.valueProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewCutoffChange(newVal));
		knobResonance.valueProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewResonanceChange(newVal));
		
		// Listener input & output
		input.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> controller.handleViewInputClick(inputX, inputY));
		fm.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> controller.handleViewFmClick(fmX, fmY));
		output.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> controller.handleViewOutputClick(outputX, outputY));

		// Listener close module
		top.lookup("#closeModuleFx").addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
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
	protected ControllerComponent getController() {
		// TODO Auto-generated method stub
		return controller;
	}
}
