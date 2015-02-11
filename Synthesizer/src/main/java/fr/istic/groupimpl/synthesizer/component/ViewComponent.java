package fr.istic.groupimpl.synthesizer.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import fr.istic.groupimpl.synthesizer.io.architecture.Module;
import fr.istic.groupimpl.synthesizer.logger.Log;

public abstract class ViewComponent implements IViewComponent {

	public static final double COMPONENT_HEIGHT = 300;

	private List<ChangeListener> portBindings = new ArrayList<ChangeListener>();
	
	private Map<String, Supplier<Double>> saveActionMap =new HashMap<>();
	private Map<String, Consumer<Double>> loadActionMap =new HashMap<>();

	private static boolean debug = false;

	/**
	 * This method should return the root Pane of the component.
	 * @return The root Pane of the component
	 */
	protected abstract Pane getComponentRoot();

	public void refreshComponent() {
		portBindings.forEach((c) -> {
			c.changed(null, null, null);
		});
	}

	/**
	 * This method adds the given node of the component as a port, and binds
	 * the portX and portY properties to the position of the port in the parent
	 * node of the component.
	 * 
	 * @param node The node to be added as a port.
	 * @param portX The property that will be bounds to the x coordinate of the node in the
	 * coordinate space of the parent node. 
	 * @param portY The property that will be bounds to the y coordinate of the node in the
	 * coordinate space of the parent node.
	 */
	final protected void addPort(Node node, DoubleProperty portX, DoubleProperty portY) {
		ChangeListener posChangeListener = ((a,b,c) -> {
			Point2D point2D = computeNodeCenter(node);
			portX.set(point2D.getX());
			portY.set(point2D.getY());
			if(debug) {
				Log.getInstance().debug("[Port Position Recomputed : X = " + point2D.getX() + " ; Y = " + point2D.getY() + "]");
			}
		});

		portBindings.add(posChangeListener);
		Node root = getComponentRoot();

		root.parentProperty().addListener(posChangeListener);
		root.boundsInParentProperty().addListener(posChangeListener);
	}

	final protected void cleanupPorts() {
		Node root = getComponentRoot();
		for(ChangeListener c : portBindings) {
			root.parentProperty().removeListener(c);
			root.boundsInParentProperty().removeListener(c);
		}
	}

