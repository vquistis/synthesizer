package fr.istic.groupimpl.synthesizer.vco.jsyn;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.SawtoothOscillator;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.TriangleOscillator;
import com.jsyn.unitgen.UnitOscillator;
import com.jsyn.unitgen.UnitSource;

import fr.istic.groupimpl.synthesizer.util.SignalUtil;
import fr.istic.groupimpl.synthesizer.util.jsyn.JsynFrequencyModulation;

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
 * @author Team GoupImpl
 */

public class VCOCircuit extends Circuit implements UnitSource
{
	/* Declare units that will be part of the circuit. */
	/** The triangle oscillator. */
	private TriangleOscillator triangleOscillator;
	
	/** The sawtooth oscillator. */
	private SawtoothOscillator sawtoothOscillator;
	
	/** The square oscillator. */
	private SquareOscillator squareOscillator;
	
	/** The vc freq. */
	private JsynFrequencyModulation vcFreq;
	
	/** The select from3. */
	private SelectFrom3Input selectFrom3;
	
	/** The pass through amplitude. */
	private PassThrough passThroughAmplitude;
	
	/* Declare ports. */
	/** The input fm. */
	private UnitInputPort inputFM;
	
	/** The input f0. */
	private UnitInputPort inputF0;
	
	/** The input octave. */
	private UnitInputPort inputOctave;
	
	/** The input shape. */
	private UnitInputPort inputShape;
	
	/** The input amplitude. */
	private UnitInputPort inputAmplitude;
	
	/** The output. */
	private UnitOutputPort output; // fonction du sélecteur de la forme (shape)
	
	/** The output square. */
	private UnitOutputPort outputSquare;
	
	/** The output sawtooth. */
	private UnitOutputPort outputSawtooth;
	
	/** The output triangle. */
	private UnitOutputPort outputTriangle;

	/**
	 * Frequency modulation input (Volt).
	 *
	 * @return the input fm
	 */
	public UnitInputPort getInputFM() {
		return inputFM;
	}

	/**
	 * Default Frequency input (Hz).
	 *
	 * @return the input f0
	 */
	public UnitInputPort getInputF0() {
		return inputF0;
	}

	/**
	 * Select shape input (1 | 2 | 3)
	 * - 1 triangleOscillator
	 * - 2 sawtoothOscillator
	 * - 3 squareOscillator.
	 *
	 * @return the input shape
	 */
	public UnitInputPort getInputShape() {
		return inputShape;
	}

	/**
	 * Octave input.
	 *
	 * @return the input octave
	 */
	public UnitInputPort getInputOctave() {
		return inputOctave;
	}

	/**
	 * Amplitude input.
	 *
	 * @return the input amplitude
	 */
	public UnitInputPort getInputAmplitude() {
		return inputAmplitude;
	}

	/**
	 *  
	 * Output of the selected shape.
	 *
	 * @return the output
	 */
	public UnitOutputPort getOutput()
	{
		return output;
	}
	
	/**
	 * Output Square shape.
	 *
	 * @return the output square
	 */
	public UnitOutputPort getOutputSquare() {
		return outputSquare;
	}

	/**
	 * Output Sawtooth shape.
	 *
	 * @return the output sawtooth
	 */
	public UnitOutputPort getOutputSawtooth() {
		return outputSawtooth;
	}

	/**
	 * Output Triangle shape.
	 *
	 * @return the output triangle
	 */
	public UnitOutputPort getOutputTriangle() {
		return outputTriangle;
	}
	
	/**
	 * Constructor.
	 */
	public VCOCircuit()
	{
		/*
		 * Create various unit generators and add them to circuit.
		 */
		add(triangleOscillator = new TriangleOscillator());
		add(sawtoothOscillator = new SawtoothOscillator());
		add(squareOscillator = new SquareOscillator());

		add(vcFreq = new JsynFrequencyModulation());
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
		 
		passThroughAmplitude.getInput().set(UnitOscillator.DEFAULT_AMPLITUDE/SignalUtil.COEF_VOLT);
		passThroughAmplitude.output.connect(triangleOscillator.amplitude);
		passThroughAmplitude.output.connect(sawtoothOscillator.amplitude);
		passThroughAmplitude.output.connect(squareOscillator.amplitude);
		
		triangleOscillator.output.connect(selectFrom3.getInput1());
		sawtoothOscillator.output.connect(selectFrom3.getInput2());
		squareOscillator.output.connect(selectFrom3.getInput3());	
	}
	
	/**
	 * Sets the amplitude.
	 *
	 * @param volt the new amplitude
	 */
	public void setAmplitude( double volt )
	{
		passThroughAmplitude.getInput().set(volt*(UnitOscillator.DEFAULT_AMPLITUDE/SignalUtil.COEF_VOLT));
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
