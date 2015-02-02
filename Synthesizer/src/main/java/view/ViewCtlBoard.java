package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class ViewCtlBoard implements Initializable {

	private static final int SHELF_HEIGHT = 200;

	@FXML
	private ScrollPane scrollpane;

	@FXML
	private BorderPane borderpane;

	@FXML
	private SplitPane splitpane;

	
	
	
	@FXML
	private Label source;

	@FXML
	private HBox target;

	Point2D lastXY = null;

	private Component component;

	public void initialize(URL arg0, ResourceBundle arg1) {

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


		component = new Component();

		source.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				/* drag was detected, start drag-and-drop gesture */
				System.out.println("onDragDetected");

				/* allow any transfer mode */
				Dragboard db = source.startDragAndDrop(TransferMode.ANY);

				/* put a string on dragboard */
				ClipboardContent content = new ClipboardContent();
				content.putString(source.getText());
				db.setContent(content);

				event.consume();
			}
		});

		target.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				/* data is dragged over the target */
				System.out.println("onDragOver");

				/*
				 * accept it only if it is not dragged from the same node and if
				 * it has a string data
				 */
				if (event.getGestureSource() != target
						&& event.getDragboard().hasString()) {
					/* allow for both copying and moving, whatever user chooses */
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}

				event.consume();
			}
		});

		target.setOnDragEntered(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				/* the drag-and-drop gesture entered the target */
				System.out.println("onDragEntered");
				/* show to the user that it is an actual gesture target */
				if (event.getGestureSource() != target
						&& event.getDragboard().hasString()) {
					// target.setFill(Color.GREEN);
				}

				event.consume();
			}
		});

		target.setOnDragExited(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				/* mouse moved away, remove the graphical cues */
				// target.setFill(Color.BLACK);

				event.consume();
			}
		});

		target.setOnDragDropped(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				/* data dropped */
				System.out.println("onDragDropped");
				/* if there is a string data on dragboard, read it and use it */
				Dragboard db = event.getDragboard();
				boolean success = false;
				if (db.hasString()) {
					Node node = component.show();					
					target.getChildren().add(node);

					System.out.println(node);

					node.setOnMouseDragged(new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent event) {
							System.out.println("Move");
							event.setDragDetect(false);

							if (lastXY == null) {
								lastXY = new Point2D(event.getSceneX(), event.getSceneY());
							}
							double dx = event.getSceneX() - lastXY.getX();
							double dy = event.getSceneY() - lastXY.getY();
							node.setTranslateX(node.getTranslateX()+dx);
							node.setTranslateY(node.getTranslateY()+dy);
							lastXY = new Point2D(event.getSceneX(), event.getSceneY());
							if (!target.intersects(event.getSceneX(), event.getSceneY(), 1, 1)) event.setDragDetect(true);
							event.consume();
						}
					});

					node.setOnDragDetected(new EventHandler<Event>() {
						@Override
						public void handle(Event event) {
							Node on = (Node)event.getTarget();
							Dragboard db = on.startDragAndDrop(TransferMode.COPY);
							event.consume();							
						}
					});

					node.setOnMouseReleased(d ->  lastXY = null);
					success = true;
				}
				/*
				 * let the source know whether the string was successfully
				 * transferred and used
				 */
				event.setDropCompleted(success);
				event.consume();
			}
		});

		// source.setOnDragDone(new EventHandler <DragEvent>() {
		// public void handle(DragEvent event) {
		// /* the drag-and-drop gesture ended */
		// System.out.println("onDragDone");
		// /* if the data was successfully moved, clear it */
		// if (event.getTransferMode() == TransferMode.MOVE) {
		// source.setText("");
		// }
		//
		// event.consume();
		// }
		// });

	}

}
