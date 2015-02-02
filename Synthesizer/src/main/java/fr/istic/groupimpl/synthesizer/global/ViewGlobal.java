package fr.istic.groupimpl.synthesizer.global;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.util.Pair;
import fr.istic.groupimpl.synthesizer.component.IViewComponent;

public class ViewGlobal implements Initializable {

	@FXML
	private SplitPane splitpane;
	
	@FXML
	private ScrollPane scrollpane;
	
	List<HBox> hboxes = new ArrayList<HBox>();
	List<Pair<Node,IViewComponent>> listAssoc = new ArrayList<Pair<Node,IViewComponent>>();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

	public void addModule(Node node, int i, int j) {
		hboxes.get(i).getChildren().add(j, node);
		setupListeners(node);
	}

	public void deleteModule(Node node, int i) {
		int idx = 0;
		for(Pair<Node, IViewComponent> p : listAssoc) {
			if(p.getKey().equals(node)) {
				break;
			}
			idx++;
		}
		listAssoc.remove(idx);
		hboxes.get(i).getChildren().remove(node);
	}
	
	public void removeModule(Node node, int i) {
		hboxes.get(i).getChildren().remove(node);
	}
	
	public void createModule(String filename) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(filename));
			IViewComponent view = loader.getController();
			Node root = loader.load();
			listAssoc.add(new Pair<Node, IViewComponent>(root, view));
			hboxes.get(0).getChildren().add(root);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setupListeners(Node node) {
		
	}
	
	
	
}
