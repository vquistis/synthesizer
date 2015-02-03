package fr.isitic;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.devices.AudioDeviceManager;
import com.jsyn.unitgen.FilterStateVariable;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.MultiplyAdd;
import com.jsyn.unitgen.RedNoise;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.UnitGenerator;
import com.jsyn.unitgen.UnitOscillator;
import com.jsyn.unitgen.WhiteNoise;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	//firstBip();
    	firstVCO();
    }
    
    public static void firstBip()
    {
    	Synthesizer synth;
    	UnitOscillator osc;
    	LineOut lineOut;
    	double time;
    	
    	// Create a context for the synthesizer.
    	synth = JSyn.createSynthesizer();
    	
    	// Start synthesizer using default stereo output at 44100 Hz.
    	synth.start();

    	// Add a tone generator.
    	synth.add( osc = new SineOscillator() );
    	//snth.add( osc = new SquareOscillator() );
    	
    	// Add a stereo audio output unit.
    	synth.add( lineOut = new LineOut() );
    	
    	// Connect the oscillator to both channels of the output.
    	osc.output.connect( 0, lineOut.input, 0 );
    	osc.output.connect( 0, lineOut.input, 1 );

    	// Set the frequency and amplitude for the sine wave.
    	osc.frequency.set( 50.0 );
    	osc.amplitude.set( 10 );

    	// We only need to start the LineOut. It will pull data from the oscillator.
    	lineOut.start();
    	    	
    	time = synth.getCurrentTime() + 3;
	    try {
			synth.sleepUntil( time );
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
    	lineOut.stop();
    	synth.stop();
    }
    
    public static void firstVCO()
    {
       	Synthesizer synth;
       	VCOCircuit oscVCO;
    	LineOut lineOut;
    	double time;
    	
    	// Create a context for the synthesizer.
    	synth = JSyn.createSynthesizer();
    	
    	// Start synthesizer using default stereo output at 44100 Hz.
    	synth.start();

    	// Add a tone generator.
    	synth.add( oscVCO = new VCOCircuit() );
    	
    	// Add a stereo audio output unit.
    	synth.add( lineOut = new LineOut() );
    	
    	// Connect the oscillator to both channels of the output.
    	oscVCO.getOutput().connect( 0, lineOut.input, 0 );
    	oscVCO.getOutput().connect( 0, lineOut.input, 1 );

    	// Set the frequency and amplitude for the sine wave.
    	oscVCO.getFo().set(440);
    	oscVCO.getFm().set(1);
    	oscVCO.getOctave().set(0.0);
    	oscVCO.getAmplitude().set(10);
    	oscVCO.getShape().set(1); // 1 | 2 | 3
    	//oscVCO.amplitude.set( 10 );

    	// We only need to start the LineOut. It will pull data from the oscillator.
    	lineOut.start();
    	    	
    	
	    try {
	    	for (int i=0; i<=10 ; i++) {
	    		time = synth.getCurrentTime() + 0.3;
	    		synth.sleepUntil( time );
	    		oscVCO.getFm().set(1 + 0.1*i);
	    	}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
    	lineOut.stop();
    	synth.stop();    	
    }
}

