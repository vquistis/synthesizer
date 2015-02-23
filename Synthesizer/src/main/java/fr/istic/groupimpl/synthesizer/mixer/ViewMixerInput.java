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
 * View of Mixer module.
 *
 * @author Team GroupImpl
 */
public class ViewMixerInput extends HBox {
	
	/** The fx input. */
	@FXML private ImageView fxInput;
	
	/** The fx label input. */
	@FXML private Label fxLabelInput;
	
	/** The fx knob volume pane. */
	@FXML private VBox fxKnobVolumePane;
	
	/** The fx value volume. */
	@FXML private TextField fxValueVolume;
	
	/** The fx mute volume. */
	@FXML private CheckBox fxMuteVolume;
	
	/**
	 * Get the instance of the Input Image View.
	 *
	 * @return ImageView
	 */
	public ImageView getFxInput() {
		return fxInput;
	}

	/**
	 * Get the instance of the Input Label.
	 *
	 * @return Label
	 */
	public Label getFxLabelInput() {
		return fxLabelInput;
	}

	/**
	 * Get the instance of the Volume Knob.
	 *
	 * @return VBox
	 */
	public VBox getFxKnobVolumePane() {
		return fxKnobVolumePane;
	}

	/**
	 * Get the instance of the Volume TextField.
	 *
	 * @return TextField
	 */
	public TextField getFxValueVolume() {
		return fxValueVolume;
	}

	/**
	 * Get the instance of the Mute Volume CheckBox.
	 *
	 * @return CheckBox
	 */
	public CheckBox getFxMuteVolume() {
		return fxMuteVolume;
	}
	
	/**
	 * Constructor.
	 *
	 * @param label 
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
