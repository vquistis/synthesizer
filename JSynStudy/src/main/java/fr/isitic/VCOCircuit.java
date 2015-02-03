package fr.isitic;

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

public class VCOCircuit extends Circuit implements UnitSource
{
	/* Declare units that will be part of the circuit. */
	private TriangleOscillator triangleOscillator;
	private SawtoothOscillator sawtoothOscillator;
	private SquareOscillator squareOscillator;
	private VcFreq vcFreq;
	private SelectFrom3 selectFrom3;
	private PassThrough passThroughAmplitude;
	
	/* Declare ports. */
	private UnitInputPort fm;
	private UnitInputPort fo;
	private UnitInputPort octave;
	private UnitInputPort shape;
	private UnitInputPort amplitude;
	private UnitOutputPort output;

	public UnitOutputPort getOutput()
	{
		return output;
	}

	public UnitInputPort getFm() {
		return fm;
	}

	public UnitInputPort getFo() {
		return fo;
	}

	public UnitInputPort getShape() {
		return shape;
	}

	public UnitInputPort getOctave() {
		return octave;
	}

	public UnitInputPort getAmplitude() {
		return amplitude;
	}
	
	public VCOCircuit()
	{
		/*
		 * Create various unit generators and add them to circuit.
		 */
		add(triangleOscillator = new TriangleOscillator());
		add(sawtoothOscillator = new SawtoothOscillator());
		add(squareOscillator = new SquareOscillator());

		add(vcFreq = new VcFreq());
		add(selectFrom3 = new SelectFrom3());
		add(passThroughAmplitude = new PassThrough());
		
		/* Make ports on internal units appear as ports on circuit. */
		/* Optionally give some circuit ports more meaningful names. */	
		addPort(fm = vcFreq.getInputfm(), "inputfm");
		addPort(fo = vcFreq.getInputf0(), "inputf0");
		addPort(octave = vcFreq.getInputOctave(), "inputOctave");
		addPort(shape = selectFrom3.getInputSelect(), "inputSelectShape");
		
		addPort(amplitude = passThroughAmplitude.getInput(), "inputAmplitude");
		
		addPort(output = selectFrom3.getOutput());
		
		/* Connect SynthUnits to make control signal path. */
		vcFreq.getFrequencyOut().connect(triangleOscillator.frequency);
		vcFreq.getFrequencyOut().connect(sawtoothOscillator.frequency);
		vcFreq.getFrequencyOut().connect(squareOscillator.frequency);
		 
		passThroughAmplitude.output.connect(triangleOscillator.amplitude);
		passThroughAmplitude.output.connect(sawtoothOscillator.amplitude);
		passThroughAmplitude.output.connect(squareOscillator.amplitude);
		
		triangleOscillator.output.connect(selectFrom3.getInput1());
		sawtoothOscillator.output.connect(selectFrom3.getInput2());
		squareOscillator.output.connect(selectFrom3.getInput3());	
	}
}
