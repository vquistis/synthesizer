package fr.istic.groupimpl.synthesizer.mixer;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Tip to instantiate a module with configuration.
 *
 * @author groupimpl Team
 */
public class ViewMixer2Ports extends ViewMixer {
	
	/**
	 * @see fr.istic.groupimpl.synthesizer.mixer.ViewMixer#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		// Change configuration
		setNumberOfInputPort(2);
		configurate();
	}
	
	/**
	 * @see fr.istic.groupimpl.synthesizer.mixer.ViewMixer#getFilename()
	 */
	@Override
	public String getFilename() {
		return "fxml/mixer2.fxml";
	}
}
