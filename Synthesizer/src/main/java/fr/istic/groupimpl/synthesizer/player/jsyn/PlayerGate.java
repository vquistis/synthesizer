package fr.istic.groupimpl.synthesizer.player.jsyn;

import java.io.File;
import java.io.IOException;

import com.jsyn.data.FloatSample;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.VariableRateStereoReader;
import com.jsyn.util.SampleLoader;

import fr.istic.groupimpl.synthesizer.logger.Log;
import fr.istic.groupimpl.synthesizer.util.SignalUtil;

/**
 * 
 * Jsyn Play module
 *  
 * @author Team GroupImpl
 */
public class PlayerGate extends VariableRateStereoReader { //VariableRateMonoReader

	/** The gate. */
 private UnitInputPort gate; 		//Volt
	
	/** The sample. */
	private FloatSample sample;
	
	/** The sig min front montant. */
	private final double sigMinFrontMontant;
	
	/** The sig max front montant. */
	private final double sigMaxFrontMontant;
	
	/**
	 * The Enum State.
	 */
	private enum State 
	{
		
		/** The attente min. */
		ATTENTE_MIN,
		
		/** The attente max. */
		ATTENTE_MAX
	}
	
	/** The etat. */
	private State etat=State.ATTENTE_MIN;
	
	/**
	 * Constructor.
	 *
	 * @param sigMinFrontMontantVolt the sig min front montant volt
	 * @param sigMaxFrontMontantVolt the sig max front montant volt
	 */
    public PlayerGate(double sigMinFrontMontantVolt, double sigMaxFrontMontantVolt) {
        addPort(gate = new UnitInputPort("player_gate"));
        
        sigMinFrontMontant = sigMinFrontMontantVolt/SignalUtil.COEF_VOLT;
        sigMaxFrontMontant = sigMaxFrontMontantVolt/SignalUtil.COEF_VOLT;
     }
    
	/**
	 * get the gate port.
	 *
	 * @return  the gate port
	 */
	public UnitInputPort getGate() {
		return gate;
	}

	/**
	 * get the output port.
	 *
	 * @return the output port
	 */
	public UnitOutputPort getOutput() {
		return output;
	}

	/**
	 * Play.
	 */
	public void play() {
		if (sample!=null) {
			// player.dataQueue.queue(sample);
			// player.dataQueue.queueOff( sample );
			this.dataQueue.queueOn(sample);
		}
	}

	/**
	 * @see com.jsyn.unitgen.UnitGenerator#stop()
	 */
	public void stop() {
		if (sample!=null) {
			this.dataQueue.clear();
		}
	}
	
	/**
	 * Load sample.
	 *
	 * @param sampleFile the sample file
	 */
	public void loadSample(File sampleFile) {
		// Load the sample and display its properties.
		SampleLoader.setJavaSoundPreferred( false );
		try {
			sample = SampleLoader.loadFloatSample(sampleFile);
		} catch (IOException e) {
			Log.getInstance().error("Failed to load sample", e );
		}
				
		this.rate.set(sample.getFrameRate());	
	}
	
    /**
     * @see com.jsyn.unitgen.VariableRateStereoReader#generate(int, int)
     */
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
