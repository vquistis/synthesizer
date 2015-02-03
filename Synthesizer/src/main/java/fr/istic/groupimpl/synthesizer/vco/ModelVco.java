package fr.istic.groupimpl.synthesizer.vco;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;

public class ModelVco extends ModelComponent {
	
	private UnitGenerator sineOsc;
		
	public ModelVco() {
		super();		
		sineOsc = new SineOscillator();
		// TODO : Remplacer par le composant Jsyn (UnitGenerator, à créer)		
		
		// TEST
//		Synthesizer synth = JSyn.createSynthesizer();    	
//    	synth.start();
//		SineOscillator sineOsc = new SineOscillator();
//		LineOut lineOut = new LineOut();		
//		sineOsc.output.connect(0, lineOut.input, 0);   // connect to left channel
//		sineOsc.output.connect(0, lineOut.input, 1);   // connect to right channel
//		synth.add(sineOsc);
//		synth.add(lineOut);
//		lineOut.start();
				
	}

	@Override
	public UnitGenerator getUnitGenerator() {
		return sineOsc;
	}
	
	protected void setJsynOctave(double octave) {
		// TODO
	}
		
}
