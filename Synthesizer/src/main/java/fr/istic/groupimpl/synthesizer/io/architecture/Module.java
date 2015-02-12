package fr.istic.groupimpl.synthesizer.io.architecture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class Module.
 */
@XmlRootElement(name="module")
public class Module {
	
	/** The id. */
	private String id;
	
	private String filename;
	
	/** The map. */
	private Map<String, Double> parameters;
	
	/** The ports. */
	private List<Port> ports;
	
	
	/** The pos x. */
	private int posX;
	
	/** The pos y. */
	private int posY;
	
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
	public Map<String, Double> getParameters() {
		return parameters;
	}

	/**
	 * Sets the parameters.
	 *
	 * @param parameters the parameters
	 */
	public void setParameters(Map<String, Double> parameters) {
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

	/**
	 * Gets the pos x.
	 *
	 * @return the pos x
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * Sets the pos x.
	 *
	 * @param posX the new pos x
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}

	/**
	 * Gets the pos y.
	 *
	 * @return the pos y
	 */
	public int getPosY() {
		return posY;
	}

	/**
	 * Sets the pos y.
	 *
	 * @param posY the new pos y
	 */
	public void setPosY(int posY) {
		this.posY = posY;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
}
