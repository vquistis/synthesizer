package fr.istic.groupimpl.synthesizer.component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import fr.istic.groupimpl.synthesizer.logger.Log;

public abstract class ViewComponent implements IViewComponent {

	public static final double COMPONENT_HEIGHT = 300;

	private List<ChangeListener> portBindings = new ArrayList<ChangeListener>();

	/**
	 * This method should return the root Pane of the component.
	 * @return The root Pane of the component
	 */
	protected abstract Pane getComponentRoot();
	
	public void refreshComponent() {
		portBindings.forEach((c) -> {c.changed(null, null, null);});
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
		Log.getInstance().debug("getNodeBoundsInComponent : " + node.getId());
		Log.getInstance().debug("   [Scene] " + node.getScene());
		Log.getInstance().debug("   [node-getBoundsInParent] = " + node.getBoundsInParent());
		Log.getInstance().debug("   [node-getBoundsInLocal]  = " + node.getBoundsInLocal());
		Log.getInstance().debug("   [node-getLayoutBounds]   = " + node.getLayoutBounds());
		Log.getInstance().debug("   [node-localToScene]      = " + node.localToScene(node.getBoundsInParent()));
		Log.getInstance().debug("   [node-getLayoutX_Y]      = " + node.getLayoutX() + " " + node.getLayoutY());

		Bounds res = node.getBoundsInParent();

		Node componentParent = getComponentRoot();
		Node currentParent = node.getParent();
		Log.getInstance().debug("   [currentParent] = " + currentParent);
		Log.getInstance().debug("      [currentParent-getBoundsInParent] = " + currentParent.getBoundsInParent());
		Log.getInstance().debug("      [currentParent-getBoundsInLocal]  = " + currentParent.getBoundsInLocal());
		Log.getInstance().debug("      [currentParent-getLayoutBounds]   = " + currentParent.getLayoutBounds());
		
		if(currentParent != null) {
			Bounds bounds = currentParent.localToParent(node.getBoundsInParent()); // BUG de localToParent
			Log.getInstance().debug("      [bounds] = " + bounds);

			while(currentParent != componentParent) {
				currentParent = currentParent.getParent();
				bounds = currentParent.localToParent(bounds);
				Log.getInstance().debug("   [currentParent] = " + currentParent);
				Log.getInstance().debug("      [currentParent-getBoundsInParent] = " + currentParent.getBoundsInParent());
				Log.getInstance().debug("      [currentParent-getBoundsInLocal]  = " + currentParent.getBoundsInLocal());
				Log.getInstance().debug("      [currentParent-getLayoutBounds]   = " + currentParent.getLayoutBounds());
				Log.getInstance().debug("      [bounds] = " + bounds);
			}

			// if connected 
			if(currentParent.getParent() != null) {
				Log.getInstance().debug("   [CONNECTED]");
				// recomputes the bounds of the node in the HBox containing the component
				currentParent = currentParent.getParent();
				bounds = currentParent.localToParent(bounds);
				Log.getInstance().debug("   [currentParent] = " + currentParent);
				Log.getInstance().debug("      [currentParent-getBoundsInParent] = " + currentParent.getBoundsInParent());
				Log.getInstance().debug("      [currentParent-getBoundsInLocal]  = " + currentParent.getBoundsInLocal());
				Log.getInstance().debug("      [currentParent-getLayoutBounds]   = " + currentParent.getLayoutBounds());
				Log.getInstance().debug("      [bounds] = " + bounds);
				
				// recomputes the bounds of the node in the SplitPane containing HBox containing the component
				if(currentParent.getParent() != null) {
					currentParent = currentParent.getParent();
					bounds = currentParent.localToParent(bounds);
					Log.getInstance().debug("   [currentParent] = " + currentParent);
					Log.getInstance().debug("      [currentParent-getBoundsInParent] = " + currentParent.getBoundsInParent());
					Log.getInstance().debug("      [currentParent-getBoundsInLocal]  = " + currentParent.getBoundsInLocal());
					Log.getInstance().debug("      [currentParent-getLayoutBounds]   = " + currentParent.getLayoutBounds());
					Log.getInstance().debug("      [bounds] = " + bounds);
				}
			} else {
				Log.getInstance().debug("   [UNCONNECTED]");
			}
			res = bounds;
		} 
		return res;
	}

	private Point2D computeNodeCenter(Node node) {
		Bounds bounds = getNodeBoundsInComponent(node);
		Log.getInstance().debug("computeNodeCenter : " + node.getId());
		Log.getInstance().debug(" computeNodeCenter " + bounds);
		Log.getInstance().debug(" computeNodeCenter " + new Point2D(bounds.getMinX()+bounds.getWidth()/2, bounds.getMinY()+bounds.getHeight()/2));
		return new Point2D(bounds.getMinX()+bounds.getWidth()/2, bounds.getMinY()+bounds.getHeight()/2);

	}
}
