package fr.groupimpl.Synthesizer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 * Created by plouzeau on 2014-09-22.
 */
public class Component implements Initializable {

    @FXML
    private AnchorPane leftView;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    public void initialize(URL location, ResourceBundle resources) {
        Logger.getGlobal().info("Left controller initialized");
    }
    
    
	public Node show() {
		Node root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("component.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return root;
    }
}
