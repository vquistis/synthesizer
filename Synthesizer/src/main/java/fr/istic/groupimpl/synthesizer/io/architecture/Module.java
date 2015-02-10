package fr.istic.groupimpl.synthesizer.io.architecture;

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
	private Map<String, String> map;
	
	/** The ports. */
	private List<Port> ports;
	
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
	 * Gets the map.
	 *
	 * @return the map
	 */
	public Map<String, String> getMap() {
		return map;
	}
	
	/**
	 * Sets the map.
	 *
	 * @param map the map
	 */
	public void setMap(Map<String, String> map) {
		this.map = map;
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
