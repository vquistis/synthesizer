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
 * </pre>
 * 
 * @author Team GroupImpl
 */

public class MixerSum extends UnitGenerator {
	
	/** The unit input ports. */
	private ArrayList<UnitInputPort> unitInputPorts = new ArrayList<UnitInputPort>();
	
	/** The output. */
	private UnitOutputPort output;
	
	/** The average output value. */
	private UnitOutputPort averageOutputValue;
    
    /**
     * Get Number Of Input Port.
     *
     * @return UnitInputPort
     */
    public Integer getNumberOfInputPort() {
		return unitInputPorts.size();
	}
    
    /**
     * Input(index) [first index = 0].
     *
     * @param index the index
     * @return UnitInputPort
     */
    public UnitInputPort getInput(Integer index) {
		return unitInputPorts.get(index);
	}

	/**
	 * Output source.
	 *
	 * @return the output
	 */
	public UnitOutputPort getOutput() {
		return output;
	}

	/**
	 * Output Average value.
	 *
	 * @return the average output value
	 */
	public UnitOutputPort getAverageOutputValue() {
		return averageOutputValue;
	}
	
    /**
     * Constructor.
     *
     * @param NumberOfInputPort   number of input port to instantiate
     */
    public MixerSum(Integer NumberOfInputPort) {
    	Integer index=0;
        for(int i = 0; i < NumberOfInputPort; i++)
        {
        	index = i + 1;
        	unitInputPorts.add(new UnitInputPort("Input" + index));
        	addPort(unitInputPorts.get(i));
        } 
        addPort(output = new UnitOutputPort("Output"));
        addPort(averageOutputValue = new UnitOutputPort("averageOutputValue"));
    }

    /* (non-Javadoc)
     * @see com.jsyn.unitgen.UnitGenerator#generate(int, int)
     */
    @Override
    public void generate(int start, int limit) {
    	double inputAverage=0;
    	double inputSum=0;
    	final ArrayList<double[]> ArrayInputs = new ArrayList<double[]>();
        double[] outputs = output.getValues();
        double[] averageOutputValues = averageOutputValue.getValues();

        for(int i = 0; i < unitInputPorts.size(); i++)
        {
        	ArrayInputs.add(unitInputPorts.get(i).getValues());
        } 
    	
        for (int i = start; i < limit; i++) {
        	inputSum=0;
        	for (int inputIndex = 0; inputIndex < unitInputPorts.size(); inputIndex++) {
        		inputSum = inputSum + ArrayInputs.get(inputIndex)[i];
        	}
        	outputs[i] = inputSum;
        	inputAverage = inputAverage + Math.abs(inputSum);
        }

        inputAverage=inputAverage/limit;
        for (int i = start; i < limit; i++) {
        	averageOutputValues[i] = inputAverage;
        }
    }
}
