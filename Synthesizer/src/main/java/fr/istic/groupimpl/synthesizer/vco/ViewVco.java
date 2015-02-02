package fr.istic.groupimpl.synthesizer.vco;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import fr.istic.groupimpl.synthesizer.component.IViewComponent;
import fr.istic.groupimpl.synthesizer.component.Port;
import fr.istic.groupimpl.synthesizer.util.Potentiometre;

public class ViewVco implements IViewComponent, Initializable {

	@FXML
	BorderPane paneVco;
	@FXML
	HBox hbLeft;
	@FXML
	HBox hbRight;
	@FXML
	Circle fm;
	@FXML
	Circle square;
	@FXML
	Circle triangle;
	@FXML
	Circle sawTooth;
	@FXML
	Text display;
	@FXML
	Region rgFm;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		CornerRadii radii = new CornerRadii(5.00);
		BorderWidths widths = new BorderWidths(1.00);
		BorderStroke bs = new BorderStroke(Paint.valueOf("BLACK"), BorderStrokeStyle.SOLID, radii, widths);
		Border border = new Border(bs);
		paneVco.setBorder(border);
		
		fm.getStyleClass().add("inout");
		
		Potentiometre potentiometre = new Potentiometre("Octave");
		potentiometre.setTitle(new Text("Octave"));
		Potentiometre potentiometre1 = new Potentiometre("Tone");
	   	hbRight.getChildren().add(potentiometre1);

	}

	@Override
	public List<Port> getAllPorts() {
		// TODO Auto-generated method stub
		return null;
	}

}