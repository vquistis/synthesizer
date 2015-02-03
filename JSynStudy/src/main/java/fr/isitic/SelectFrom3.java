/*
 * Copyright 2004 Phil Burk, Mobileer Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.isitic;

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

public class SelectFrom3 extends UnitGenerator {
	private UnitInputPort input1;
	private UnitInputPort input2;
	private UnitInputPort input3;
	private UnitInputPort inputSelect;
	private UnitOutputPort output;
    
    public UnitInputPort getInput1() {
		return input1;
	}
	public UnitInputPort getInput2() {
		return input2;
	}

	public UnitInputPort getInput3() {
		return input3;
	}

	public UnitInputPort getInputSelect() {
		return inputSelect;
	}

	public UnitOutputPort getOutput() {
		return output;
	}

    public SelectFrom3() {
        addPort(input1 = new UnitInputPort("Input1"));
        addPort(input2 = new UnitInputPort("Input2"));
        addPort(input3 = new UnitInputPort("Input3"));
        addPort(inputSelect = new UnitInputPort("InputSelect"));
        addPort(output = new UnitOutputPort("Output"));
    }

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
