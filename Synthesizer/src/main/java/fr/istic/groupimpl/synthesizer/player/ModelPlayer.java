package fr.istic.groupimpl.synthesizer.player;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.player.jsyn.PlayerGate;

/**
 * 
 * Model of Player module
 * 
 * @author Team GroupImpl
 *
 */
public class ModelPlayer extends ModelComponent {

	private PlayerGate player;
	private StringProperty sampleFileName = new SimpleStringProperty();
	
	private final double sigMin = 0.00001;
	private final double sigMax = 0.1;
	
	/**
	 * Constructor
	 */
	public ModelPlayer() {
		super();
		player = new PlayerGate(sigMin, sigMax);
		player.output.setName("player_output");
		player.getGate().setName("player_gate");
	}
	
	@Override
	public UnitGenerator getUnitGenerator() {
		return player;
	}

	/**
	 * Get the jsyn output port.
	 * @return UnitInputPort
	 */
	public UnitOutputPort getOutputPort() {
		return player.getOutput();
	}

	/**
	 * Get the sample file name.
	 * @return String
	 */
	public StringProperty getSampleFileName() {
		return sampleFileName;
	}
	
	@Override
	public Collection<UnitPort> getAllPorts() {
		return player.getPorts();
	}

	/**
	 * Load wav file sample
	 * 
	 * @param fileName
	 */
	public void loadSample(String fileName) {
		File sampleFile = new File( fileName);
		sampleFileName.set(sampleFile.getName());
		try {
			player.loadSample(sampleFile);
		} catch (IOException e) {
			sampleFileName.set(null);
		}
	}
	
	/**
	 * Play the sample
	 */
	public void play() {
		player.play();
	}

	/**
	 * Stop playing the sample
	 */
	public void stop() {
		player.stop();
	}

	public boolean isPlayRunning() {
		return player.dataQueue.hasMore();
	}
}