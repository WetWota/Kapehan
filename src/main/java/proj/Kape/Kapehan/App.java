package proj.Kape.Kapehan;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        Button btn = new Button("Click Me");
        btn.setId("btnClick"); // Important for TestFX
        btn.setOnAction(event -> btn.setText("Clicked!"));

        StackPane root = new StackPane(btn);
        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.setTitle("TestFX Example");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}