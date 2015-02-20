package fr.istic.groupimpl.synthesizer.io.architecture;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class Connection.
 * 
 * @author Team groupImpl
 * 
 */
@XmlRootElement(name="connection")
public class Connection {

	/** The input port. */
	private Port inputPort;
	
	/** The output port. */
	private Port outputPort;
	
	/** The color. */
	private String color;

	/**
	 * Gets the input port.
	 *
	 * @return the input port
	 */
	public Port getInputPort() {
		return inputPort;
	}

	/**
	 * Sets the input port.
	 *
	 * @param inputPort the new input port
	 */
	public void setInputPort(Port inputPort) {
		this.inputPort = inputPort;
	}

	/**
	 * Gets the output port.
	 *
	 * @return the output port
	 */
	public Port getOutputPort() {
		return outputPort;
	}

	/**
	 * Sets the output port.
	 *
	 * @param outputPort the new output port
	 */
	public void setOutputPort(Port outputPort) {
		this.outputPort = outputPort;
	}

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Sets the color.
	 *
	 * @param color the new color
	 */
	public void setColor(String color) {
		this.color = color;
	}
}
