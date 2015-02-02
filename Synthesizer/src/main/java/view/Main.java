package view;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	public static void main(String[] arg0) {
		launch(arg0);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		//Group root = new Group();
	    Parent root = FXMLLoader.load(getClass().getResource("/vco.fxml"));
		ClassLoader loader = getClass().getClassLoader();
	 
		Scene scene = new Scene(root, 400,400);
		URL cssURL = loader.getResource("Style.css");
		
		primaryStage.setScene(scene);
		scene.getStylesheets().add(cssURL.toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
} 
