package fr.istic.groupimpl.synthesizer.io.architecture;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class Configuration.
 */
@XmlRootElement
public class Configuration {
	
	/** The modules. */
	private List<Module> modules;
	
	/** The connections. */
	private List<Connection> connections;

	/**
	 * Gets the modules.
	 *
	 * @return the modules
	 */
	public List<Module> getModules() {
		return modules;
	}

	/**
	 * Sets the modules.
	 *
	 * @param modules the new modules
	 */
	public void setModules(List<Module> modules) {
		this.modules = modules;
	}

	/**
	 * Gets the connections.
	 *
	 * @return the connections
	 */
	public List<Connection> getConnections() {
		return connections;
	}

	/**
	 * Sets the connections.
	 *
	 * @param connections the new connections
	 */
	public void setConnections(List<Connection> connections) {
		this.connections = connections;
	}
}
