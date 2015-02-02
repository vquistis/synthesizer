package view;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application{
	public static void main(String[] arg0) {
		launch(arg0);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		HBox hbox = new HBox(100);
		hbox.setAlignment(Pos.CENTER);
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		
	 	Parent root = FXMLLoader.load(getClass().getResource("/vco.fxml"));
		ClassLoader loader = getClass().getClassLoader();
	 
		Scene scene = new Scene(root, 350,350);
		URL cssURL = loader.getResource("Style.css");
		
		//root.getChildren().add(p);
		primaryStage.setScene(scene);
		scene.getStylesheets().add(cssURL.toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
       
       


	}
} 
