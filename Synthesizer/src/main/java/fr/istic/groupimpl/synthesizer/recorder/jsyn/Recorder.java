package fr.istic.groupimpl.synthesizer.recorder.jsyn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.util.WaveRecorder;

import fr.istic.groupimpl.synthesizer.logger.Log;

/**
 * Jsyn Recorder module.
 *
 * @author Team GroupImpl
 */
public class Recorder extends Circuit {

	/** The input. */
	private UnitInputPort input; 
	
	/** The recorder. */
	private WaveRecorder recorder;
	
	/** The pass through input. */
	private PassThrough passThroughInput;
	
	/**
	 * Constructor.
	 */
    public Recorder() {
		/*
		 * Create various unit generators and add them to circuit.
		 */
        add(passThroughInput = new PassThrough());
        
		/* Make ports on internal units appear as ports on circuit. */
		/* Optionally give some circuit ports more meaningful names. */	
		input = (UnitInputPort) addNamedPort(passThroughInput.getInput(), "recorder_input");
     }

	/**
	 * get the input port.
	 *
	 * @return UnitInputPort
	 * the input port
	 */
	public UnitInputPort getInput() {
		return input;
	}

	/**
	 * @see com.jsyn.unitgen.UnitGenerator#start()
	 */
	public void startRecording() {
		if (recorder != null) {
			recorder.start();
		};
	}

	/**
	 * @see com.jsyn.unitgen.UnitGenerator#stop()
	 */
	public void stopRecording() throws IOException {
		if (recorder != null) {
			recorder.stop();
			try {
				recorder.close(); // close and flush
			} catch (IOException e) {
				Log.getInstance().error("Failed to stop recording", e );
				throw e;
			}
		}
	}
	
	/**
	 * Prepare file.
	 *
	 * @param sampleFile the sample file
	 */
	public void prepareFile(File sampleFile) throws FileNotFoundException {
		try {
			recorder = new WaveRecorder(synthesisEngine, sampleFile);
			
			// connect InputPort
			passThroughInput.output.connect(recorder.getInput());
		} catch (FileNotFoundException e) {
			Log.getInstance().error("Failed to initialize the sample file", e );
			throw e;
		}
	}
	
	/**
	 * add a named port to the circuit and return its instance.
	 *
	 * @param UnitPort   instance to add
	 * @param name   Port Name
	 * @return   Instance named port
	 */
	private UnitPort addNamedPort(UnitPort UnitPort, String name) {
		addPort(UnitPort, name);
		return getPortByName(name);
	}
 }
