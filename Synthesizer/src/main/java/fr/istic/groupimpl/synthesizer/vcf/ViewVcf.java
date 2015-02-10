package fr.istic.groupimpl.synthesizer.vcf;

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
import fr.istic.groupimpl.synthesizer.util.DoubleStringConverter;
import fr.istic.groupimpl.synthesizer.util.Potentiometre;
import fr.istic.groupimpl.synthesizer.util.PotentiometreFactory;

public class ViewVcf extends ViewComponent implements Initializable {

	@FXML private Pane rootModulePane;
	@FXML private ImageView closeModuleFx;
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
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		addPort(input, inputX, inputY);
		addPort(fm, fmX, fmY);
		addPort(output, outputX, outputY);
		
		PotentiometreFactory knobFact = PotentiometreFactory.getFactoryInstance();
		knobFact.setNbSpins(1);
		knobFact.setRayon(32);
		
		knobFact.setMinValue(10);
		knobFact.setMaxValue(16384);
		knobFact.setValueDef(1024);
		Potentiometre knobCutoff = knobFact.getPotentiometre();
		knobCutoffPane.getChildren().add(1,knobCutoff);
		
		knobFact.setMinValue(0);
		knobFact.setMaxValue(4);
		knobFact.setValueDef(1);
		Potentiometre knobResonance = knobFact.getPotentiometre();
		knobResonancePane.getChildren().add(1,knobResonance);
		
		// Bind knob value and text field value
		StringConverter<Number> converter = new DoubleStringConverter();
		Bindings.bindBidirectional(valueCutoffFx.textProperty(), knobCutoff.valueProperty(), converter);
		Bindings.bindBidirectional(valueResonanceFx.textProperty(), knobResonance.valueProperty(), converter);

		// Creation du controller
		ControllerVcf controller = new ControllerVcf();

		// Listener parameters
		knobCutoff.valueProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewCutoffChange(newVal));
		knobResonance.valueProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewResonanceChange(newVal));
		
		// Listener input & output
		input.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> controller.handleViewInputClick("vcf_input", inputX, inputY));
		fm.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> controller.handleViewFmClick(fmX, fmY));
		output.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> controller.handleViewOutputClick("vcf_output", outputX, outputY));

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
}
