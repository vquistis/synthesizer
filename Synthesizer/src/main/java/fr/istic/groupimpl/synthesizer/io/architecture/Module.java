package fr.istic.groupimpl.synthesizer.io.architecture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class Module.
 */
@XmlRootElement
public class Module {
	
	/** The id. */
	private String id;
	
	/** The map. */
	private Map<String, String> parameters;
	
	/** The ports. */
	private List<Port> ports;
	
	/**
	 * Create a new module.
	 */
	public Module() {
		parameters = new HashMap<>();
		ports = new ArrayList<>();
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the parameters.
	 *
	 * @return the parameters
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}

	/**
	 * Sets the parameters.
	 *
	 * @param parameters the parameters
	 */
	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	/**
	 * Gets the ports.
	 *
	 * @return the ports
	 */
	public List<Port> getPorts() {
		return ports;
	}
	
	/**
	 * Sets the ports.
	 *
	 * @param ports the new ports
	 */
	public void setPorts(List<Port> ports) {
		this.ports = ports;
	}
}
