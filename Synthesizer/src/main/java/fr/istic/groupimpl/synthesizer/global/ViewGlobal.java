package fr.istic.groupimpl.synthesizer.global;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import fr.istic.groupimpl.synthesizer.cable.Cable;
import fr.istic.groupimpl.synthesizer.component.ViewComponent;
import fr.istic.groupimpl.synthesizer.logger.Log;
import fr.istic.groupimpl.synthesizer.util.DebugJFXTools;

/**
 * The Class ViewGlobal.
 * Implements all the FXML components and sets them their corresponding commands.
 * @version 1.0
 * @since 1.0
 */
public class ViewGlobal implements Initializable {

	/** The borderpane. */
	@FXML private BorderPane borderpane; 
	
	/** The contentpane. */
	@FXML private Pane contentpane;
	
	/** The scrollpane. */
	@FXML private ScrollPane scrollpane;
	
	/** The splitpane. */
	@FXML private SplitPane splitpane;
	
	/** The hb1. */
	@FXML private HBox hb1;
	
	/** The hb2. */
	@FXML private HBox hb2;
	
	/** The hb3. */
	@FXML private HBox hb3;
	
//	private List<HBox> hboxes = new ArrayList<HBox>();
//	private List<Pair<Node,ViewComponent>> listAssoc = new ArrayList<Pair<Node,ViewComponent>>();
	/** The mouse x. */
private DoubleProperty mouseX = new SimpleDoubleProperty(0);
	
	/** The mouse y. */
	private DoubleProperty mouseY = new SimpleDoubleProperty(0);
	
	/** The ctl. */
	private ControllerGlobal ctl;

	/**
	 * Adds the cable.
	 *
	 * @param cable the cable
	 */
	public void addCable(Cable cable) {
		contentpane.getChildren().add(contentpane.getChildren().size()-1, cable);
		cable.setOnMouseClicked((event) -> {
			if(event.getButton() == MouseButton.PRIMARY) {
				ctl.handleRemoveCable(cable);
			}
		});
		cable.toFront();
	}

	/**
	 * Removes the cable.
	 *
	 * @param cable the cable
	 */
	public void removeCable(Cable cable) {
		contentpane.getChildren().remove(cable);
		cable.toFront();
	}

	/**
	 * Initializes the controller class.
	 * This method is automatically called after the FXML file has been loaded. It creates a new view and set all the button with new created buttons.
	 *
	 * @param url the url
	 * @param resourceBundle the resourceBundle
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		
		ctl = ControllerGlobal.getInstance();
		
		contentpane.addEventFilter(MouseEvent.MOUSE_CLICKED, (event) -> {
			if(event.getButton() == MouseButton.SECONDARY) {
				event.consume();
				ctl.handleRightButtonClicked();
				contentpane.setCursor(Cursor.DEFAULT);
				for(Node n : contentpane.getChildren()) {
					if(n instanceof Cable) {
						n.setMouseTransparent(true);
					}
				}
			}
		});
		
		// automatic resizing
		scrollpane.widthProperty().addListener((obs, oldWidth, newWidth) -> {
			splitpane.setPrefWidth((double) newWidth);
		});
		scrollpane.heightProperty().addListener((obs, oldHeight, newHeight) -> {
			splitpane.setPrefHeight((double) newHeight);
		});
		
		for(Node n : splitpane.getItems()) {
			n.setOnDragOver((e) -> {
				e.acceptTransferModes(TransferMode.ANY);
				e.consume();
			});
			n.setOnDragDropped((e) -> {
				e.acceptTransferModes(TransferMode.ANY);
				e.consume();				
				Dragboard db = e.getDragboard();
//				boolean success = false;
				if (db.hasString()) {
					Log.getInstance().debug("DROP DONE");
					String []pos=db.getString().split(";");
					Node node = ((HBox)splitpane.getItems().get(Integer.parseInt(pos[0]))).getChildren().get(Integer.parseInt(pos[1]));
					HBox box=(HBox) n;
					((HBox)splitpane.getItems().get(Integer.parseInt(pos[0]))).getChildren().remove(Integer.parseInt(pos[1]));
					int index =0;
					for(Node child : box.getChildren()){	
						if (child.contains(e.getX(), e.getY())) {
							break;
						}
						index++;
					}
					box.getChildren().add(index,node);
				}
			});
		}

		splitpane.setOnMouseMoved((e) -> {
			mouseX.set(e.getX());
			mouseY.set(e.getY());
		});

//		splitpane.setOnDragOver((e) -> {
//			Event.fireEvent(scrollpane, e);
//			double spXMin = scrollpane.getViewportBounds().getMinX();
//			double spYMin = scrollpane.getViewportBounds().getMinY();
//			double spXMax = scrollpane.getViewportBounds().getMaxX();
//			double spYMax = scrollpane.getViewportBounds().getMaxY();
//			double x = e.getX();
//			double y = e.getY();
//			Point2D point = splitpane.localToParent(e.getX(), e.getY());
//
//			double pX = point.getX();
//			double pY = point.getY();
//			Log.getInstance().debug("!! Scroll : " + x  + " " + y);
//			Log.getInstance().debug("!! Scroll : " + pX  + " " + pY);
//
//			if ((pX >= spXMin  && pX <= spXMin+50) &&
//					(pY >= spYMin  && pY <= spYMax))  
//			{
//				scrollpane.setHvalue(scrollpane.getHvalue() - 0.05);
//				Log.getInstance().debug("!!! Scroll Column left " + scrollpane.getHvalue() );
//
//			} else if ((pX >= spXMax-50  && pX <= spXMax) &&
//					(pY >= spYMin  && pY <= spYMax))  
//			{
//				scrollpane.setHvalue(scrollpane.getHvalue() + 0.05);
//				Log.getInstance().debug("!!! Scroll Column right " + scrollpane.getHvalue() );
//
//			}
//
//			if ((pX >= spXMin  && pX <= spXMax) &&
//					(pY >= spYMin  && pY <= spYMin+50))  
//			{
//				scrollpane.setVvalue(scrollpane.getVvalue() - 0.05);
//
//			} else if ((pX >= spXMin  && pX <= spXMax) &&
//					(pY >= spYMax-50  && pY <= spYMax))  
//			{
//				scrollpane.setVvalue(scrollpane.getVvalue() + 0.05);
//			}
//
//		});

		createModule("fxml/out.fxml");
		
		ctl.setView(this);
	}

//	/**
//	 * add the node in the i position HBOX.
//	 *
//	 * @param node the node
//	 * @param i the i
//	 * @param j the j
//	 */
//	public void addModule(Node node, int i, int j) {
//		hboxes.get(i).getChildren().add(j, node);
//	}
//
//	/**
//	 * Delete the i th module which is in the list hbox
//	 *
//	 * @param node the node delete
//	 * @param i the position
//	 */
//	public void deleteModule(Node node, int i) {
//		int idx = 0;
//		for(Pair<Node, ViewComponent> p : listAssoc) {
//			if(p.getKey().equals(node)) {
//				break;
//			}
//			idx++;
//		}
//		listAssoc.remove(idx);
//		hboxes.get(i).getChildren().remove(node);
//	}
//
//	/**
//	 * Removes the module.
//	 *
//	 * @param node the node
//	 * @param i the i
//	 */
//	public void removeModule(Node node, int i) {
//		hboxes.get(i).getChildren().remove(node);
//	}