	private Bounds getNodeBoundsInComponent(Node node) {
		if(debug) {
			Log.getInstance().debug("getNodeBoundsInComponent : " + node.getId());
			Log.getInstance().debug("   [Scene] " + node.getScene());
			Log.getInstance().debug("   [node-getBoundsInParent] = " + node.getBoundsInParent());
			Log.getInstance().debug("   [node-getBoundsInLocal]  = " + node.getBoundsInLocal());
			Log.getInstance().debug("   [node-getLayoutBounds]   = " + node.getLayoutBounds());
			Log.getInstance().debug("   [node-localToScene]      = " + node.localToScene(node.getBoundsInParent()));
			Log.getInstance().debug("   [node-getLayoutX_Y]      = " + node.getLayoutX() + " " + node.getLayoutY());
		}
		Bounds res = node.getBoundsInParent();

		Node componentParent = getComponentRoot();
		Node currentParent = node.getParent();
		if(debug) {
			Log.getInstance().debug("   [currentParent] = " + currentParent);
			Log.getInstance().debug("      [currentParent-getBoundsInParent] = " + currentParent.getBoundsInParent());
			Log.getInstance().debug("      [currentParent-getBoundsInLocal]  = " + currentParent.getBoundsInLocal());
			Log.getInstance().debug("      [currentParent-getLayoutBounds]   = " + currentParent.getLayoutBounds());
		}
		if(currentParent != null) {
			Bounds bounds = currentParent.localToParent(node.getBoundsInParent());
			if(debug) {
				Log.getInstance().debug("      [bounds] = " + bounds);
			}
			while(currentParent != componentParent) {
				currentParent = currentParent.getParent();
				bounds = currentParent.localToParent(bounds);
				if(debug) {
					Log.getInstance().debug("   [currentParent] = " + currentParent);
					Log.getInstance().debug("      [currentParent-getBoundsInParent] = " + currentParent.getBoundsInParent());
					Log.getInstance().debug("      [currentParent-getBoundsInLocal]  = " + currentParent.getBoundsInLocal());
					Log.getInstance().debug("      [currentParent-getLayoutBounds]   = " + currentParent.getLayoutBounds());
					Log.getInstance().debug("      [bounds] = " + bounds);
				}
			}

			// if connected 
			if(currentParent.getParent() != null) {
				if(debug) {
					Log.getInstance().debug("   [CONNECTED]");
				}
				// recomputes the bounds of the node in the HBox containing the component
				currentParent = currentParent.getParent();
				bounds = currentParent.localToParent(bounds);
				if(debug) {
					Log.getInstance().debug("   [currentParent] = " + currentParent);
					Log.getInstance().debug("      [currentParent-getBoundsInParent] = " + currentParent.getBoundsInParent());
					Log.getInstance().debug("      [currentParent-getBoundsInLocal]  = " + currentParent.getBoundsInLocal());
					Log.getInstance().debug("      [currentParent-getLayoutBounds]   = " + currentParent.getLayoutBounds());
					Log.getInstance().debug("      [bounds] = " + bounds);
				}
				// recomputes the bounds of the node in the SplitPane containing HBox containing the component
				if(currentParent.getParent() != null) {
					currentParent = currentParent.getParent();
					bounds = currentParent.localToParent(bounds);
					if(debug) {
						Log.getInstance().debug("   [currentParent] = " + currentParent);
						Log.getInstance().debug("      [currentParent-getBoundsInParent] = " + currentParent.getBoundsInParent());
						Log.getInstance().debug("      [currentParent-getBoundsInLocal]  = " + currentParent.getBoundsInLocal());
						Log.getInstance().debug("      [currentParent-getLayoutBounds]   = " + currentParent.getLayoutBounds());
						Log.getInstance().debug("      [bounds] = " + bounds);
					}
				}
			} else {
				if(debug) {
					Log.getInstance().debug("   [UNCONNECTED]");
				}
			}
			res = bounds;
		} 
		return res;
	}

	private Point2D computeNodeCenter(Node node) {
		Bounds bounds = getNodeBoundsInComponent(node);
		if(debug) {
			Log.getInstance().debug("computeNodeCenter : " + node.getId());
			Log.getInstance().debug(" computeNodeCenter " + bounds);
			Log.getInstance().debug(" computeNodeCenter " + new Point2D(bounds.getMinX()+bounds.getWidth()/2, bounds.getMinY()+bounds.getHeight()/2));
		}
		return new Point2D(bounds.getMinX()+bounds.getWidth()/2, bounds.getMinY()+bounds.getHeight()/2);

	}
	
	public final Supplier<Module> getSaveSupplier() {
		Supplier<Module> sup = (() -> getConfiguration());
		return sup;
	}
	
	public final int getPositionX(){
		Node node =  getComponentRoot();
		return ((HBox)node.getParent()).getChildren().indexOf(node);
	}
	
	public final int getPositionY(){
		Node node =  getComponentRoot();
		HBox hbox = ((HBox)node.getParent());		
		SplitPane splitPane = ((SplitPane)hbox.getParent().getParent());		
		return splitPane.getItems().indexOf(hbox);
	}
	
	protected final void addParameters(String parameterName, Supplier<Double> saveAction, Consumer<Double> loadAction){
		saveActionMap.put(parameterName, saveAction);
		loadActionMap.put(parameterName, loadAction);		
	}
	
	protected abstract ControllerComponent getController();
	
	protected Module getConfiguration() {
		Module module= new Module();
		Map<String, Double> parameters = module.getParameters();
		saveActionMap.forEach((k,v) -> {
			parameters.put(k, v.get());
		});

		module.setPorts(getController().getAllPort());;
		module.setPosX(getPositionX());
		module.setPosY(getPositionY());
		return module;
	}
	
}
