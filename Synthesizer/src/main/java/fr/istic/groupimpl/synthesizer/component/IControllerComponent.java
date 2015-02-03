package fr.istic.groupimpl.synthesizer.component;

public interface IControllerComponent {
	/**
	 * Click listener for input port.
	 * @param portName - name of clicked port
	 */
	public void handleViewInputClick(String portName);
	
	/**
	 * Click listener for output port.
	 * @param portName - name of clicked port
	 */
	public void handleViewOutputClick(String portName);
	
	/**
	 * Click listener for close the component.
	 */
	public void handleViewClose();
}
