package fr.istic.groupimpl.synthesizer.keyboard.jsyn;

import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.util.SignalUtil;

/**
 * 
 * The Class JSynKeyboard
 * 
 * @author Team GroupImpl
 *
 */
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
	
	/**
	 * set volt
	 * @param volt
	 */
	public void setVolt( double volt )
	{
		this.voltCV = volt;
	}
	
	/**
	 * set Volt of gate
	 * 
	 * @param press
	 */
	public void setPress( boolean press )
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
