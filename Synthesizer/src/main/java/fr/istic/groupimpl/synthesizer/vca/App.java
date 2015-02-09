package fr.istic.groupimpl.synthesizer.vca;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application
{
    
    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main( String[] args ){
        launch(args);
    }

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/vca.fxml"));
		
		URL cssURL = getClass().getClassLoader().getResource("css/style.css");
		
		Scene scene = new Scene(root,230,300);
		scene.getStylesheets().add(cssURL.toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

        primaryStage.setOnCloseRequest((event) -> System.exit(0));
	}
}
