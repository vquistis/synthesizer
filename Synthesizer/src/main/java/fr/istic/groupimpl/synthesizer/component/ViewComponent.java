package fr.istic.groupimpl.synthesizer.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import com.jsyn.ports.UnitPort;

import fr.istic.groupimpl.synthesizer.command.ICommand;
import fr.istic.groupimpl.synthesizer.io.architecture.Module;
import fr.istic.groupimpl.synthesizer.io.architecture.Port;
import fr.istic.groupimpl.synthesizer.logger.Log;

/**
 * The Class ViewComponent.
 */
public abstract class ViewComponent implements IViewComponent {

	/** The Constant COMPONENT_HEIGHT. */
	public static final double COMPONENT_HEIGHT = 300;

	/** The port bindings. */
	private List<ChangeListener> portBindings = new ArrayList<ChangeListener>();
	
	/** The save action map. */
	private Map<String, Supplier<Double>> saveActionMap = new HashMap<>();
	
	/** The load action map. */
	private Map<String, Consumer<Double>> loadActionMap = new HashMap<>();
	
	private Map<String, Pair<DoubleProperty, DoubleProperty>> cablesProperties= new HashMap<>();

	private final Supplier<Module> sup = (() -> getConfiguration());
	
	/** The debug. */
	private static boolean debug = false;
	
	private ChangeListener<? super Number> listener = (a,b,c) -> {refreshComponent();};

	private ICommand cmd;

	public ChangeListener<? super Number> getListener() {
		return listener;
	}
	
	/**
	 * This method should return the root Pane of the component.
	 * @return The root Pane of the component
	 */
	protected abstract Pane getComponentRoot();

	/**
	 * Refresh component.
	 */
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
	 * @param portNode The node to be added as a port.
	 * @param portX The property that will be bounds to the x coordinate of the node in the
	 * coordinate space of the parent node. 
	 * @param portY The property that will be bounds to the y coordinate of the node in the
	 * coordinate space of the parent node.
	 */
	final protected void addPort(String portName, Node portNode) {
		
		DoubleProperty portX = new SimpleDoubleProperty(0);
		DoubleProperty portY = new SimpleDoubleProperty(0);
		Pair<DoubleProperty, DoubleProperty> prop = new Pair<>(portX, portY);
		cablesProperties.put(portName, prop);

		ControllerComponent ctl = getController();
		ctl.setupPort(portName, portNode, portX, portY);
				
		ChangeListener posChangeListener = ((a,b,c) -> {
			Point2D point2D = computeNodeCenter(portNode);

			cablesProperties.get(portName).getKey().set(point2D.getX());
			cablesProperties.get(portName).getValue().set(point2D.getY());
			if(debug) {
				Log.getInstance().debug("[Port Position Recomputed : X = " + point2D.getX() + " ; Y = " + point2D.getY() + "]");
			}
		});

		portBindings.add(posChangeListener);
		Node root = getComponentRoot();

		root.parentProperty().addListener(posChangeListener);
		root.boundsInParentProperty().addListener(posChangeListener);
	}

	
	/**
	 * Cleanup ports.
	 */
	final protected void cleanup() {
		Node root = getComponentRoot();
		cmd.execute();
		for(ChangeListener c : portBindings) {
			root.parentProperty().removeListener(c);
			root.boundsInParentProperty().removeListener(c);
		}
	}

	/**
	 * Gets the node bounds in component.
	 *
	 * @param node the node
	 * @return the node bounds in component
	 */
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

	/**
	 * Compute node center.
	 *
	 * @param node the node
	 * @return the point2 d
	 */
	private Point2D computeNodeCenter(Node node) {
		Bounds bounds = getNodeBoundsInComponent(node);
		if(debug) {
			Log.getInstance().debug("computeNodeCenter : " + node.getId());
			Log.getInstance().debug(" computeNodeCenter " + bounds);
			Log.getInstance().debug(" computeNodeCenter " + new Point2D(bounds.getMinX()+bounds.getWidth()/2, bounds.getMinY()+bounds.getHeight()/2));
		}
		return new Point2D(bounds.getMinX()+bounds.getWidth()/2, bounds.getMinY()+bounds.getHeight()/2);
	}
	
	/**
	 * Gets the save supplier.
	 *
	 * @return the save supplier
	 */
	public final Supplier<Module> getSaveSupplier() {
		return sup;
	}
	
	/**
	 * Gets the position x.
	 *
	 * @return the position x
	 */
	public final int getPositionX(){
		Node node =  getComponentRoot();
		if (node.getParent() !=null) {
			return ((HBox)node.getParent()).getChildren().indexOf(node);
		} else {
			return 0;
		}
		
	}
	
	/**
	 * Gets the position y.
	 *
	 * @return the position y
	 */
	public final int getPositionY(){
		Node node =  getComponentRoot();
		if (node.getParent() != null) {
			HBox hbox = ((HBox)node.getParent());		
			SplitPane splitPane = ((SplitPane)hbox.getParent().getParent());		
			return splitPane.getItems().indexOf(hbox);
		} else {
			return 0;
		}
		
	}
	
	/**
	 * Adds the parameters.
	 *
	 * @param parameterName the parameter name
	 * @param saveAction the save action
	 * @param loadAction the load action
	 */
	protected final void addParameters(String parameterName, Supplier<Double> saveAction, Consumer<Double> loadAction){
		saveActionMap.put(parameterName, saveAction);
		loadActionMap.put(parameterName, loadAction);		
	}
	
	/**
	 * Gets the controller.
	 *
	 * @return the controller
	 */
	protected abstract ControllerComponent getController();
	
	/**
	 * Gets the configuration.
	 *
	 * @return the configuration
	 */
	protected final Module getConfiguration() {
		Module module= new Module();
		Map<String, Double> parameters = module.getParameters();
		saveActionMap.forEach((k,v) -> {
			parameters.put(k, v.get());
		});
		
		module.setFilename(getFilename());

		module.setPorts(getController().getAllPort());;
		module.setPosX(getPositionX());
		module.setPosY(getPositionY());
		return module;
	}

	/**
	 * Inits the component.
	 *
	 * @param module the module
	 */
	public void initComponent(Module module){
		loadActionMap.forEach((k, v)->{
			v.accept(module.getParameters().get(k));
		});
	}
	
	
	public abstract String getFilename();
	
	public Pair<DoubleProperty, DoubleProperty> getStuff(String inputName) {
		return cablesProperties.get(inputName);
	}
	
	public UnitPort getPort(String name) {
		UnitPort res = null;
		for(Port p : getController().getAllPort()) {
			if(p.getName().equals(name)) {
				return p.getUnitPort();
			}
		}
		return res;
	}

	public void setOnCloseCmd(ICommand cmd) {
		this.cmd = cmd;
	}

	public void printStuff() {
		Log.getInstance().error("Map = " + cablesProperties);
	}
	
}
