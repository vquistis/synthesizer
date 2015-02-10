package fr.istic.groupimpl.synthesizer.vca;

import javafx.beans.property.DoubleProperty;
import fr.istic.groupimpl.synthesizer.component.IControllerComponent;
import fr.istic.groupimpl.synthesizer.global.ControllerGlobal;

/**
 * The Class ControllerVca.
 */
public class ControllerVca implements IControllerComponent {
	
	/** The model vca. */
	private ModelVca modelVca;
	
	/** The ctrl glob. */
	private ControllerGlobal ctrlGlob;
	
	/**
	 * Instantiates a new controller vca.
	 *
	 * @param uiFreqLabel the ui freq label
	 */
	public ControllerVca() {
		modelVca = new ModelVca();
		ctrlGlob = ControllerGlobal.getInstance();
		ctrlGlob.registerUnitGenerator(modelVca.getUnitGenerator());
	}
		

	/**
	 * Click listener for input ports
	 * @param portName Name of the clicked input port
	 * @param xCoord Coordinate of the clicked input port (x axis)
	 * @param yCoord Coordinate of the clicked input port (y axis)
	 */
	public void handleViewInputClick(String portName, DoubleProperty xCoord, DoubleProperty yCoord) {
		ctrlGlob.handleInputClicked(modelVca.getInputPort(portName), xCoord, yCoord);
	}

	/**
	 * Click listener for output ports
	 * @param portName Name of the clicked output port
	 * @param xCoord Coordinate of the clicked output port (x axis)
	 * @param yCoord Coordinate of the clicked output port (y axis)
	 */
	public void handleViewOutputClick(DoubleProperty xCoord, DoubleProperty yCoord) {
		ctrlGlob.handleOutputClicked(modelVca.getOutputPort(), xCoord, yCoord);
	}

	/* (non-Javadoc)
	 * @see fr.istic.groupimpl.synthesizer.component.IControllerComponent#handleViewClose()
	 */
	@Override
	public void handleViewClose() {
		ctrlGlob.removeAllConnections(modelVca.getAllPorts());
		ctrlGlob.unregisterUnitGenerator(modelVca.getUnitGenerator());
	}

	/**
	 * Handle view volt amplitude change.
	 *
	 * @param volt the volt
	 * @param precision the precision
	 */
	public void handleViewVoltChange(double volt) {
		modelVca.setVolt(volt);
	}
}
