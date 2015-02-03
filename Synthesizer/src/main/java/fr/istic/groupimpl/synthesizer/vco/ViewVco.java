package fr.istic.groupimpl.synthesizer.vco;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import fr.istic.groupimpl.synthesizer.component.IViewComponent;
import fr.istic.groupimpl.synthesizer.component.Port;
import fr.istic.groupimpl.synthesizer.util.Potentiometre;

public class ViewVco implements IViewComponent, Initializable {

	@FXML
	private BorderPane paneVco;
	@FXML
	private VBox knobOctavePane;
	@FXML
	private VBox knobFreqPane;
	@FXML
	private ImageView fm;
	@FXML
	private ImageView square;
	@FXML
	private ImageView triangle;
	@FXML
	private ImageView sawTooth;
	@FXML
	private Text display;
	@FXML
	private Region rgFm;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Potentiometre octaveKnob = new Potentiometre("");
		octaveKnob.setMin(-32);
		octaveKnob.setMax(0);
		knobOctavePane.getChildren().add(octaveKnob);
	   	
	   	Potentiometre precisionKnob = new Potentiometre("");
	   	precisionKnob.setMin(-1);
	   	precisionKnob.setMax(1);
	   	knobFreqPane.getChildren().add(precisionKnob);
	   	
	   	ControllerVco vcoControl = new ControllerVco();
	   	octaveKnob.valueProperty().addListener((p, newVal, oldVal) ->
	   		vcoControl.handleViewOctaveChange((double) newVal, precisionKnob.getValue()));
	   	precisionKnob.valueProperty().addListener((p, newVal, oldVal) ->
   			vcoControl.handleViewOctaveChange(octaveKnob.getValue(), (double) newVal));
	}

	@Override
	public List<Port> getAllPorts() {
		// TODO Auto-generated method stub
		return null;
	}

}
