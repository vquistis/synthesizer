package fr.istic.groupimpl.synthesizer.io.architecture;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.jsyn.ports.UnitPort;

/**
 * The Class Port.
 */
@XmlRootElement(name="port")
public class Port {
	
	/** The name. */
	private String name;
	
	/** The id module. */
	private String idModule;

	/** The type. */
	private Type type;
	
	/** The connected. */
	private boolean connected;
	
	/** The unit port. */
	private UnitPort unitPort;

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

	/**
	 * Gets the unit port.
	 *
	 * @return the unit port
	 */
	@XmlTransient
	public UnitPort getUnitPort() {
		return unitPort;
	}

	/**
	 * Sets the unit port.
	 *
	 * @param unitPort the new unit port
	 */
	public void setUnitPort(UnitPort unitPort) {
		this.unitPort = unitPort;
	}
	
	/**
	 * Gets the id module.
	 *
	 * @return the id module
	 */
	public String getIdModule() {
		return idModule;
	}

	/**
	 * Sets the id module.
	 *
	 * @param idModule the new id module
	 */
	public void setIdModule(String idModule) {
		this.idModule = idModule;
	}
	
	
}
