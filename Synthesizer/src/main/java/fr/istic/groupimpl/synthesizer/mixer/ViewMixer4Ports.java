package fr.istic.groupimpl.synthesizer.mixer;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 
 * Tip to instanciate a module with configuration
 * 
 * @author groupimpl Team
 *
 */
public class ViewMixer4Ports extends ViewMixer {
	@Override
	public void initialize(URL location, ResourceBundle resource) {	
		// Change configuration
		setNumberOfInputPort(4);
		configurate();
	}
	
	@Override
	public String getFilename() {
		return "fxml/mixer4.fxml";
	}
}
