package fr.istic.groupimpl.synthesizer.io.architecture;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class Port.
 */
@XmlRootElement
public class Port {
	
	/** The name. */
	private String name;
	
	/** The type. */
	private Type type;
	
	/** The connected. */
	private boolean connected;

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * Checks if is connected.
	 *
	 * @return true, if is connected
	 */
	public boolean isConnected() {
		return connected;
	}

	/**
	 * Sets the connected.
	 *
	 * @param connected the new connected
	 */
	public void setConnected(boolean connected) {
		this.connected = connected;
	}
}
