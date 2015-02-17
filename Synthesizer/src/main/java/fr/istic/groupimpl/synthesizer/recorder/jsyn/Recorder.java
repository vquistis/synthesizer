package fr.istic.groupimpl.synthesizer.recorder.jsyn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.jsyn.data.FloatSample;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.UnitOscillator;
import com.jsyn.unitgen.VariableRateStereoReader;
import com.jsyn.util.SampleLoader;
import com.jsyn.util.WaveRecorder;

import fr.istic.groupimpl.synthesizer.util.SignalUtil;

public class Recorder extends Circuit {

	private UnitInputPort input; 
	private WaveRecorder recorder;
	private PassThrough passThroughInput;
	
	/**
	 * Constructor
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
	 * get the input port
	 * @return UnitInputPort
	 * the input port
	 */
	public UnitInputPort getInput() {
		return input;
	}

	public void start() {
		if (recorder != null) {
			recorder.start();
		};
	}

	public void stop() {
		if (recorder != null) {
			recorder.stop();
			try {
				recorder.close(); // close and flush
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void prepareFile(File sampleFile) {
		try {
			recorder = new WaveRecorder(synthesisEngine, sampleFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// connect InputPort
		passThroughInput.output.connect(recorder.getInput());
	}
	
	/**
	 * 
	 * add a named port to the circuit and return its instance
	 * 
	 * @param UnitPort
	 *   instance to add
	 * @param name
	 *   Port Name
	 * @return
	 *   Instance named port
	 */
	private UnitPort addNamedPort(UnitPort UnitPort, String name) {
		addPort(UnitPort, name);
		return getPortByName(name);
	}
 }
