package fr.istic.groupimpl.synthesizer.vco;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import fr.istic.groupimpl.synthesizer.component.IViewComponent;
import fr.istic.groupimpl.synthesizer.util.Potentiometre;
import fr.istic.groupimpl.synthesizer.util.PotentiometreFactory;

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
		
		PotentiometreFactory pf = PotentiometreFactory.getFactoryInstance();
		pf.setMinValue(0);
		pf.setMaxValue(8);
		pf.setDiscret(true);
		pf.setShowTickMarks(true);
		pf.setShowTickLabels(true);
		pf.setMajorTickUnit(1);
		pf.setNbSpins(0.88);
		pf.setRayon(40);
		pf.setValueDef(0);
		
		Potentiometre octaveKnob = pf.getPotentiometre();
		knobOctavePane.getChildren().add(octaveKnob);
	   	
		pf.setNbSpins(0.80);
		pf.setDiscret(false);
		pf.setMinValue(-1);
		pf.setMaxValue(1);
		
	   	Potentiometre precisionKnob = pf.getPotentiometre();
	   	knobFreqPane.getChildren().add(precisionKnob);
	   	
	   	ControllerVco vcoControl = new ControllerVco();
	   	octaveKnob.valueProperty().addListener((p, oldVal, newVal) ->
	   		vcoControl.handleViewOctaveChange((double) newVal, precisionKnob.getValue()));
	   	precisionKnob.valueProperty().addListener((p, oldVal, newVal) ->
   			vcoControl.handleViewOctaveChange(octaveKnob.getValue(), (double) newVal));
	}

}
