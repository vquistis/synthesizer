package fr.istic.groupimpl.synthesizer.global;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.util.Pair;
import fr.istic.groupimpl.synthesizer.component.IViewComponent;

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

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		HBox h1 = new HBox();
		HBox h2 = new HBox();
		HBox h3 = new HBox();
		h1.setPrefSize(800, 300);
		h2.setPrefSize(800, 300);
		h3.setPrefSize(800, 300);
		
		splitpane.getItems().add(h1);
		splitpane.getItems().add(h2);
		splitpane.getItems().add(h3);;

		
		for (Node hBox : splitpane.getItems()) {
			hBox.setOnMouseExited(new EventHandler<Event>() {
				@Override
				public void handle(Event event) {
					System.out.println("sss ");
//					listHbox.get(0).getChildren().remove(h);
					
				}
			});
			
//			hBox.setonmouseen(new EventHandler<Event>() {
//				@Override
//				public void handle(Event event) {
//					System.out.println("sss "+listHbox.indexOf(hBox));
//					listHbox.get(1).getChildren().add(h);
//					
//				}
//			});
		}
		
		
	}

	public void addModule(Node node, int i, int j) {
		hboxes.get(i).getChildren().add(j, node);
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
//			hboxes.get(0).getChildren().add(root);
			((HBox)splitpane.getItems().get(0)).getChildren().add(root);
			setupListeners(root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setupListeners(Node node) {
		node.setOnMouseDragged(new EventHandler<MouseEvent>() {			 
	        @Override
	        public void handle(MouseEvent event) {
	        	if (lastXY == null) {
	                lastXY = new Point2D(event.getSceneX(), event.getSceneY());
	            }
	        	
//	        		 HBox hBox=null;
//	        		 for (Node h : listHbox) {	        			 
//	        			 if (listHbox.get(0).intersects(lastXY.getX(), lastXY.getY(),1,1)) {
//	        				 hBox = (HBox) listHbox.get(0);
//	        				 hBox.getChildren().remove(node);
//	        				 
//	        				 
//	        				 System.out.println("pane1");
//	        				 break;
//						}						
//					}
//	        		 for (Node h : listHbox) {	        			 
//	        			 if (listHbox.get(1).contains(event.getX(), event.getY())) {
//	        				 hBox = (HBox) listHbox.get(1);
//	        				 hBox.getChildren().add(node);
//	        				 System.out.println("pane2");
////	        				 break;
//						}						
//					}
		            event.setDragDetect(false);		            
		            
		            double dx = event.getSceneX() - lastXY.getX();
		            double dy = event.getSceneY() - lastXY.getY();
		            node.setTranslateX(node.getTranslateX()+dx);
		            node.setTranslateY(node.getTranslateY()+dy);
		            lastXY = new Point2D(event.getSceneX(), event.getSceneY());
		            if (!splitpane.intersects(event.getSceneX(), event.getSceneY(), 1, 1)) event.setDragDetect(true);
		            event.consume();
	        }
	    });

        node.setOnDragDetected(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
	            Node on = (Node)event.getTarget();
	            
	            System.out.println("--"+on.getId());
	            Dragboard db = on.startDragAndDrop(TransferMode.COPY);
	            event.consume();							
			}
		});
	}
	
	@FXML
	public void handleAddComponent(){
		createModule("fxml/vco.fxml");		
	}
	
	
	
}
