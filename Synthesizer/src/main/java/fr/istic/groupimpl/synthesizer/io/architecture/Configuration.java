package fr.istic.groupimpl.synthesizer.io.architecture;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class Configuration.
 */
@XmlRootElement
public class Configuration {
	
	/** The modules. */
	private Collection<Module> modules;
	
	/** The connections. */
	private Collection<Connection> connections;
	
	
	public Configuration() {
		modules = new ArrayList<Module>();
		connections = new ArrayList<Connection>();
	}

	/**
	 * Gets the modules.
	 *
	 * @return the modules
	 */
	@XmlElement(name="modules")
	public Collection<Module> getModules() {
		return modules;
	}

	/**
	 * Sets the modules.
	 *
	 * @param modules the new modules
	 */
	public void setModules(Collection<Module> modules) {
		this.modules = modules;
	}

	/**
	 * Gets the connections.
	 *
	 * @return the connections
	 */
	 @XmlElement(name="connections")
	public Collection<Connection> getConnections() {
		return connections;
	}

	/**
	 * Sets the connections.
	 *
	 * @param connections the new connections
	 */
	public void setConnections(Collection<Connection> connections) {
		this.connections = connections;
	}
	
	/**
	 * Adds the module.
	 *
	 * @param module the module
	 */
	public void addModule(Module module){
		modules.add(module);
	}
	
	/**
	 * Removes the module.
	 *
	 * @param module the module
	 */
	public void removeModule(Module module){
		modules.remove(module);
	}
	
	/**
	 * Adds the connection.
	 *
	 * @param connection the connection
	 */
	public void addConnection(Connection connection){
		connections.add(connection);
	}
	
	/**
	 * Removes the connection.
	 *
	 * @param connection the connection
	 */
	public void removeConnection(Connection connection){
		connections.remove(connection);
	}
}
