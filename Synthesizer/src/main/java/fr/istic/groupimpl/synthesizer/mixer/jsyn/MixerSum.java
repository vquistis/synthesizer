package fr.istic.groupimpl.synthesizer.mixer.jsyn;

import java.util.ArrayList;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

/**
 * Sum Mixer unit. Sum of many inputs.
 * 
 * <pre>
 * output = sum of inputs
 * <pre>
 * 
 * @author Team GroupImpl
 */

public class MixerSum extends UnitGenerator {
	private ArrayList<UnitInputPort> unitInputPorts = new ArrayList<UnitInputPort>();
	private UnitOutputPort output;
    
    /**
     * Get Number Of Input Port
     * @return UnitInputPort
     */
    public Integer getNumberOfInputPort() {
		return unitInputPorts.size();
	}
    
    /**
     * Input(index) [first index = 0]
     * @return UnitInputPort
     */
    public UnitInputPort getInput(Integer index) {
		return unitInputPorts.get(index);
	}

	/**
	 * Output source
	 * @return
	 */
	public UnitOutputPort getOutput() {
		return output;
	}

    /**
     * Constructor
     * 
     * @param NumberOfInputPort
     *   number of input port to instantiate
     */
    public MixerSum(Integer NumberOfInputPort) {
    	Integer index=0;
        for(int i = 0; i < NumberOfInputPort; i++)
        {
        	index = i + 1;
        	unitInputPorts.add(new UnitInputPort("Input" + index));
        	System.out.println("unitInputPorts.add " + i);
        	addPort(unitInputPorts.get(i));
        } 
        addPort(output = new UnitOutputPort("Output"));
    }

    @Override
    public void generate(int start, int limit) {
    	double inputSum=0;
    	final ArrayList<double[]> ArrayInputs = new ArrayList<double[]>();
        double[] outputs = output.getValues();

        for(int i = 0; i < unitInputPorts.size(); i++)
        {
        	ArrayInputs.add(unitInputPorts.get(i).getValues());
        } 
    	
        for (int i = start; i < limit; i++) {
        	inputSum=0;
        	for (int inputIndex = 0; inputIndex < unitInputPorts.size(); inputIndex++) {
        		inputSum = inputSum + ArrayInputs.get(inputIndex)[i]/unitInputPorts.size();
        	}
        	outputs[i] = inputSum;
        }
    }
}
