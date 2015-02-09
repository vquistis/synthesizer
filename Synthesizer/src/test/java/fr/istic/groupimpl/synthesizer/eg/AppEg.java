package fr.istic.groupimpl.synthesizer.eg;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppEg extends Application{
	
	public static void main(String[] arg0) {
		launch(arg0);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
	    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/eg.fxml"));
		URL cssURL = getClass().getClassLoader().getResource("css/style.css");
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(cssURL.toExternalForm());
		
		primaryStage.setScene(scene);
		primaryStage.setScene(scene);
		primaryStage.show();
		
        primaryStage.setOnCloseRequest((event) -> System.exit(0));
	}
} 
