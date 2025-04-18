package proj.Kape.Kapehan.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import proj.Kape.Kapehan.models.ProductModel;
import proj.Kape.Kapehan.service.ProductService;

import java.io.IOException;
import java.util.List;

public class ProductGridController {
    private final ProductService productService;
    private final GridPane productGrid;
    private final DashboardController dashboardController;
    private static final int COLUMNS = 4;

    public ProductGridController(GridPane productGrid, DashboardController dashboardController) {
        this.productGrid = productGrid;
        this.productService = new ProductService();
        this.dashboardController = dashboardController;
        initializeGrid();
    }

    private void initializeGrid() {
        productGrid.getChildren().clear();
        productGrid.setHgap(20);
        productGrid.setVgap(20);

        List<ProductModel> products = productService.getAllProducts();
        
        for (int i = 0; i < products.size(); i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/ItemPane.fxml"));
                Pane itemPane = loader.load();
                
                ItemMenuController controller = loader.getController();
                controller.setProductData(products.get(i));
                controller.setDashboardController(dashboardController);
                
                int row = i / COLUMNS;
                int column = i % COLUMNS;
                
                productGrid.add(itemPane, column, row);
            } catch (IOException e) {
                System.err.println("Error loading ItemPane: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void refreshGrid() {
        initializeGrid();
    }
}