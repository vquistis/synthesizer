package fr.istic.groupimpl.synthesizer.player.jsyn;

import java.io.File;
import java.io.IOException;

import com.jsyn.data.FloatSample;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.VariableRateStereoReader;
import com.jsyn.util.SampleLoader;

import fr.istic.groupimpl.synthesizer.util.SignalUtil;

/**
 * 
 * Jsyn Play module
 *  
 * @author Team GroupImpl
 */
public class PlayerGate extends VariableRateStereoReader { //VariableRateMonoReader

	private UnitInputPort gate; 		//Volt
	private FloatSample sample;
	
	private final double sigMinFrontMontant;
	private final double sigMaxFrontMontant;
	
	private enum State 
	{
		ATTENTE_MIN,
		ATTENTE_MAX
	}
	private State etat=State.ATTENTE_MIN;
	
	/**
	 * Constructor
	 */
    public PlayerGate(double sigMinFrontMontantVolt, double sigMaxFrontMontantVolt) {
        addPort(gate = new UnitInputPort("player_gate"));
        
        sigMinFrontMontant = sigMinFrontMontantVolt/SignalUtil.COEF_VOLT;
        sigMaxFrontMontant = sigMaxFrontMontantVolt/SignalUtil.COEF_VOLT;
     }
    
	/**
	 * get the gate port
	 * @return
	 *  the gate port
	 */
	public UnitInputPort getGate() {
		return gate;
	}

	/**
	 * get the output port
	 * @return
	 * the output port
	 */
	public UnitOutputPort getOutput() {
		return output;
	}

	public void play() {
		if (sample!=null) {
			// player.dataQueue.queue(sample);
			// player.dataQueue.queueOff( sample );
			this.dataQueue.queueOn(sample);
		}
	}

	public void stop() {
		if (sample!=null) {
			this.dataQueue.clear();
		}
	}
	
	public void loadSample(File sampleFile) {
		// Load the sample and display its properties.
		SampleLoader.setJavaSoundPreferred( false );
		try {
			sample = SampleLoader.loadFloatSample(sampleFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*
		System.out.println( "Sample has: channels  = " + sample.getChannelsPerFrame() );
		System.out.println( "            frames    = " + sample.getNumFrames() );
		System.out.println( "            rate      = " + sample.getFrameRate() );
		System.out.println( "            loopStart = " + sample.getSustainBegin() );
		System.out.println( "            loopEnd   = " + sample.getSustainEnd() );
		*/
		
		this.rate.set(sample.getFrameRate());	
	}
	
    @Override
    public void generate(int start, int limit) {
		super.generate(start, limit);
	    double[] inputs = gate.getValues();
	
	    for (int i = start; i < limit; i++) { 
	    	if ( etat == State.ATTENTE_MIN ) {
	    		if ( inputs[i] <= sigMinFrontMontant ) {
	    			etat = State.ATTENTE_MAX;
	    		}
	    	} else 	{
	      		if ( inputs[i] >= sigMaxFrontMontant ) {
	    			etat = State.ATTENTE_MIN;
	    			if (sample!=null) {
	    				this.dataQueue.queueOn(sample);
	    			}
	    		}
	    	}
	     }
    }
 }
