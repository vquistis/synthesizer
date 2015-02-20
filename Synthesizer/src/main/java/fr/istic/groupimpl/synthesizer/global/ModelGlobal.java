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

/**
 * The Class ModelGlobal
 * 
 * @author Team groupImpl 
 *
 */
public class ModelGlobal {

	private List<UnitGenerator> unitGenerators = new ArrayList<UnitGenerator>();

	private Synthesizer synth;

	private Map<UnitOutputPort, UnitInputPort> outputConnections;

	private Map<UnitInputPort, UnitOutputPort> inputConnections;

	@SuppressWarnings("unused")
	private WaveRecorder recorder;

	/**
	 * Instantiates the underlying JSyn synthesizer and starts it.
	 */
	public ModelGlobal() {
		this.outputConnections = new HashMap<UnitOutputPort, UnitInputPort>();
		this.inputConnections = new HashMap<UnitInputPort, UnitOutputPort>();
		this.synth = JSyn.createSynthesizer();		
		this.synth.start( 44100, AudioDeviceManager.USE_DEFAULT_DEVICE, 2, 
				AudioDeviceManager.USE_DEFAULT_DEVICE,2);
	}

	/**
	 * Adds the given UnitGenerator to the underlying JSyn synthesizer.
	 * @param unitGen
	 */
	public void addUnitGenerator(UnitGenerator unitGen) {
		unitGenerators.add(unitGen);
		synth.add(unitGen);
	}

	/**
	 * Adds the given UnitGenerator as an output module to the underlying
	 * JSyn synthesizer.
	 * @param unitGen
	 */
	public void addOutUnit(UnitGenerator unitGen) {
		unitGenerators.add(unitGen);
		synth.add(unitGen);
		unitGen.start();
	}

	/**
	 * Removes the given UnitGenerator from the underlying JSyn synthesizer.
	 * @param unitGen
	 */
	public void removeUnitGenerator(UnitGenerator unitGen) {
		synth.remove(unitGen);
		unitGenerators.remove(unitGen);
	}

	public void removeOutUnitGenerator(UnitGenerator unitGen) {
		unitGen.stop();
		synth.remove(unitGen);
		unitGenerators.remove(unitGen);
	}

	public boolean isPortConnected(UnitPort port) {
		return inputConnections.containsKey(port) || outputConnections.containsKey(port);
	}

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
	 * @param outputPort
	 * @param inputPort
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
	 * @param port
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
	 * @param port
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
	 * @param outputPort
	 * @param inputPort
	 */
	private void putOutputConnection(UnitOutputPort outputPort, UnitInputPort inputPort) {
		outputConnections.put(outputPort, inputPort);
	}

	/**
	 * Adds an entry in the input connections map with the output port as a key
	 * and the output port as a value.
	 * @param inputPort
	 * @param outputPort
	 */
	private void putInputConnection(UnitInputPort inputPort, UnitOutputPort outputPort) {
		inputConnections.put(inputPort, outputPort);
	}

	/**
	 * Removes every connection originating from each port in the given list.
	 * @param unitports
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
	 * Stop Synthesizer of JSyn
	 */
	public void stopSynth(){
		try {
			if (synth.isRunning()) {
				synth.stop();
			}
		} catch (Exception e) {
		}				
	}
	
	public void recordWavTemp() throws IOException{
		File temp = File.createTempFile("tempfile", ".wav");
		recorder = new WaveRecorder( synth, temp, 0 );
	}
	
	/**
	 * to start Synthesizer of JSyn
	 */
	public void start(){
		this.synth.start();
	}
	
	/**
	 * to stop Synthesizer of JSyn
	 */
	public void stop(){
		synth.stop();
	}
	
	/**
	 * get Synthesizer
	 * @return
	 */
	public Synthesizer getSynth(){
		return synth;
	}
}
