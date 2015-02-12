package fr.istic.groupimpl.synthesizer.global;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.beans.property.DoubleProperty;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.cable.Cable;
import fr.istic.groupimpl.synthesizer.io.architecture.Connection;
import fr.istic.groupimpl.synthesizer.io.architecture.Module;
import fr.istic.groupimpl.synthesizer.io.architecture.Port;
import fr.istic.groupimpl.synthesizer.logger.Log;

/**
 * The Class ControllerGlobal.
 */
public class ControllerGlobal {

	/** The instance. */
	private static ControllerGlobal instance;

	/**
	 * Gets the single instance of ControllerGlobal.
	 *
	 * @return single instance of ControllerGlobal
	 */
	public static ControllerGlobal getInstance() {
		if(instance == null) {
			instance = new ControllerGlobal();
		}
		return instance;
	}

	/** The model. */
	private ModelGlobal model;
	
	/** The view. */
	private ViewGlobal view;

	/** The interaction mode. */
	private InteractionMode interactionMode = InteractionMode.CableCreation_none;
	/*
	 * By default, when the user clicks on a port, a new cable is created.
	 * However, if the user clicks on an already connected port, the existing
	 * cable should be in a "move" state.
	 */
	/** The previous port. */
	private UnitPort previousPort;
	
	/** The previous x. */
	private DoubleProperty previousX;
	
	/** The previous y. */
	private DoubleProperty previousY;

	/** The current port. */
	private UnitPort currentPort;

	/** The cables. */
	private Map<UnitPort,Cable> cables = new HashMap<UnitPort,Cable>();

	/**
	 * Instantiates a new controller global.
	 */
	private ControllerGlobal() {
		model = new ModelGlobal();
	}

	/**
	 * Signals the model that the given UnitGenerator must be added
	 * to the synthesizer.
	 *
	 * @param unitGen the unit gen
	 */
	public void registerUnitGenerator(UnitGenerator unitGen) {
		model.addUnitGenerator(unitGen);
	}

	/**
	 * Register out unit generator.
	 *
	 * @param unitGen the unit gen
	 */
	public void registerOutUnitGenerator(UnitGenerator unitGen) {
		model.addOutUnit(unitGen);
	}

	/**
	 * Signals the model that the given UnitGenerator must be removed
	 * from the synthesizer.
	 *
	 * @param unitGen the unit gen
	 */
	public void unregisterUnitGenerator(UnitGenerator unitGen) {

		model.removeUnitGenerator(unitGen);
	}

	/**
	 * Unregister out unit generator.
	 *
	 * @param unitGen the unit gen
	 */
	public void unregisterOutUnitGenerator(UnitGenerator unitGen) {
		model.removeOutUnitGenerator(unitGen);
	}

	/**
	 * Signals the model that any connection originating from each port in
	 * the given collection must be removed.
	 *
	 * @param unitports the unitports
	 */
	public void removeAllConnections(Collection<UnitPort> unitports) {
		model.removeAllConnections(unitports);
		for(UnitPort p : unitports) {
			Cable c = cables.remove(p);
			if(c != null) {
				view.removeCable(c);
			}
		}
	}

