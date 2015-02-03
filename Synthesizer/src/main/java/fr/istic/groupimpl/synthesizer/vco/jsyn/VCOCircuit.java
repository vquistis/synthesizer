package fr.istic.groupimpl.synthesizer.vco.jsyn;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.FilterStateVariable;
import com.jsyn.unitgen.MultiplyAdd;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.RedNoise;
import com.jsyn.unitgen.SawtoothOscillator;
import com.jsyn.unitgen.SawtoothOscillatorBL;
import com.jsyn.unitgen.SawtoothOscillatorDPW;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.SquareOscillatorBL;
import com.jsyn.unitgen.TriangleOscillator;
import com.jsyn.unitgen.UnitSource;
import com.jsyn.unitgen.WhiteNoise;

/**
 * VCO oscillator with a frequency modulation input.
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

	public UnitInputPort getInputFM() {
		return inputFM;
	}

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
		addPort(inputFM = vcFreq.getInputfm(), "inputfm");
		addPort(inputF0 = vcFreq.getInputf0(), "inputf0");
		addPort(inputOctave = vcFreq.getInputOctave(), "inputOctave");
		addPort(inputShape = selectFrom3.getInputSelect(), "inputSelectShape");
		
		addPort(inputAmplitude = passThroughAmplitude.getInput(), "inputAmplitude");
		
		addPort(output = selectFrom3.getOutput()); // fct sélecteur
		addPort(outputSquare = squareOscillator.getOutput());
		addPort(outputSawtooth = sawtoothOscillator.getOutput());
		addPort(outputTriangle = triangleOscillator.getOutput());
		
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
}
