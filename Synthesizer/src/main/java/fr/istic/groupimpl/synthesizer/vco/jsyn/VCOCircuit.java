package fr.istic.groupimpl.synthesizer.vco.jsyn;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.SawtoothOscillator;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.TriangleOscillator;
import com.jsyn.unitgen.UnitSource;

/**
 * VCO oscillator Circuit with a frequency modulation input.
 * 
 * UnitGenerator included :
 * - TriangleOscillator
 * - SawtoothOscillator
 * - SquareOscillator
 * - VCFrequency
 * - SelectFrom3Input
 * - PassThrough
 * 
 * @author GoupImpl
 */

public class VCOCircuit extends Circuit implements UnitSource
{
	/* Declare units that will be part of the circuit. */
	private TriangleOscillator triangleOscillator;
	private SawtoothOscillator sawtoothOscillator;
	private SquareOscillator squareOscillator;
	private VCFrequency vcFreq;
	private SelectFrom3Input selectFrom3;
	private PassThrough passThroughAmplitude;
	
	/* Declare ports. */
	private UnitInputPort inputFM;
	private UnitInputPort inputF0;
	private UnitInputPort inputOctave;
	private UnitInputPort inputShape;
	private UnitInputPort inputAmplitude;
	private UnitOutputPort output; // fonction du sélecteur de la forme (shape)
	
	private UnitOutputPort outputSquare;
	private UnitOutputPort outputSawtooth;
	private UnitOutputPort outputTriangle;

	/**
	 * Frequency modulation input (Volt)
	 * @return
	 */
	public UnitInputPort getInputFM() {
		return inputFM;
	}

	/**
	 * Default Frequency input (Hz)
	 * @return
	 */
	public UnitInputPort getInputF0() {
		return inputF0;
	}

	/**
	 * Select shape input (1 | 2 | 3)
	 * - 1 triangleOscillator
	 * - 2 sawtoothOscillator
	 * - 3 squareOscillator
	 * @return
	 */
	public UnitInputPort getInputShape() {
		return inputShape;
	}

	/**
	 * Octave input
	 * @return
	 */
	public UnitInputPort getInputOctave() {
		return inputOctave;
	}

	/**
	 * Amplitude input
	 * @return
	 */
	public UnitInputPort getInputAmplitude() {
		return inputAmplitude;
	}

	/** 
	 * Output of the selected shape
	 * @return
	 */
	public UnitOutputPort getOutput()
	{
		return output;
	}
	
	/**
	 * Output Square shape
	 * @return
	 */
	public UnitOutputPort getOutputSquare() {
		return outputSquare;
	}

	/**
	 * Output Sawtooth shape
	 * @return
	 */
	public UnitOutputPort getOutputSawtooth() {
		return outputSawtooth;
	}

	/**
	 * Output Triangle shape
	 * @return
	 */
	public UnitOutputPort getOutputTriangle() {
		return outputTriangle;
	}
	
	/**
	 * Constructor
	 */
	public VCOCircuit()
	{
		/*
		 * Create various unit generators and add them to circuit.
		 */
		add(triangleOscillator = new TriangleOscillator());
		add(sawtoothOscillator = new SawtoothOscillator());
		add(squareOscillator = new SquareOscillator());

		add(vcFreq = new VCFrequency());
		add(selectFrom3 = new SelectFrom3Input());
		add(passThroughAmplitude = new PassThrough());
		
		/* Make ports on internal units appear as ports on circuit. */
		/* Optionally give some circuit ports more meaningful names. */	
		inputFM = (UnitInputPort) addNamedPort(vcFreq.getInputfm(), "vco_inputFm");
		inputF0 = (UnitInputPort) addNamedPort(vcFreq.getInputf0(), "inputf0");
		inputOctave = (UnitInputPort) addNamedPort(vcFreq.getInputOctave(), "inputOctave");
		inputShape = (UnitInputPort) addNamedPort(selectFrom3.getInputSelect(), "inputSelectShape");
		
		inputAmplitude = (UnitInputPort) addNamedPort(passThroughAmplitude.getInput(), "inputAmplitude");
		
		output = (UnitOutputPort) addNamedPort(selectFrom3.getOutput(), "outputAmplitude"); // fct sélecteur
		outputSquare = (UnitOutputPort) addNamedPort(squareOscillator.getOutput(), "vco_outputSquare");
		outputSawtooth = (UnitOutputPort) addNamedPort(sawtoothOscillator.getOutput(), "vco_outputSawTooth");
		outputTriangle = (UnitOutputPort) addNamedPort(triangleOscillator.getOutput(), "vco_outputTriangle");
		
		/* Connect SynthUnits to make control signal path. */
		vcFreq.getOutput().connect(triangleOscillator.frequency);
		vcFreq.getOutput().connect(sawtoothOscillator.frequency);
		vcFreq.getOutput().connect(squareOscillator.frequency);
		 
		passThroughAmplitude.output.connect(triangleOscillator.amplitude);
		passThroughAmplitude.output.connect(sawtoothOscillator.amplitude);
		passThroughAmplitude.output.connect(squareOscillator.amplitude);
		
		triangleOscillator.output.connect(selectFrom3.getInput1());
		sawtoothOscillator.output.connect(selectFrom3.getInput2());
		squareOscillator.output.connect(selectFrom3.getInput3());	
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