	/**
	 * Handles the process of connection creation when an input port is clicked.
	 *
	 * @param port the port
	 * @param x the x
	 * @param y the y
	 */
	public void handleInputClicked(UnitInputPort port, DoubleProperty x, DoubleProperty y) {
		switch(interactionMode) {
		case CableDeletion:
			view.enableCableDeletionMode(false);
			view.enableCableCreationMode(true);
			doStuff(port, x, y);
			break;
		case CableCreation_none:
			doStuff(port, x, y);
			break;
		case CableCreation_out:
			/*
			 * An output port is connected to the cable. By clicking on an input port,
			 * we should ask the model whether this input port is already part of a connection
			 * or not. If it is, do nothing. If it isn't, create a new connection.
			 */
			if(!model.isPortConnected(port)) {
				interactionMode = InteractionMode.CableCreation_none;
				view.enableDefaultMode(true);
				this.model.connectPorts(currentPort, port);

				//----------------------
				Cable cable = cables.get(currentPort);
				cables.put(port,cable);
				cable.bindInput(x, y);
				Log.getInstance().debug("input X = " + x.get() + " ; " + "input Y = " + y.get());
				Log.getInstance().debug("INPUT PORT CONNECTED TO OUTPUT PORT");
				//----------------------

				currentPort = null;
				previousPort = null;
				previousX = null;
				previousY = null;
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Handles the process of connection creation when an output port is clicked.
	 *
	 * @param port the port
	 * @param x the x
	 * @param y the y
	 */
	public void handleOutputClicked(UnitOutputPort port, DoubleProperty x, DoubleProperty y) {
		switch(interactionMode) {
		case CableDeletion:
			view.enableCableDeletionMode(false);
			view.enableCableCreationMode(true);
			doStuff(port, x, y);
			break;
		case CableCreation_none:
			doStuff(port, x, y);
			break;
		case CableCreation_in:
			if(!model.isPortConnected(port)) {
				interactionMode = InteractionMode.CableCreation_none;
				view.enableDefaultMode(true);
				this.model.connectPorts(port, currentPort);

				//----------------------
				Cable cable = cables.get(currentPort);
				cables.put(port,cable);
				cable.bindOutput(x, y);
				Log.getInstance().debug("input X = " + x.get() + " ; " + "input Y = " + y.get());
				//----------------------

				currentPort = null;
				previousPort = null;
				previousX = null;
				previousY = null;
			}
			break;
		default:
			break;
		}
	}
	
	/*
	 * No connection is currently in the process of creation.
	 * If the put port that was clicked is already part of a connection,
	 * we should enter in a "move" move:
	 *   - set the "previousPort" field to remember which port was connected, 
	 *   in order to restore the connection in case of cancellation,
	 *   - set the "currentPort" field to the port currently connected to the
	 *   parameter "port",
	 *   - disconnect the currentPort and the previousPort in the model.
	 *   - change mode accordingly.
	 * If not, we should enter in a "create" mode.
	 */
	/**
	 * Do stuff.
	 *
	 * @param port the port
	 * @param x the x
	 * @param y the y
	 */
	private void doStuff(UnitPort port, DoubleProperty x, DoubleProperty y) {
		boolean isInput = port instanceof UnitInputPort;
		view.enableCableCreationMode(true);
		if(model.isPortConnected(port)) {
			interactionMode = isInput ? InteractionMode.CableCreation_out : InteractionMode.CableCreation_in;
			currentPort = model.getConnectedPort(port);
			if(isInput) {
				model.disconnectInputPort((UnitInputPort) port);
			} else {
				model.disconnectOutputPort((UnitOutputPort) port);
			}
			previousPort = port;
			previousX = x;
			previousY = y;

			//----------------------
			Cable cable = cables.get(port);
			cables.remove(port);
			if(isInput) {
				cable.bindInput(view.mouseXProperty(), view.mouseYProperty());
				Log.getInstance().debug("INPUT PORT DISCONNECTED");
			} else {
				cable.bindOutput(view.mouseXProperty(), view.mouseYProperty());
				Log.getInstance().debug("OUTPUT PORT DISCONNECTED");
			}
			//----------------------

		} else {
			interactionMode = isInput ? InteractionMode.CableCreation_in : InteractionMode.CableCreation_out;
			currentPort = port;

			//----------------------
			Cable cable = new Cable(view.getCableColor());
			cables.put(port, cable);
			if(isInput) {
				cable.bindInput(x, y);
				Log.getInstance().debug("input X = " + x.get() + " ; " + "input Y = " + y.get());
				cable.bindOutput(view.mouseXProperty(), view.mouseYProperty());
				Log.getInstance().debug("CREATING CABLE FROM INPUT PORT");
			} else {
				cable.bindOutput(x, y);
				Log.getInstance().debug("output X = " + x.get() + " ; " + "output Y = " + y.get());
				cable.bindInput(view.mouseXProperty(), view.mouseYProperty());
				Log.getInstance().debug("CREATING CABLE FROM OUTPUT PORT");
			}
			view.addCable(cable);
			//----------------------
		}
	}

	/**
	 * Aborts the connection creation process.
	 */
	public void handleRightButtonClicked() {
		switch(interactionMode) {
		case CableCreation_in:
		case CableCreation_out:
			cancelCableCreation();
			break;
		case CableDeletion:
			view.enableCableDeletionMode(false);
			break;
		case CablePainting:
			view.enableCablePaintingMode(false);
			break;
		default:
			break;
		}
		view.enableDefaultMode(true);
		interactionMode = InteractionMode.CableCreation_none;
	}

	/**
	 * Cancel cable creation.
	 */
	private void cancelCableCreation() {
		Cable cable = cables.get(currentPort);
		if(cable != null) {
			if(previousPort != null) {
				cables.put(previousPort,cable);
				if(previousPort instanceof UnitInputPort) {
					cable.bindInput(previousX, previousY);
					this.model.connectPorts(currentPort, previousPort);
				} else {
					cable.bindOutput(previousX, previousY);
					this.model.connectPorts(previousPort, currentPort);
				}
			} else {
				cables.remove(currentPort);
				view.removeCable(cable);
			}
		}
		currentPort = null;
		previousPort = null;
		previousX = null;
		previousY = null;
	}

	/**
	 * Handle click on cable.
	 *
	 * @param cable the cable
	 */
	public void handleClickOnCable(Cable cable) {
		switch(interactionMode) {
		case CableDeletion:
			view.removeCable(cable);
			cables.forEach((k,v) -> {
				if(v == cable) {
					if(k instanceof UnitInputPort) {
						model.disconnectInputPort((UnitInputPort) k);
					} else if(k instanceof UnitOutputPort) {
						model.disconnectOutputPort((UnitOutputPort) k);
					}
				}
			});
			break;
		case CablePainting:
			cable.setStroke(view.getCableColor());
			break;
		default:
			break;
		}
			
	}

	/**
	 * Sets the view.
	 *
	 * @param view the new view
	 */
	public void setView(ViewGlobal view) {
		this.view = view;
	}

	/**
	 * Activate deletion mode.
	 */
	public void activateDeletionMode() {
		switch(interactionMode) {
		case CableCreation_none:
			view.enableCableDeletionMode(true);
			break;
		case CableCreation_in:
			cancelCableCreation();
			view.enableCableDeletionMode(true);
			break;
		case CableCreation_out:
			cancelCableCreation();
			view.enableCableDeletionMode(true);
			break;
		case CablePainting:
			view.enableCableDeletionMode(true);
			break;
		default:
			break;
		}
		interactionMode = InteractionMode.CableDeletion;
	}

	/**
	 * Activate painting mode.
	 */
	public void activatePaintingMode() {
		switch(interactionMode) {
		case CableCreation_none:
			view.enableCablePaintingMode(true);
			break;
		case CableCreation_in:
			cancelCableCreation();
			view.enableCablePaintingMode(true);
			break;
		case CableCreation_out:
			cancelCableCreation();
			view.enableCablePaintingMode(true);
			break;
		case CablePainting:
			view.enableCablePaintingMode(true);
			break;
		default:
			break;
		}
		interactionMode = InteractionMode.CablePainting;
	}
	
	/**
	 * Checks if is port connected.
	 *
	 * @param port the port
	 * @return true, if is port connected
	 */
	public boolean isPortConnected(UnitPort port){
		return model.isPortConnected(port);
	}
	
	/**
	 * The Enum InteractionMode.
	 */
	private enum InteractionMode {
		
		/** The Cable creation_none. */
		CableCreation_none,
		/** The Cable creation_in. */
		CableCreation_in,
		/** The Cable creation_out. */
		CableCreation_out,
		/** The Cable deletion. */
		CableDeletion,
		/** The Cable painting. */
		CablePainting
	}
	
	
	/**
	 * Gets the port.
	 *
	 * @param unitPort the unit port
	 * @return the port
	 */
	public Port getPort(UnitPort unitPort){
		view.getModules();
		List<Module> modules = view.getModules();
		for (Module module : modules) {
			List<Port> ports = module.getPorts();
			for (Port port : ports) {
				if (port.getUnitPort().equals(unitPort)) {
					return port;
				}
			}
		}
		return null;
	}
	
	/**
	 * Gets the module.
	 *
	 * @param unitPort the unit port
	 * @return the module
	 */
	public Module getModule(UnitPort unitPort){
		List<Module> modules = view.getModules();
		for (Module module : modules) {
			List<Port> ports = module.getPorts();
			for (Port port : ports) {
				if (port.getUnitPort().equals(unitPort)) {
					return module;
				}
			}
		}
		return null;
	}
	
	/**
	 * Gets the connection list.
	 *
	 * @return the connection list
	 */
	public List<Connection> getConnectionList(){
		List<Connection> connections =new ArrayList<Connection>();
		
		Set<UnitPort> unitPorts = cables.keySet();
		for (UnitPort unitPort : unitPorts) {
			Connection connection =new Connection();
			if (unitPort instanceof UnitInputPort) {
				UnitPort portConnected = model.getConnectedPort(unitPort);
				if (portConnected != null) {
					Port portInput = getPort(unitPort);
					Port portOutput = getPort(portConnected);					
					connection.setInputPort(portInput);					
					connection.setOutputPort(portOutput);
					connection.getInputPort().setIdModule(getModule(unitPort).getId());
					connection.getOutputPort().setIdModule(getModule(portConnected).getId());
					connection.setColor(cables.get(unitPort).getStroke().toString());
					connections.add(connection);
				}				
			}			
		}
		return connections;
	}
	
	public void clearAllComponent(){
		model.stopSynth();
		Set<UnitPort> set= cables.keySet();
		for (UnitPort unitPort : set) {
			Cable cable=cables.get(unitPort);
			view.removeCable(cable);
		}
		model = new ModelGlobal();
	}
}
