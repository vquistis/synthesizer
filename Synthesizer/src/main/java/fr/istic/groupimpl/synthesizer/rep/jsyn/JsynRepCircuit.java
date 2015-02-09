package fr.istic.groupimpl.synthesizer.rep.jsyn;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;

public class JsynRepCircuit extends Circuit {
	
	private UnitInputPort input; 	
	private UnitOutputPort output1, output2, output3; 
	
	/**
	 * Get the input of REP module.
	 * @return
	 */
	public UnitInputPort getInput() {
		return input;
	}
	
	/**
	 * Get an output of REP module.
	 * @param i
	 * 	  chose an output
	 * @return
	 */
	public UnitOutputPort getOutput(int i) {
		if(i == 1)
			return output1;
		if(i == 2)
			return output2;
		if(i == 3)
			return output3;
		
		return output1;
	}
	
	/**
	 * Constructor
	 */
	public JsynRepCircuit(){
		addPort(input = new UnitInputPort("rep_in"));
        addPort(output1 = new UnitOutputPort("rep_out1"));
        addPort(output2 = new UnitOutputPort("rep_out2"));
        addPort(output3 = new UnitOutputPort("rep_out3"));
	}
}