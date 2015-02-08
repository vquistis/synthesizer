package fr.istic.groupimpl.synthesizer.oscilloscope;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import fr.istic.groupimpl.synthesizer.component.ViewComponent;
import fr.istic.groupimpl.synthesizer.util.Oscilloscope;
import fr.istic.groupimpl.synthesizer.util.OscilloscopeFactory;

public class ViewOscillo extends ViewComponent implements Initializable {

	@FXML private Pane rootModulePane;
	@FXML private ImageView closeModuleFx;
	@FXML private VBox screenScopePane;
	@FXML private ImageView in;
	@FXML private ImageView out;
	

	private DoubleProperty inX = new SimpleDoubleProperty(0);
	private DoubleProperty inY = new SimpleDoubleProperty(0);
	private DoubleProperty outX = new SimpleDoubleProperty(0);
	private DoubleProperty outY = new SimpleDoubleProperty(0);

	@Override
	public void initialize(URL location, ResourceBundle resource) {

		// Creation du controller
		ControllerOscillo controller = new ControllerOscillo();
		
		OscilloscopeFactory scopeFact = OscilloscopeFactory.getFactoryInstance();
		scopeFact.setWidth(400);
		scopeFact.setHeight(200);
		scopeFact.setRefreshPeriod(1000);
		scopeFact.setCmdGetBuffer(()->{return controller.getbufferData();});
		
		
		Oscilloscope scope = scopeFact.getOscilloscope();
		screenScopePane.getChildren().add(scope);
		

		// Listener in
		in.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			controller.handleViewInputClick("oscillo_in", inX, inY);
		});
		
		// Listener out
		out.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			controller.handleViewOutputClick("oscillo_out", outX, outY);
		});
		
		// Listener close module
		closeModuleFx.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			cleanupPorts();			
			controller.handleViewClose();
			Pane parent = (Pane) rootModulePane.getParent();
			parent.getChildren().remove(rootModulePane);
		});
		
		addPort(in, inX, inY);
		addPort(out, outX, outY);
		
		scope.start();
	}

	@Override
	protected Pane getComponentRoot() {
		return rootModulePane;
	}
}
