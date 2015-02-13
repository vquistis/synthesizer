package fr.istic.groupimpl.synthesizer.keyboard;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;
import com.jsyn.unitgen.UnitSource;

import fr.istic.groupimpl.synthesizer.util.SignalUtil;


public class JSynKeyboard extends UnitGenerator  {

	/* Declare ports */
	private UnitOutputPort outputCV;
	private UnitOutputPort outputGate;
	
	private double voltCV;
	private double voltGate;
	
	/**
	 * constructor
	 */
	public JSynKeyboard() {
		addPort(outputCV = new UnitOutputPort("outputKeyCV"));
		addPort(outputGate = new UnitOutputPort("outputKeyGate"));
	}
	
	
	void setVolt( double volt )
	{
		this.voltCV = volt;
	}
	
	void setPress( boolean press )
	{
		voltGate = press ? 5/SignalUtil.COEF_VOLT : -5/SignalUtil.COEF_VOLT;
	}
	
	
	@Override
	public void generate(int start, int limit) {
		double[] outputs_CV = outputCV.getValues();
		double[] outputs_Gate = outputGate.getValues();

		for (int i = start; i < limit; i++) {
			outputs_CV[i] = voltCV;
			outputs_Gate[i] = voltGate;
		}
	}



	
}
