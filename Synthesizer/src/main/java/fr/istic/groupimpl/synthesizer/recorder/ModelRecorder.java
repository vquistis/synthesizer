package fr.istic.groupimpl.synthesizer.recorder;

import java.io.File;
import java.util.Collection;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.recorder.jsyn.Recorder;

/**
 * 
 * Model of Recorder module
 * 
 * @author Team GroupImpl
 *
 */
public class ModelRecorder extends ModelComponent {

	private Recorder recorder;
	private StringProperty sampleFileName = new SimpleStringProperty();
	
	/**
	 * Constructor
	 */
	public ModelRecorder() {
		super();
		recorder = new Recorder();
		recorder.getInput().setName("player_input");
	}
	
	@Override
	public UnitGenerator getUnitGenerator() {
		return recorder;
	}

	/**
	 * Get the jsyn input port.
	 * @return UnitInputPort
	 */
	public UnitInputPort getInputPort() {
		return recorder.getInput();
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
		return recorder.getPorts();
	}

	/**
	 * Prepare Wave file
	 * 
	 * @param fileName
	 */
	public void prepareFile(String fileName) {
		File sampleFile = new File( fileName );
		sampleFileName.set(sampleFile.getName());
		recorder.prepareFile(sampleFile);
	}
	
	/**
	 * Start recording
	 */
	public void start() {
		recorder.start();
	}

	/**
	 * Stop recording
	 */
	public void stop() {
		recorder.stop();
	}
}