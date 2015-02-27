package fr.istic.groupimpl.synthesizer.util;

import java.net.URL;
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

/**
 * The Class ViewTestPot.
 */
public class ViewTestPot implements Initializable {

	/** The pane vco. */
	@FXML
	BorderPane paneVco;
	
	/** The hb left. */
	@FXML
	HBox hbLeft;
	
	/** The hb right. */
	@FXML
	HBox hbRight;
	
	/** The fm. */
	@FXML
	Circle fm;
	
	/** The square. */
	@FXML
	Circle square;
	
	/** The triangle. */
	@FXML
	Circle triangle;
	
	/** The saw tooth. */
	@FXML
	Circle sawTooth;
	
	/** The display. */
	@FXML
	Text display;
	
	/** The rg fm. */
	@FXML
	Region rgFm;

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		CornerRadii radii = new CornerRadii(5.00);
		BorderWidths widths = new BorderWidths(1.00);
		BorderStroke bs = new BorderStroke(Paint.valueOf("BLACK"), BorderStrokeStyle.SOLID, radii, widths);
		Border border = new Border(bs);
		paneVco.setBorder(border);
		
		fm.getStyleClass().add("inout");
		
		PotentiometreFactory initPot = PotentiometreFactory.getFactoryInstance();
		initPot.setTitle("Octave");
		initPot.setRayon(40);
		initPot.setMinValue(1);
		initPot.setMaxValue(9);
		initPot.setNbSpins(0.8);
		initPot.setDiscret(true);
		initPot.setShowTickLabels(true);
		initPot.setShowTickMarks(true);
		initPot.setMajorTickUnit(1.);
		initPot.setValueDef(5);
		
		Potentiometre potentiometre = initPot.getPotentiometre();
		hbLeft.getChildren().add(potentiometre);
		
	
		PotentiometreFactory initPot2 = PotentiometreFactory.getFactoryInstance();
		
		initPot2.setRayon(30);
		initPot2.setTitle("Tone");
		
		Potentiometre potentiometre1 = initPot2.getPotentiometre();
		
	   	hbRight.getChildren().add(potentiometre1);

	}


}
