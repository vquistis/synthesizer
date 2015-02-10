package fr.istic.groupimpl.synthesizer;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    public static void main( String[] args ){
        launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/global.fxml"));
		Parent root = loader.load();
		URL cssURL = getClass().getClassLoader().getResource("css/style.css");
		Scene scene = new Scene(root,1100,700);
		scene.getStylesheets().add(cssURL.toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

        primaryStage.setOnCloseRequest((event) -> System.exit(0));
	}
}
