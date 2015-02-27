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
 * Frequency Voltage Controller
 * +1 Volt = +1 Octave
 * -1 Volt = -1 Octave
 *  
 * <pre>
 * output = TODO;
 * <pre>
 * 
 * 
 * @author Team GroupImpl
 */

public class VcFreq extends UnitGenerator {
	private UnitInputPort inputfm;
	private UnitInputPort inputf0;
	private UnitInputPort inputOctave; //UnitVariablePort 
	private UnitOutputPort frequencyOut;
    
    public UnitInputPort getInputfm() {
		return inputfm;
	}

	public UnitInputPort getInputf0() {
		return inputf0;
	}

	public UnitInputPort getInputOctave() {
		return inputOctave;
	}
	
	public UnitOutputPort getFrequencyOut() {
		return frequencyOut;
	}

    public VcFreq() {
        addPort(inputfm = new UnitInputPort("Inputfm"));
        addPort(inputf0 = new UnitInputPort("Inputf0"));
        addPort(inputOctave = new UnitInputPort("InputOctave"));
        addPort(frequencyOut = new UnitOutputPort("FrequencyOut"));
    }

    @Override
    public void generate(int start, int limit) {
        double[] inputfms = inputfm.getValues();
        double[] inputf0s = inputf0.getValues();
        double[] inputOctaves = inputOctave.getValues();
        double[] frequencyOuts = frequencyOut.getValues();

        for (int i = start; i < limit; i++) {	
        	frequencyOuts[i] = converter(inputfms[i], inputf0s[i], inputOctaves[i]);
        }
    }
    
    public double converter(double fm, double f0, double octave) {
    	return f0*Math.pow(2, octave + fm);
    }
}
