package fr.isitic;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.devices.AudioDeviceManager;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.RedNoise;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.UnitOscillator;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	firstBip();

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
}

