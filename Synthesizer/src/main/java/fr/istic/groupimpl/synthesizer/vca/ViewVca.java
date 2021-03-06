package fr.istic.groupimpl.synthesizer.vca;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ViewComponent;
import fr.istic.groupimpl.synthesizer.util.Potentiometre;
import fr.istic.groupimpl.synthesizer.util.PotentiometreFactory;

/**
 * View of vca module.
 *
 * @author GroupImpl
 */
public class ViewVca extends ViewComponent implements Initializable {

	/** The pane vca. */
	@FXML private BorderPane paneVca;
	
	/** The top. */
	@FXML private GridPane top;
	
	/** The volt pane. */
	@FXML private VBox voltPane;
	
	/** The am. */
	@FXML private ImageView am;
	
	/** The out. */
	@FXML private ImageView out;
	
	/** The input. */
	@FXML private ImageView input;

	/** The vca control. */
	private ControllerVca vcaControl;

	/**
	 * Initializes the controller class.
	 * This method is automatically called after the FXML file has been loaded. It creates a new view and set all the button with new created buttons.
	 *
	 * @param location the location
	 * @param resources the resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		((Label) top.lookup("#titleModule")).setText("VCA");

		PotentiometreFactory pf = PotentiometreFactory.getFactoryInstance();

		// knob
		pf.setMinValue(-5);
		pf.setMaxValue(+5);
		pf.setShowTickMarks(true);
		pf.setShowTickLabels(true);
		pf.setMajorTickUnit(1);
		pf.setNbSpins(0.88);
		pf.setRayon(32);
		pf.setValueDef(0);		
		Potentiometre amplitudeKnod = pf.getPotentiometre();
		amplitudeKnod.setPadding(new Insets(50,0,50,0));
		voltPane.getChildren().add(1,amplitudeKnod);

		// VcaController creation and listeners on knob values
		vcaControl = new ControllerVca();

		amplitudeKnod.valueProperty().addListener((p, oldVal, newVal) ->
		vcaControl.handleViewVoltChange((double) newVal));		
		// Listener close VCA
		top.lookup("#closeModuleFx").addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			cleanup();
			vcaControl.handleViewClose();
			Pane parent = (Pane) paneVca.getParent();
			parent.getChildren().remove(paneVca);
		});

		addPort("vca_input",input);
		addPort("vca_inputam",am);
		addPort("vca_output",out);
		
		addParameters("amplitudeKnod", () -> amplitudeKnod.getValue(), (val) -> amplitudeKnod.setValue(val));
	}

	/**
	 * @see fr.istic.groupimpl.synthesizer.component.ViewComponent#getComponentRoot()
	 */
	@Override
	protected Pane getComponentRoot() {
		return paneVca;
	}


	/**
	 * @see fr.istic.groupimpl.synthesizer.component.ViewComponent#getController()
	 */
	@Override
	protected ControllerComponent getController() {
		return vcaControl;
	}

	/**
	 * @see fr.istic.groupimpl.synthesizer.component.ViewComponent#getFilename()
	 */
	@Override
	public String getFilename() {
		return "fxml/vca.fxml";
	}

}
