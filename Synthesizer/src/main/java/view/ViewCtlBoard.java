package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;

public class ViewCtlBoard implements Initializable {

	private static final int SHELF_HEIGHT = 200;

	@FXML
	private ScrollPane scrollpane;

	@FXML
	private BorderPane borderpane;

	@FXML
	private SplitPane splitpane;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		scrollpane.pannableProperty().set(true);
		int nbShelves = (int) SHELF_HEIGHT + 3;
		for(int i=0;i<nbShelves;i++) {
			HBox box = new HBox();
			box.setPrefHeight(SHELF_HEIGHT);
			box.setMinHeight(SHELF_HEIGHT);
			box.setMaxHeight(SHELF_HEIGHT);
			splitpane.getItems().add(box);
		}
		for(int i=0; i<nbShelves;i++) {
			splitpane.setDividerPosition(i, (i+1) * 1.0 / (nbShelves - 1));
		}
	}

}
