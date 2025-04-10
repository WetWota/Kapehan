package proj.Kape.Kapehan.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import proj.Kape.Kapehan.models.ProductModel;

import java.math.BigDecimal;

public class ItemMenuController {
    @FXML private Label productName;
    @FXML private Label productPrice;
    @FXML private TextField quantityField;
    @FXML private Button increaseBTN;
    @FXML private Button decreaseBTN;
    @FXML private Label totalLabel;
    @FXML private Button addBTN;

    private ProductModel product;
    private int quantity = 0;

    @FXML
    public void initialize() {
        // Set up event handlers
        increaseBTN.setOnAction(e -> increaseQuantity());
        decreaseBTN.setOnAction(e -> decreaseQuantity());
        quantityField.textProperty().addListener((obs, oldVal, newVal) -> quantityChanged());
        totalLabel.setText("");
        
    }

    // Add this method to set product data
    public void setProductData(ProductModel product) {
        this.product = product;
        updateDisplay();
    }

    private void updateDisplay() {
        if (product != null) {
            productName.setText(product.getProductName());
            productPrice.setText(String.format("₱%.2f", product.getPrice()));
            quantityField.setText(String.valueOf(quantity));
            calculateTotal();
        }
    }

    private void increaseQuantity() {
        quantity++;
        updateDisplay();
    }

    private void decreaseQuantity() {
        if (quantity > 1) {
            quantity--;
            updateDisplay();
        }
    }

    private void quantityChanged() {
        try {
            int newQuantity = Integer.parseInt(quantityField.getText());
            if (newQuantity > 0) {
                quantity = newQuantity;
                calculateTotal();
            }
        } catch (NumberFormatException e) {
            quantityField.setText(String.valueOf(quantity));
        }
    }

    private void calculateTotal() {
        if (product != null) {
        	if(quantity != 0) {
                BigDecimal total = product.getPrice().multiply(BigDecimal.valueOf(quantity));
                totalLabel.setText(String.format("Total: ₱%.2f", total));
        	}
        }
    }
    
    @FXML
    private void handleAdd(ActionEvent event) {
    	refresh();
    }
    
    private void refresh() {
    	quantity = 0;
    	quantityField.setText("0");
    	totalLabel.setText("");
    }
}