package fr.istic.groupimpl.synthesizer.vcf;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import fr.istic.groupimpl.synthesizer.component.ViewComponent;
import fr.istic.groupimpl.synthesizer.util.DoubleStringConverter;
import fr.istic.groupimpl.synthesizer.util.Potentiometre;

/**
 * View vcf module.
 *
 * @author Team GroupImpl
 */
public abstract class ViewVcf extends ViewComponent {
	
	/** The root module pane. */
	@FXML private Pane rootModulePane;
	
	/** The top. */
	@FXML private GridPane top;
	
	/** The content. */
	@FXML private HBox content;
	
	/** The port. */
	@FXML private HBox port;
	
	/**
	 * Configurate.
	 *
	 * @param title the title
	 * @param controller the controller
	 * @param knobCutoff the knob cutoff
	 * @param knobResonance the knob resonance
	 */
	public void configurate(String title, ControllerVcf controller, Potentiometre knobCutoff, Potentiometre knobResonance) {
		((Label) top.lookup("#titleModule")).setText(title);
		
		((VBox) content.lookup("#knobCutoffPane")).getChildren().add(1,knobCutoff);
		((VBox) content.lookup("#knobResonancePane")).getChildren().add(1,knobResonance);
		
		// Bind knob value and text field value
		StringConverter<Number> converter = new DoubleStringConverter();
		Bindings.bindBidirectional(((TextField) content.lookup("#valueCutoffFx")).textProperty(), knobCutoff.valueProperty(), converter);
		Bindings.bindBidirectional(((TextField) content.lookup("#valueResonanceFx")).textProperty(), knobResonance.valueProperty(), converter);

		// Listener parameters
		knobCutoff.valueProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewCutoffChange(newVal));
		knobResonance.valueProperty().addListener((obsVal, oldVal, newVal) -> controller.handleViewResonanceChange(newVal));

		// Listener close module
		top.lookup("#closeModuleFx").addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			cleanup();			
			controller.handleViewClose();
			Pane parent = (Pane) rootModulePane.getParent();
			parent.getChildren().remove(rootModulePane);
		});

		// Initialisation du model
		controller.handleViewCutoffChange(knobCutoff.getValue());
		controller.handleViewResonanceChange(knobResonance.getValue());
		
		addPort("vcf_input",port.lookup("#input"));
		addPort("vcf_fm",port.lookup("#fm"));
		addPort("vcf_output",port.lookup("#output"));

		addParameters("knobCutoff", () -> knobCutoff.getValue(), (val) -> knobCutoff.setValue(val));
		addParameters("knobResonance", () -> knobResonance.getValue(), (val) -> knobResonance.setValue(val));
	}
	
	/**
	 * @see fr.istic.groupimpl.synthesizer.component.ViewComponent#getComponentRoot()
	 */
	@Override
	protected Pane getComponentRoot() {
		return rootModulePane;
	}
}
