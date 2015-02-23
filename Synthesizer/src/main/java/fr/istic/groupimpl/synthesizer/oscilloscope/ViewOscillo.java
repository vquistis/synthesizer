package fr.istic.groupimpl.synthesizer.oscilloscope;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import fr.istic.groupimpl.synthesizer.component.ControllerComponent;
import fr.istic.groupimpl.synthesizer.component.ViewComponent;
import fr.istic.groupimpl.synthesizer.util.Oscilloscope;
import fr.istic.groupimpl.synthesizer.util.OscilloscopeFactory;

/**
 * View of oscilloscope module.
 *
 * @author Team GroupImpl
 */
public class ViewOscillo extends ViewComponent implements Initializable {

	/** The root module pane. */
	@FXML private Pane rootModulePane;
	
	/** The top pane. */
	@FXML private GridPane top;
	
	/** The screen scope pane. */
	@FXML private VBox screenScopePane;
	
	/** The in. */
	@FXML private ImageView in;
	
	/** The out. */
	@FXML private ImageView out;
	
	/** The refresh period slider. */
	@FXML private Slider refreshPeriodSlider;
	
	/** The controller. */
	private ControllerOscillo controller;


	/**
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		((Label) top.lookup("#titleModule")).setText("Oscilloscope");
		
		OscilloscopeFactory scopeFact = OscilloscopeFactory.getFactoryInstance();
		scopeFact.setWidth(screenScopePane.getPrefWidth()-20);
		scopeFact.setHeight(screenScopePane.getPrefHeight()-20);
		scopeFact.setRefreshPeriod(1000);
		scopeFact.setCmdGetBuffer(()->{return controller.getbufferData();});
		
		
		Oscilloscope scope = scopeFact.getOscilloscope();
		screenScopePane.getChildren().add(scope);
		
		// Creation du controller
		controller = new ControllerOscillo(scope);
		
		refreshPeriodSlider.valueProperty().addListener( (obsVal, oldVal, newVal) -> controller.handleRefreshPeriodViewChange(newVal));
		// Listener mute);

		// Listener close module
		top.lookup("#closeModuleFx").addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			cleanup();			
			controller.handleViewClose();
			Pane parent = (Pane) rootModulePane.getParent();
			parent.getChildren().remove(rootModulePane);
		});
		
		addPort("oscillo_in", in);
		addPort("oscillo_out", out);
		
		addParameters("refreshPeriodSlider", ()-> {return  refreshPeriodSlider.getValue();}, (val)-> refreshPeriodSlider.setValue(val));
		scope.start();
	}

	/**
	 * @see fr.istic.groupimpl.synthesizer.component.ViewComponent#getComponentRoot()
	 */
	@Override
	protected Pane getComponentRoot() {
		return rootModulePane;
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
		return "fxml/oscillo.fxml";
	}
}
