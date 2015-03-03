package fr.istic.groupimpl.synthesizer.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitPort;

import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;
import fr.istic.groupimpl.synthesizer.io.architecture.Port;
import fr.istic.groupimpl.synthesizer.io.architecture.Type;

/**
 * The Class ControllerComponent.
 * 
 * @author Team groupImpl 
 */
public abstract class ControllerComponent {	
	
	/**
	 * Click listener for close the component.
	 */
	public  abstract void handleViewClose();
	

	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	public abstract ModelComponent getModel();
	
	/**
	 * Gets the all port.
	 *
	 * @return the all port
	 */
	public final List<Port> getAllPort(){
		List<Port> ports =new ArrayList<>();
		
		Collection<UnitPort>  list = getModel().getAllPorts();				
		
		for (UnitPort unitPort : list) {
			Port port = new Port();
			port.setName(unitPort.getName());
			
			if (unitPort instanceof UnitInputPort) {
				port.setType(Type.IN);
			} else {
				port.setType(Type.OUT);
			}
			port.setConnected(ControllerGlobal.getInstance().isPortConnected(unitPort));	
			port.setUnitPort(unitPort);
			ports.add(port);
		}		
		return ports;
	}
	
	/**
	 * Handle port clicked
	 * @param port the port jsyn
	 * @param x the property
	 * @param y the property
	 */
	public void handlePortClicked(UnitPort port, DoubleProperty x, DoubleProperty y) {
		ControllerGlobal.getInstance().handlePortClicked(port, x, y);
	}

	/**
	 * setup ports
	 * 
	 * @param portName the port name
	 * @param portNode the port node
	 * @param portX port x
	 * @param portY port y
	 */
	public void setupPort(String portName, Node portNode, DoubleProperty portX,	DoubleProperty portY) {
		getModel().getAllPorts().forEach((p) -> {				
			if(p.getName().equals(portName)) {
				portNode.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
					handlePortClicked(p, portX, portY);
				});
			}
		});
	}
}
