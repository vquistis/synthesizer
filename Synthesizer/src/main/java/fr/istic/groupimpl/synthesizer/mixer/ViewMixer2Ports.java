package fr.istic.groupimpl.synthesizer.mixer;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 
 * Tip to instantiate a module with configuration
 * 
 * @author groupimpl Team
 *
 */
public class ViewMixer2Ports extends ViewMixer {
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		// Change configuration
		setNumberOfInputPort(2);
		configurate();
	}
	
	@Override
	public String getFilename() {
		return "fxml/mixer2.fxml";
	}
}
