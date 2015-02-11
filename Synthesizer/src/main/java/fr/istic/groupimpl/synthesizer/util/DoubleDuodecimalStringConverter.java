package fr.istic.groupimpl.synthesizer.util;

import javafx.util.StringConverter;

public class DoubleDuodecimalStringConverter extends StringConverter<Number> {
	@Override
	public String toString(Number value) {

		Double v = (Double) value;

		int valInt = (int) (v * Math.pow(12., 4.));

		String str = Integer.toString(Math.abs(valInt), 12);

		while (str.length() < 5) {
			str = "0" + str;
		}
		int ind = str.length() - 4;
		str = str.substring(0, ind) + "." + str.substring(ind);

		if (valInt < 0) {
			str = "-" + str;
		}
		return str;
	}

	@Override
	public Number fromString(String string) {

		double value = 0.;
		double sens = 1.;

		String code = "0123456789ab";

		String str = string.toLowerCase();

		if (str.substring(0, 1).equals("-")) {
			str = str.substring(1);
			sens = -1.;
		}

		double vDiv = 0.;
		for (byte c : string.getBytes()) {
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
		return value * sens;
	}

}