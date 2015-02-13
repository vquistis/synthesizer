package fr.istic.groupimpl.synthesizer.keyboard;

import java.util.Collection;

import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.UnitGenerator;

import fr.istic.groupimpl.synthesizer.component.ModelComponent;

/**
 * 
 * Model of keyboard module
 * 
 * @author Team GroupImpl
 *
 */
public class ModelKeyboard extends ModelComponent {

	private JSynKeyboard keyboard;
	
	private final int MIN_OCTAVE=-9;
	private final int MAX_OCTAVE=8;
	
	
	private int octave; // from -9 to +9
	
	/**
	 * Constructor
	 */
	public ModelKeyboard() {
		super();
		keyboard = new JSynKeyboard();
		octave = 0;
	}
	
	@Override
	public UnitGenerator getUnitGenerator() {
		return keyboard;
	}

	
	@Override
	public Collection<UnitPort> getAllPorts() {
		return keyboard.getPorts();
	}

	void incOctave()
	{
		octave = Math.min(MAX_OCTAVE, octave+1);
	}
	
	void decOctave()
	{
		octave = Math.max(MIN_OCTAVE, octave-1);
	}
	
	
	
	/**
	 * sets the key of the keyboard
	 * @param key, Time stamp
	 */
	public void setKey(int n) {
		double v = (double)octave + ((double)n)/12.;
		keyboard.setVolt(v);
	}
	
	public void setPress( boolean press )
	{
		keyboard.setPress(press);
	}
}
