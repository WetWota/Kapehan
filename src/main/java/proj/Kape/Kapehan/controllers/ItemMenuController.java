package proj.Kape.Kapehan.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import proj.Kape.Kapehan.models.InvoiceItemModel;
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

    private DashboardController dashboardController;
    
    private ProductModel product;
    private int quantity = 0;

    @FXML
    public void initialize() {
        // Set up event handlers
        increaseBTN.setOnAction(e -> increaseQuantity());
        decreaseBTN.setOnAction(e -> decreaseQuantity());
        quantityField.textProperty().addListener((obs, oldVal, newVal) -> quantityChanged());
        totalLabel.setText("");
        addBTN.setVisible(false);
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
        if (quantity > 0) {
            quantity--;
            updateDisplay();
        }
    }

    private void quantityChanged() {
        try {
            int newQuantity = Integer.parseInt(quantityField.getText());
            if (newQuantity >= 0) {
                quantity = newQuantity;
                calculateTotal();
            } if (newQuantity > 0){
                addBTN.setVisible(true); 
            } else {
            	addBTN.setVisible(false);
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
        	} else {
        		totalLabel.setText("");
        	}
        }
    }
    
    @FXML
    private void handleAdd(ActionEvent event) {
    	try {
    		if (quantity > 0 && product != null) {
        		InvoiceItemModel item = new InvoiceItemModel();
                item.setProductId(product.getProductId());
                item.setQuantity(quantity);
                item.setPrice(product.getPrice());
                item.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
                item.setItemName(product.getProductName());
                dashboardController.addOrderItem(item);
                refresh();
            }
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    private void refresh() {
    	quantity = 0;
    	quantityField.setText("0");
    	totalLabel.setText("");
    }
    
    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }
}