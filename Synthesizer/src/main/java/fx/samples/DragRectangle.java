package fx.samples;

import java.io.File;
import java.util.LinkedList;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javax.imageio.ImageIO;


public class DragRectangle extends Application {
    Point2D lastXY = null;

    public void start(Stage primaryStage) {
        Pane mainPane = new Pane(); 
        Scene scene = new Scene(mainPane, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        Rectangle area = new Rectangle(0, 0, 500 , 500);

        Rectangle rect = new Rectangle(0, 0, 30, 30);
        rect.setFill(Color.RED);
        mainPane.getChildren().add(rect);

        rect.setOnMouseDragged(event -> {
            System.out.println("Move");
            event.setDragDetect(false);
            Node on = (Node)event.getTarget();
            if (lastXY == null) {
                lastXY = new Point2D(event.getSceneX(), event.getSceneY());
            }
            double dx = event.getSceneX() - lastXY.getX();
            double dy = event.getSceneY() - lastXY.getY();
            on.setTranslateX(on.getTranslateX()+dx);
            on.setTranslateY(on.getTranslateY()+dy);
            lastXY = new Point2D(event.getSceneX(), event.getSceneY());
            if (!area.intersects(event.getSceneX(), event.getSceneY(), 1, 1)) event.setDragDetect(true);
            event.consume();
        });

        rect.setOnDragDetected(event -> {
            System.out.println("Drag:"+event);
            Node on = (Node)event.getTarget();
            Dragboard db = on.startDragAndDrop(TransferMode.COPY);
            event.consume();
        });

        rect.setOnMouseReleased(d ->  lastXY = null);
    }

    public static void main(String[] args) {
        launch(args);
    }
}