	/**
	 * Creates a new module.
	 *
	 * @param filename the filename component fxml
	 */
	public void createModule(String filename) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(filename));
			Node root = loader.load();
			ViewComponent view = loader.getController();
//			listAssoc.add(new Pair<Node, ViewComponent>(root, view));
			HBox hb = (HBox) splitpane.getItems().get(0);
			hb.getChildren().add(root);
			enableDrag(root);
			view.refreshComponent();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to return the position of a component in the container splitPane.
	 *
	 * @param node the node
	 * @return the string. position the component
	 */
	private String getString(Node node) {
		int i=0;
		String res = "";
		for(Node hbox : splitpane.getItems()) {
			ObservableList<Node> nodes = ((HBox) hbox).getChildren();
			if(nodes.contains(node)) {
				res = res + i + ";" + nodes.indexOf(node);
				break;
			}
			i++;
		}
		return res;
	}

	/**
	 * Add enableDrag event to a node.
	 * This event is called when the drag event is enabled
	 *
	 * @param node the node
	 */
	private void enableDrag(Node node) {
		node.setOnDragDetected((event) -> {
			Log.getInstance().debug("DROP STARTED");
			ClipboardContent content = new ClipboardContent();
			content.putString(getString(node));
			Dragboard db = scrollpane.startDragAndDrop(TransferMode.ANY);
			db.setContent(content);
			event.consume();
		});
		node.setOnDragDone((event) -> {
			Log.getInstance().debug("DROPPED");
		});
	}

//	/**
//	 * Sets the all modules transparent.
//	 *
//	 * @param t the new all modules transparent
//	 */
//	private void setAllModulesTransparent(boolean t) {
//		splitpane.getItems().forEach((b) -> {
//			((HBox) b).getChildrenUnmodifiable().forEach((m) -> {
//				m.setMouseTransparent(t);
//			});
//		});
//	}

	/**
	 * Handle add vco. Plus click. This method adds a new VCO component VCO
	 */
	@FXML
	public void handleAddVco(){
		createModule("fxml/vco.fxml");		
	}
	
	/**
	 * Handle add vca.
	 */
	@FXML
	public void handleAddVca(){
		createModule("fxml/vca.fxml");	
	}

	/**
	 * Handle add out. This method adds a new OUT components
	 */
	@FXML
	public void handleAddOut(){
		createModule("fxml/out.fxml");		
	}
	
	/**
	 * Handle add scope.
	 */
	@FXML
	public void handleAddScope(){
		createModule("fxml/oscillo.fxml");		
	}
	
	/**
	 * Handle add eg.
	 */
	@FXML
	public void handleAddEg(){
		createModule("fxml/eg.fxml");		
	}

	/**
	 * Mouse x property.
	 *
	 * @return the double property
	 */
	public DoubleProperty mouseXProperty() {
		return mouseX;
	}

	/**
	 * Mouse y property.
	 *
	 * @return the double property
	 */
	public DoubleProperty mouseYProperty() {
		return mouseY;
	}
	
	/**
	 * Handle delete.
	 */
	@FXML
	public void handleDelete() {
		contentpane.setCursor(Cursor.CROSSHAIR);
		for(Node n : contentpane.getChildren()) {
			if(n instanceof Cable) {
				n.setMouseTransparent(false);
			}
		}
	}

	@FXML
	public void handleMenuDevmodeNodeHierarchy_1() {
		DebugJFXTools debugJFXTools = new DebugJFXTools();
		debugJFXTools.GenerateNodeHierarchy(borderpane, "synthjfx_1.dmp");
	}
	@FXML
	public void handleMenuDevmodeNodeHierarchy_2() {
		DebugJFXTools debugJFXTools = new DebugJFXTools();
		debugJFXTools.GenerateNodeHierarchy(borderpane, "synthjfx_2.dmp");
	}
}
