package fr.istic.groupimpl.synthesizer.component;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import fr.istic.groupimpl.synthesizer.logger.Log;

public abstract class CopyOfIViewComponent {
	
	public static final double COMPONENT_HEIGHT = 300;
	
	private List<ChangeListener> portBindings = new ArrayList<ChangeListener>();

	/**
	 * This method should return the root Pane of the component.
	 * @return The root Pane of the component
	 */
	protected abstract Pane getComponentRoot();

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
			portX.set(computeNodeCenter(node).getX());
			portY.set(computeNodeCenter(node).getY());
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
		Bounds res = node.getBoundsInParent();

		Node componentParent = getComponentRoot();
		Node currentParent = node.getParent();

		if(currentParent != null) {
			Bounds bounds = currentParent.localToParent(node.getBoundsInParent());

			while(currentParent != componentParent) {
				Log.getInstance().debug(""+currentParent.getClass());
				currentParent = currentParent.getParent();
				bounds = currentParent.localToParent(bounds);
			}
			// recomputes the bounds of the node in the HBox containing the component
			bounds = currentParent.getParent().localToParent(bounds);
			// recomputes the bounds of the node in the SplitPane containing HBox containing the component
			bounds = currentParent.getParent().getParent().localToParent(bounds);
			res = bounds;
		}

		return res;
	}

	private Point2D computeNodeCenter(Node node) {
		Bounds bounds = getNodeBoundsInComponent(node);
		return new Point2D(bounds.getMinX()+bounds.getWidth()/2, bounds.getMinY()+bounds.getHeight()/2);
	}
	
}
