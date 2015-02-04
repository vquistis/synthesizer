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
 * <pre>
 * output = TODO
 * </pre>
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
	 * Frequency modulation 
	 * @return
	 */
	public UnitInputPort getInputFM() {
		return inputFM;
	}

	/**
	 * @return
	 */
	public UnitInputPort getInputF0() {
		return inputF0;
	}

	public UnitInputPort getInputShape() {
		return inputShape;
	}

	public UnitInputPort getInputOctave() {
		return inputOctave;
	}

	public UnitInputPort getInputAmplitude() {
		return inputAmplitude;
	}

	public UnitOutputPort getOutput()
	{
		return output;
	}
	
	public UnitOutputPort getOutputSquare() {
		return outputSquare;
	}

	public UnitOutputPort getOutputSawtooth() {
		return outputSawtooth;
	}

	public UnitOutputPort getOutputTriangle() {
		return outputTriangle;
	}
	
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
		inputFM = (UnitInputPort) addNamedPort(vcFreq.getInputfm(), "inputfm");
		inputF0 = (UnitInputPort) addNamedPort(vcFreq.getInputf0(), "inputf0");
		inputOctave = (UnitInputPort) addNamedPort(vcFreq.getInputOctave(), "inputOctave");
		inputShape = (UnitInputPort) addNamedPort(selectFrom3.getInputSelect(), "inputSelectShape");
		
		inputAmplitude = (UnitInputPort) addNamedPort(passThroughAmplitude.getInput(), "inputAmplitude");
		
		output = (UnitOutputPort) addNamedPort(selectFrom3.getOutput(), "outputAmplitude"); // fct sélecteur
		outputSquare = (UnitOutputPort) addNamedPort(squareOscillator.getOutput(), "outputSquareOscillator");
		outputSawtooth = (UnitOutputPort) addNamedPort(sawtoothOscillator.getOutput(), "outputSawtoothOscillator");
		outputTriangle = (UnitOutputPort) addNamedPort(triangleOscillator.getOutput(), "outputTriangleOscillator");
		
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
	 *
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
