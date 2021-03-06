package fr.istic.groupimpl.synthesizer.util;

import javafx.util.StringConverter;
import fr.istic.groupimpl.synthesizer.logger.Log;

/**
 * Convert String to Double, string is in duodecimal format ( radix 12 ).
 * the interest of this radix is that in this base 
 * when one is one octave then 0.1 is one semitone.
 *
 * @author Team GrouplImpl
 */
public class DoubleDuodecimalStringConverter extends StringConverter<Number> {
	
	/**
	 * @see javafx.util.StringConverter#toString(java.lang.Object)
	 */
	@Override
	public String toString(Number value) {

		Double v = (Double) value;

		int valInt = (int) (v * Math.pow(12., 3.));

		String str = Integer.toString(Math.abs(valInt), 12);

		while (str.length() < 4) {
			str = "0" + str;
		}
		int ind = str.length() - 3;
		str = str.substring(0, ind) + "." + str.substring(ind);

		// suppress of zero endings
		boolean flagCont = true;
		while (flagCont) {
			
			String last = str.substring(str.length() - 1);
			switch (last) {
			case ".":
				flagCont = false;
				// NO BREAK  FALLS THROUGHT
			case "0":
				str = str.substring(0, str.length() - 1);
				break;
			default:
				flagCont = false;
			}
		}

		if (valInt < 0) {
			str = "-" + str;
		}
		return str;
	}

	/**
	 * @see javafx.util.StringConverter#fromString(java.lang.String)
	 */
	@Override
	public Number fromString(String string) {
		Log.getInstance().trace("DoubleDuoDecimal.fromString debut string="+string);

		double value = 0.;
		double sens = 1.;

		String code = "0123456789ab";

		String str = string.toLowerCase();

		if( str.length() < 1 )
		{
			return 0.;
		}
		
		if (str.substring(0, 1).equals("-")) {
			str = str.substring(1);
			sens = -1.;
			Log.getInstance().trace("DoubleDuoDecimal.fromString moins str="+str);

		} else {
			if (str.substring(0, 1).equals("+")) {
				str = str.substring(1);
				sens = 1.;
			}
		}
		if( str.length() < 1 )
		{
			return 0.;
		}

		double vDiv = 0.;
		for (byte c : str.getBytes()) {
			if (c == '.' && vDiv == 0.) {
				vDiv = 1.;
				continue;
			}

			value *= 12.;
			vDiv *= 12.;

			double v = code.indexOf(c);

			if (v < 0) {
				return 0;
			}
			value += v;

		}
		if (vDiv != 0) {
			value /= vDiv;
		}
		
		Log.getInstance().trace("DoubleDuoDecimal.fromString ret = "+value * sens);
		return value * sens;
	}

}