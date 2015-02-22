package fr.istic.groupimpl.synthesizer.mixer;

import java.io.IOException;

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
	
	public ImageView getFxInput() {
		return fxInput;
	}

	public Label getFxLabelInput() {
		return fxLabelInput;
	}

	public VBox getFxKnobVolumePane() {
		return fxKnobVolumePane;
	}

	public TextField getFxValueVolume() {
		return fxValueVolume;
	}

	public CheckBox getFxMuteVolume() {
		return fxMuteVolume;
	}
	
	/**
	 * Constructor
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
			e.printStackTrace();
	    }
	}
}
