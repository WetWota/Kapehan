package proj.Kape.Kapehan;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import proj.Kape.Kapehan.controllers.ProductGridController;

public class ProductGridTest extends Application {

    public static void main(String[] args) {
        // Launch the standalone test application
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane productGrid = new GridPane();
        productGrid.setHgap(2);
        productGrid.setVgap(10);  
        
        // Initialize your controller
        new ProductGridController(productGrid);

        Scene scene = new Scene(productGrid, 800, 600);
        
        primaryStage.setTitle("Product Grid Test");
        primaryStage.setScene(scene);
        
        // Let JavaFX calculate the required size
        primaryStage.sizeToScene();
        primaryStage.show();
    }
}