package fr.istic.groupimpl.synthesizer.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitPort;

import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;
import fr.istic.groupimpl.synthesizer.io.architecture.Port;
import fr.istic.groupimpl.synthesizer.io.architecture.Type;


public abstract class ControllerComponent {	
	/**
	 * Click listener for close the component
	 */
	public  abstract void handleViewClose();
	

	public abstract ModelComponent getModel();
	
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
			ports.add(port);
		}		
		return ports;
	}
}
