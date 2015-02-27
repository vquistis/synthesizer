package fr.istic.groupimpl.synthesizer.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javafx.util.StringConverter;

/**
 * Converter double in string.
 *
 * @author GroupImpl
 */
public class DoubleStringConverter extends StringConverter<Number> {
	
	/**
	 * @see javafx.util.StringConverter#toString(java.lang.Object)
	 */
	@Override
	public String toString(Number value) {
		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.ENGLISH);
		df.applyPattern("#.###");
		return df.format(value);
	}

	/**
	 * @see javafx.util.StringConverter#fromString(java.lang.String)
	 */
	@Override
	public Number fromString(String string) {
		if (string.equals("")) return 0; 
		try {
			return Double.valueOf(string);
		} catch (Exception e) {
			return 0;
		}
	}
}