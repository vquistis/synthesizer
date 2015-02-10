package fr.istic.groupimpl.synthesizer.io.architecture;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class Connection.
 */
@XmlRootElement
public class Connection {

	/** The input port. */
	private String inputPort;
	
	/** The output port. */
	private String outputPort;

	/**
	 * Gets the input port.
	 *
	 * @return the input port
	 */
	public String getInputPort() {
		return inputPort;
	}

	/**
	 * Sets the input port.
	 *
	 * @param inputPort the new input port
	 */
	public void setInputPort(String inputPort) {
		this.inputPort = inputPort;
	}

	/**
	 * Gets the output port.
	 *
	 * @return the output port
	 */
	public String getOutputPort() {
		return outputPort;
	}

	/**
	 * Sets the output port.
	 *
	 * @param outputPort the new output port
	 */
	public void setOutputPort(String outputPort) {
		this.outputPort = outputPort;
	}

}
