import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        JFXButton button = new JFXButton("Click Me");
        VBox root = new VBox(new Label("Hello, JFoenix!"), button);
        Scene scene = new Scene(root, 300, 200);

        primaryStage.setTitle("JFoenix Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
