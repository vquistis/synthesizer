package fr.istic.groupimpl.synthesizer.global;

import javax.sound.sampled.Port;

import fr.istic.groupimpl.synthesizer.component.IModelComponent;

public class ControllerGlobal {
	
	private ModelGlobal model;
	private ViewGlobal view;
	
	private CableMode cableMode;
	private IModelComponent currentModel;
	
	
	public void addComponentModel(IModelComponent model) {
		this.model.addModule(model);
	}

	public void handleConnectModules() {
		//TODO notify the view to bind the cable.
	}
	
	public void handleDisconnectModules() {
		//TODO notify the view to unbind the cable.
	}
	
	public void handlePortClicked(IModelComponent model, Port port) {
//		switch(cableMode) {
//		case NONE_CONNECTED:
//			if(port.isInput()) {
//				cableMode = CableMode.IN_CONNECTED;
//			} else {
//				cableMode = CableMode.OUT_CONNECTED;
//			}
//			currentModel = model;
//			//TODO notify the view to create a new, unbound cable.
//			break;
//		case IN_CONNECTED:
//			if(!port.isInput()) {
//				cableMode = CableMode.NONE_CONNECTED;
//				this.model.connectModules(model, port.getName(), currentModel, currentPort.getName());
//				currentModel = null; 
//			}
//			break;
//		case OUT_CONNECTED:
//			if(port.isInput()) {
//				cableMode = CableMode.NONE_CONNECTED;
//				this.model.connectModules(currentModel, currentPort.getName(), model, port.getName());
//				currentModel = null; 
//			}
//			break;
//		default:
//		}
	}
	
	public void handleLeftButtonClicked() {
		cableMode = CableMode.NONE_CONNECTED;
		currentModel = null;
		//TODO notify the view to stop displaying the cable.
	}
	
	private enum CableMode {
		NONE_CONNECTED,IN_CONNECTED,OUT_CONNECTED
	}

}
