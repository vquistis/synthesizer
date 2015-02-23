package fr.istic.groupimpl.synthesizer.vco.jsyn;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

/**
 * SelectUnit unit. Select Input1 or Input2 or Input3 based on value on inputSelect port.
 * 
 * <pre>
 * output = switch inputSelects {
 *                case 1:  Input1
 *                case 2:  Input2
 *                case 3:  Input3
 *                default: Input1
 *            }
 * <pre>
 * 
 * @author Team GroupImpl
 */

public class SelectFrom3Input extends UnitGenerator {
	
	/** The input1. */
	private UnitInputPort input1;
	
	/** The input2. */
	private UnitInputPort input2;
	
	/** The input3. */
	private UnitInputPort input3;
	
	/** The input select. */
	private UnitInputPort inputSelect;  // 1 | 2 | 3
	
	/** The output. */
	private UnitOutputPort output;
    
    /**
     * Input 1.
     *
     * @return the input1
     */
    public UnitInputPort getInput1() {
		return input1;
	}
	
	/**
	 * Input 2.
	 *
	 * @return the input2
	 */
	public UnitInputPort getInput2() {
		return input2;
	}

	/**
	 * Input 3.
	 *
	 * @return the input3
	 */
	public UnitInputPort getInput3() {
		return input3;
	}

	/**
	 * Input source selected.
	 *
	 * @return the input select
	 */
	public UnitInputPort getInputSelect() {
		return inputSelect;
	}

	/**
	 * Output selected source.
	 *
	 * @return the output
	 */
	public UnitOutputPort getOutput() {
		return output;
	}

    /**
     * Constructor.
     */
    public SelectFrom3Input() {
        addPort(input1 = new UnitInputPort("Input1"));
        addPort(input2 = new UnitInputPort("Input2"));
        addPort(input3 = new UnitInputPort("Input3"));
        addPort(inputSelect = new UnitInputPort("InputSelect"));
        addPort(output = new UnitOutputPort("Output"));
    }

    /**
     * @see com.jsyn.unitgen.UnitGenerator#generate(int, int)
     */
    @Override
    public void generate(int start, int limit) {
        double[] input1s = input1.getValues();
        double[] input2s = input2.getValues();
        double[] input3s = input3.getValues();
        double[] inputSelects = inputSelect.getValues();
        double[] outputs = output.getValues();

        
        for (int i = start; i < limit; i++) {
             switch ((int)inputSelects[i]) {
                 case 1:  outputs[i] = input1s[i];
                          break;
                 case 2:  outputs[i] = input2s[i];
                          break;
                 case 3:  outputs[i] = input3s[i];
                          break;
                 default: outputs[i] = input1s[i];
                          break;
             }
        }
    }
}
