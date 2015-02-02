package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;



public class ViewCtlVCO implements Initializable {

	@FXML
	HBox hb_left;
	@FXML
	HBox hb_right;
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
	Region rg_fm;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fm.getStyleClass().add("inout");
		//fm.getStyleClass().add("inout");
		Potentiometre potentiometre = new Potentiometre("Octave");
		potentiometre.setTitle(new Text("Octave"));
//		Potentiometre potentiometre1 = new Potentiometre("Tone");
//		hb_left.getChildren().add(potentiometre);
    	hb_right.getChildren().add(potentiometre);
//	//	fm.getStyleClass().add(e);

	}

}
