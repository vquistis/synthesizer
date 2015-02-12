package fr.istic.groupimpl.synthesizer.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.beans.property.DoubleProperty;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitPort;

import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;
import fr.istic.groupimpl.synthesizer.io.architecture.Port;
import fr.istic.groupimpl.synthesizer.io.architecture.Type;

/**
 * The Class ControllerComponent.
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
	
	public void handlePortClicked(UnitPort port, DoubleProperty x, DoubleProperty y) {
		ControllerGlobal.getInstance().handlePortClicked(port, x, y);
	}
}
