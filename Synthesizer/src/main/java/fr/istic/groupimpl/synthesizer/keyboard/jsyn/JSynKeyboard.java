package fr.istic.groupimpl.synthesizer.keyboard.jsyn;

import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.util.SignalUtil;

/**
 * The Class JSynKeyboard.
 *
 * @author Team GroupImpl
 */
public class JSynKeyboard extends UnitGenerator  {

	/** The output cv. */
	private UnitOutputPort outputCV;
	
	/** The output gate. */
	private UnitOutputPort outputGate;
	
	/** The volt cv. */
	private double voltCV;
	
	/** The volt gate. */
	private double voltGate;
	
	/**
	 * constructor.
	 */
	public JSynKeyboard() {
		addPort(outputCV = new UnitOutputPort("outputKeyCV"));
		addPort(outputGate = new UnitOutputPort("outputKeyGate"));
	}
	
	/**
	 * set volt.
	 *
	 * @param volt the new volt
	 */
	public void setVolt( double volt )
	{
		this.voltCV = volt;
	}
	
	/**
	 * set Volt of gate.
	 *
	 * @param press the new press
	 */
	public void setPress( boolean press )
	{
		voltGate = press ? 5/SignalUtil.COEF_VOLT : -5/SignalUtil.COEF_VOLT;
	}
	
	
	/**
	 * @see com.jsyn.unitgen.UnitGenerator#generate(int, int)
	 */
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
