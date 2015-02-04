package fr.istic.groupimpl.synthesizer.component;

import javafx.beans.property.DoubleProperty;

public interface IControllerComponent {
	/**
	 * Click listener for input port.
	 * @param portName - name of clicked port
	 */
	public void handleViewInputClick(String portName, DoubleProperty xCoord, DoubleProperty yCoord);
	
	/**
	 * Click listener for output port.
	 * @param portName - name of clicked port
	 */
	public void handleViewOutputClick(String portName, DoubleProperty xCoord, DoubleProperty yCoord);
	
	/**
	 * Click listener for close the component.
	 */
	public void handleViewClose();
}
