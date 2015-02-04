package fr.istic.groupimpl.synthesizer.global;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.util.Pair;
import fr.istic.groupimpl.synthesizer.component.IViewComponent;
import fr.istic.groupimpl.synthesizer.logger.Log;

/**
 * The Class ViewGlobal.
 */
public class ViewGlobal implements Initializable {

	/** The vco btn. */
	@FXML
	private Button vcoBtn;
	
	/** The out btn. */
	@FXML
	private Button outBtn;

	/** The splitpane. */
	@FXML
	private SplitPane splitpane;

	/** The scrollpane. */
	@FXML
	private ScrollPane scrollpane;

	/** The hboxes. */
	List<HBox> hboxes = new ArrayList<HBox>();
	
	/** The list assoc. */
	List<Pair<Node,IViewComponent>> listAssoc = new ArrayList<Pair<Node,IViewComponent>>();
	
	/** The last xy. */
	Point2D lastXY = null;

	/** The h. */
	private HBox h;
	
	/** The h1. */
	private HBox h1;
	
	/** The n. */
	private Node n;

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		for (int i = 0; i < 3; i++) {
			h1 = new HBox();
			h1.setPrefSize(600, 200);
			h1.getStyleClass().add("background");
			splitpane.getItems().add(h1);
		}
		
		vcoBtn.getStyleClass().add("btnClass");
		outBtn.getStyleClass().add("btnClass");

		for(Node n : splitpane.getItems()) {
			n.setOnDragOver((e) -> {
				e.acceptTransferModes(TransferMode.ANY);
				e.consume();
			});
			n.setOnDragDropped((e) -> {
				
				e.acceptTransferModes(TransferMode.ANY);
				e.consume();				
				Dragboard db = e.getDragboard();
				boolean success = false;
				if (db.hasString()) {
					Log.getInstance().debug("----DROPPED: "+db.getString());
					
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
	}

	/**
	 * Adds the module.
	 *
	 * @param node the node
	 * @param i the i
	 * @param j the j
	 */
	public void addModule(Node node, int i, int j) {
		hboxes.get(i).getChildren().add(j, node);
	}

	//	public void deleteModule(Node node, int i) {
	//		int idx = 0;
	//		for(Pair<Node, IViewComponent> p : listAssoc) {
	//			if(p.getKey().equals(node)) {
	//				break;
	//			}
	//			idx++;
	//		}
	//		listAssoc.remove(idx);
	//		hboxes.get(i).getChildren().remove(node);
	//	}

	//	public void removeModule(Node node, int i) {
	//		hboxes.get(i).getChildren().remove(node);
	//	}

	/**
	 * Creates the module.
	 *
	 * @param filename the filename
	 */
	public void createModule(String filename) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(filename));
			IViewComponent view = loader.getController();
			Node root = loader.load();
			listAssoc.add(new Pair<Node, IViewComponent>(root, view));
			((HBox)splitpane.getItems().get(0)).getChildren().add(root);
			enableDrag(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the string.
	 *
	 * @param node the node
	 * @return the string
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
	 * Enable drag.
	 *
	 * @param node the node
	 */
	private void enableDrag(Node node) {
		node.setOnDragDetected((event) -> {
			ClipboardContent content = new ClipboardContent();
			content.putString(getString(node));
			Dragboard db = scrollpane.startDragAndDrop(TransferMode.ANY);
			db.setContent(content);
			event.consume();
		});
	}

	/**
	 * Handle add vco.
	 */
	@FXML
	public void handleAddVco(){
		createModule("fxml/vco.fxml");		
	}
	
	/**
	 * Handle add out.
	 */
	@FXML
	public void handleAddOut(){
		createModule("fxml/out.fxml");		
	}	
}
