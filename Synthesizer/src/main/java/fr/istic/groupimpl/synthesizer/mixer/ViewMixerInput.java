package fr.istic.groupimpl.synthesizer.mixer;

import java.io.IOException;

import fr.istic.groupimpl.synthesizer.logger.Log;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 
 * View of Mixer module
 * 
 * @author Team GroupImpl
 *
 */
public class ViewMixerInput extends HBox {
	@FXML private ImageView fxInput;
	@FXML private Label fxLabelInput;
	@FXML private VBox fxKnobVolumePane;
	@FXML private TextField fxValueVolume;
	@FXML private CheckBox fxMuteVolume;
	
	/**
	 * Get the instance of the Input Image View
	 * 
	 * @return ImageView
	 */
	public ImageView getFxInput() {
		return fxInput;
	}

	/**
	 * Get the instance of the Input Label
	 * 
	 * @return Label
	 */
	public Label getFxLabelInput() {
		return fxLabelInput;
	}

	/**
	 * Get the instance of the Volume Knob
	 * 
	 * @return VBox
	 */
	public VBox getFxKnobVolumePane() {
		return fxKnobVolumePane;
	}

	/**
	 * Get the instance of the Volume TextField
	 * 
	 * @return TextField
	 */
	public TextField getFxValueVolume() {
		return fxValueVolume;
	}

	/**
	 * Get the instance of the Mute Volume CheckBox
	 * 
	 * @return CheckBox
	 */
	public CheckBox getFxMuteVolume() {
		return fxMuteVolume;
	}
	
	/**
	 * Constructor
	 * @param label
	 * 	input label
	 */
	public ViewMixerInput(String label) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/mixerInput.fxml"));
	        loader.setController(this);
	        loader.setRoot(this);
	        loader.load();
	        fxLabelInput.setText(label);
	    } catch (IOException e) {
			Log.getInstance().error("Failed to initialize mixer", e );
	    }
	}
}
