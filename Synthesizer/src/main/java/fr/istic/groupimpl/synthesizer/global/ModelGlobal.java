package fr.istic.groupimpl.synthesizer.global;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.devices.AudioDeviceManager;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;
import com.jsyn.util.WaveRecorder;

import fr.istic.groupimpl.synthesizer.io.architecture.Module;
import fr.istic.groupimpl.synthesizer.io.architecture.Port;

/**
 * The Class ModelGlobal.
 */
public class ModelGlobal {

	/** The unit generators. */
	private List<UnitGenerator> unitGenerators = new ArrayList<UnitGenerator>();

	/** The synth. */
	private Synthesizer synth;

	/** The output connections. */
	private Map<UnitOutputPort, UnitInputPort> outputConnections;

	/** The input connections. */
	private Map<UnitInputPort, UnitOutputPort> inputConnections;

	/** The recorder. */
	private WaveRecorder recorder;

	/**
	 * Instantiates the underlying JSyn synthesizer and starts it.
	 */
	public ModelGlobal() {
		this.outputConnections = new HashMap<UnitOutputPort, UnitInputPort>();
		this.inputConnections = new HashMap<UnitInputPort, UnitOutputPort>();
		
		this.synth = JSyn.createSynthesizer();	
		synth.setRealTime( true );
		this.synth.start( 44100, AudioDeviceManager.USE_DEFAULT_DEVICE, 2, 
				AudioDeviceManager.USE_DEFAULT_DEVICE,2);
		
	}

	/**
	 * Adds the given UnitGenerator to the underlying JSyn synthesizer.
	 *
	 * @param unitGen the unit gen
	 */
	public void addUnitGenerator(UnitGenerator unitGen) {
		unitGenerators.add(unitGen);
		synth.add(unitGen);
	}

	/**
	 * Adds the given UnitGenerator as an output module to the underlying
	 * JSyn synthesizer.
	 *
	 * @param unitGen the unit gen
	 */
	public void addOutUnit(UnitGenerator unitGen) {
		unitGenerators.add(unitGen);
		synth.add(unitGen);
		unitGen.start();
	}

	/**
	 * Removes the given UnitGenerator from the underlying JSyn synthesizer.
	 *
	 * @param unitGen the unit gen
	 */
	public void removeUnitGenerator(UnitGenerator unitGen) {
		synth.remove(unitGen);
		unitGenerators.remove(unitGen);
	}

	/**
	 * Removes the out unit generator.
	 *
	 * @param unitGen the unit gen
	 */
	public void removeOutUnitGenerator(UnitGenerator unitGen) {
		unitGen.stop();
		synth.remove(unitGen);
		unitGenerators.remove(unitGen);
	}

	/**
	 * Checks if is port connected.
	 *
	 * @param port the port
	 * @return true, if is port connected
	 */
	public boolean isPortConnected(UnitPort port) {
		return inputConnections.containsKey(port) || outputConnections.containsKey(port);
	}

	/**
	 * Gets the connected port.
	 *
	 * @param port the port
	 * @return the connected port
	 */
	public UnitPort getConnectedPort(UnitPort port) {
		UnitPort res = null;
		if(inputConnections.containsKey(port)) {
			res = inputConnections.get(port);
		} else if(outputConnections.containsKey(port)) {
			res = outputConnections.get(port);
		}		
		return res;
	}

	/**
	 * Connects the two given output and input ports.
	 *
	 * @param outputPort the output port
	 * @param inputPort the input port
	 */
	public void connectPorts(UnitPort outputPort, UnitPort inputPort) {
		UnitOutputPort out = (UnitOutputPort) outputPort;
		UnitInputPort in = (UnitInputPort) inputPort;
		out.connect(in);
		putOutputConnection(out, in);
		putInputConnection(in, out);
	}

	/**
	 * Retrieves the output port connected to the given input port
	 * and disconnects them.
	 *
	 * @param port the port
	 */
	public void disconnectInputPort(UnitInputPort port) {
		UnitOutputPort out = inputConnections.get(port);
		if(out != null) {
			out.disconnect(port);
			inputConnections.remove(port);
			outputConnections.remove(out);
		}
	}

	/**
	 * Retrieves the input port connected to the given output port
	 * and disconnects them.
	 *
	 * @param port the port
	 */
	public void disconnectOutputPort(UnitOutputPort port) {
		UnitInputPort in = outputConnections.get(port);
		if(in != null) {
			port.disconnect(in);
			inputConnections.remove(in);
			outputConnections.remove(port);
		}
	}

	/**
	 * Adds an entry in the output connexions map with the output port as a key
	 * and the input port as a value.
	 *
	 * @param outputPort the output port
	 * @param inputPort the input port
	 */
	private void putOutputConnection(UnitOutputPort outputPort, UnitInputPort inputPort) {
		outputConnections.put(outputPort, inputPort);
	}

	/**
	 * Adds an entry in the input connections map with the output port as a key
	 * and the output port as a value.
	 *
	 * @param inputPort the input port
	 * @param outputPort the output port
	 */
	private void putInputConnection(UnitInputPort inputPort, UnitOutputPort outputPort) {
		inputConnections.put(inputPort, outputPort);
	}

	/**
	 * Removes every connection originating from each port in the given list.
	 *
	 * @param unitports the unitports
	 */
	public void removeAllConnections(Collection<UnitPort> unitports) {
		unitports.forEach((p1) -> {
			if(inputConnections.containsKey(p1)) {
				disconnectInputPort((UnitInputPort) p1);
			} else if(outputConnections.containsKey(p1)) {
				disconnectOutputPort((UnitOutputPort) p1);
			}
		});
	}
	
	/**
	 * Stop synth.
	 */
	public void stopSynth(){
		if (synth.isRunning()) {
			synth.stop();
		}		
	}
	
	/**
	 * Record wav file.
	 *
	 * @param file the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void recordWavFile( File file) throws IOException{
		recorder = new WaveRecorder(synth, file);
	}
	
	/**
	 * Start synth.
	 */
	public void startSynth(){
		this.synth.start();
	}
	
	/**
	 * Start recoder.
	 * 
	 * @param moduleOut the module out
	 */
	public void startRecoder(List<Module> moduleOut){
		for (int i = 0; i < moduleOut.size(); i++) {
			Module module = moduleOut.get(i);
			
			if (module.getParameters().get("muteVolumeFx") == 0) {
				List<Port> ports = module.getPorts();
				for (Port port : ports) {
					UnitPort unitPort = port.getUnitPort();
					if (unitPort instanceof UnitInputPort) {
						UnitInputPort unitIntputPort = (UnitInputPort)unitPort;
						UnitOutputPort unitOutputPort = (UnitOutputPort) getConnectedPort(unitIntputPort);
						if (unitOutputPort != null) {
							unitOutputPort.connect(0, recorder.getInput(), 0 );
						}					
					}
				}
			}			
		}
		this.recorder.start();
	}
	
	/**
	 * Stop recorder.
	 */
	public void stopRecorder(){
		try {
			recorder.stop();
			recorder.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the synth.
	 *
	 * @return the synth
	 */
	public Synthesizer getSynth(){
		return synth;
	}
}
