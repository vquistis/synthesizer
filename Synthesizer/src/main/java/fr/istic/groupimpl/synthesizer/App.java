package fr.istic.groupimpl.synthesizer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import fr.istic.groupimpl.synthesizer.global.ViewGlobal;

/**
 * The Class App.
 */
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

	/**
	 * start method javafx
	 * @param primaryStage
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Synthesizer by GroupImpl");
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/global.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root,1100,700);
		primaryStage.setScene(scene);
		primaryStage.show();		
        primaryStage.setOnCloseRequest((event) -> System.exit(0));
        
		ViewGlobal view = loader.getController();
		view.setStage(primaryStage);
		view.init();
	}
}
