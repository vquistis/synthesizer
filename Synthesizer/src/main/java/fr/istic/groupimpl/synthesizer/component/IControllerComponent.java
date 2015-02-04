package fr.istic.groupimpl.synthesizer.component;

import javafx.beans.property.DoubleProperty;

public interface IControllerComponent {

	/**
	 * Click listener for input ports
	 * @param portName Name of the clicked input port
	 * @param xCoord Coordinate of the clicked input port (x axis)
	 * @param yCoord Coordinate of the clicked input port (y axis)
	 */
	public void handleViewInputClick(String portName, DoubleProperty xCoord, DoubleProperty yCoord);
	

	/**
	 * Click listener for output ports
	 * @param portName Name of the clicked output port
	 * @param xCoord Coordinate of the clicked output port (x axis)
	 * @param yCoord Coordinate of the clicked output port (y axis)
	 */
	public void handleViewOutputClick(String portName, DoubleProperty xCoord, DoubleProperty yCoord);
	
	/**
	 * Click listener for close the component
	 */
	public void handleViewClose();
}
