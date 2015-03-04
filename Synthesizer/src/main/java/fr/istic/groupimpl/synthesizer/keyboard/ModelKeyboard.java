package fr.istic.groupimpl.synthesizer.keyboard;

import java.util.Collection;

import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;
import fr.istic.groupimpl.synthesizer.keyboard.jsyn.JSynKeyboard;
import fr.istic.groupimpl.synthesizer.util.SignalUtil;

/**
 * Model of keyboard module.
 *
 * @author Team GroupImpl
 */
public class ModelKeyboard extends ModelComponent {

	/** The keyboard. */
	private JSynKeyboard keyboard;
	
	/** The min octave. */
	private final int MIN_OCTAVE=-9;
	
	/** The max octave. */
	private final int MAX_OCTAVE=8;
	
	
	/** The octave. */
	private int octave; // from -9 to +9
	
	private int numKey;
	
	/**
	 * Constructor.
	 */
	public ModelKeyboard() {
		super();
		keyboard = new JSynKeyboard();
		octave = 0;
	}
	
	/**
	 * @see fr.istic.groupimpl.synthesizer.component.IModelComponent#getUnitGenerator()
	 */
	@Override
	public UnitGenerator getUnitGenerator() {
		return keyboard;
	}

	
	/**
	 * @see fr.istic.groupimpl.synthesizer.component.IModelComponent#getAllPorts()
	 */
	@Override
	public Collection<UnitPort> getAllPorts() {
		return keyboard.getPorts();
	}

	/**
	 * increase 1 octave.
	 */
	public void incOctave()
	{
		octave = Math.min(MAX_OCTAVE, octave+1);
	}
	
	/**
	 * decrease 1 octave.
	 */
	public void decOctave()
	{
		octave = Math.max(MIN_OCTAVE, octave-1);
	}
	
	/**
	 * sets the key of the keyboard.
	 *
	 * @param n the new key
	 */
	public void setKey(int n) {
		double v = (double)octave + ((double)n)/12.;
		v /=SignalUtil.COEF_VOLT;
		keyboard.setVolt(v);
		numKey = n;
	}
	/**
	 * get the last press key of the keyboard.
	 *
	 */
	public int getKey() {
		return numKey;
	}
	
	/**
	 * to know if a key is pressed.
	 *
	 * @param press the new press
	 */
	public void setPress( boolean press )
	{
		keyboard.setPress(press);
	}
}
