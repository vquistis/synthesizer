package fr.istic.groupimpl.synthesizer.oscilloscope;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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

public class ViewOscillo extends ViewComponent implements Initializable {

	@FXML private Pane rootModulePane;
	@FXML private GridPane top;
	@FXML private VBox screenScopePane;
	@FXML private ImageView in;
	@FXML private ImageView out;
	@FXML private Slider refreshPeriodSlider;
	
	private ControllerOscillo controller;


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
			cleanupPorts();			
			controller.handleViewClose();
			Pane parent = (Pane) rootModulePane.getParent();
			parent.getChildren().remove(rootModulePane);
		});
		
		addPort("oscillo_in", in);
		addPort("oscillo_out", out);
		
		scope.start();
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
		return "fxml/oscillo.fxml";
	}
}
