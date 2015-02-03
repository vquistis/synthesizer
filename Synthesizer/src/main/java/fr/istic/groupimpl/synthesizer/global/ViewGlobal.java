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



public class ViewGlobal implements Initializable {


	@FXML
	private Button vcoModule;

	@FXML
	private SplitPane splitpane;

	@FXML
	private ScrollPane scrollpane;

	List<HBox> hboxes = new ArrayList<HBox>();
	List<Pair<Node,IViewComponent>> listAssoc = new ArrayList<Pair<Node,IViewComponent>>();
	Point2D lastXY = null;

	private HBox h;
	private HBox h1;
	private HBox h2;
	private HBox h3;
	private Node n;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		h1 = new HBox();
		h2 = new HBox();
		h3 = new HBox();
		h1.setPrefSize(800, 300);
		h2.setPrefSize(800, 300);
		h3.setPrefSize(800, 300);

		splitpane.getItems().addAll(h1, h2, h3);

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
					box.getChildren().add(node);
					
				}
			});
		}
	}

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
	
	private void enableDrag(Node node) {
		node.setOnDragDetected((event) -> {
			ClipboardContent content = new ClipboardContent();
			content.putString(getString(node));
			Dragboard db = scrollpane.startDragAndDrop(TransferMode.ANY);
			db.setContent(content);
			event.consume();
		});
	}

	@FXML
	public void handleAddComponent(){
		createModule("fxml/vco.fxml");		
	}

	
}
