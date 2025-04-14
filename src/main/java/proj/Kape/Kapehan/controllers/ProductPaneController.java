package proj.Kape.Kapehan.controllers;

import java.math.BigDecimal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import proj.Kape.Kapehan.controllers.DashboardController.ProductUpdateListener;
import proj.Kape.Kapehan.models.ProductModel;
import proj.Kape.Kapehan.service.ProductService;

public class ProductPaneController {
	private final ProductService productService = new ProductService();
	
	private ProductUpdateListener productUpdateListener;
	
	@FXML private TextField idField;
	@FXML private TextField nameField;
	@FXML private TextField priceField;
	@FXML private Label modeLabel;
	@FXML private Label idLabel;
	@FXML private ToggleButton editToggle;
	@FXML private Button submitBTN;
	@FXML private Label statusLabel;
	
	private boolean editMode = false;
	
	@FXML
	private void initialize() {
		editOff();
	}
	
	public void setProductUpdateListener(ProductUpdateListener listener) {
	    this.productUpdateListener = listener;
	}
	
	@FXML
	private void idFieldAction() {
		nameField.requestFocus();
	}
	
	@FXML
	private void nameFieldAction() {
		priceField.requestFocus();
	}
	
	@FXML
	private void priceFieldAction(){
		if(editMode) {
			handleEdit();
		} else {
			handleAdd();
			nameField.setText(null);
			priceField.setText(null);
		}
	}
	
	@FXML
	private void handleAdd() {
		try {
			String name = nameField.getText().trim();
			BigDecimal price = BigDecimal.valueOf(Double.parseDouble(priceField.getText().trim()));
			
			ProductModel productModel = new ProductModel(0, name, 'd', price );
			productService.addProduct(productModel);
			statusLabel.setText(name + " has been added");
			if (productUpdateListener != null) {
	            productUpdateListener.onProductUpdated();
	        }
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleEdit() {
		try {
			int id = Integer.parseInt(idField.getText().trim());
			String name = nameField.getText().trim();
			BigDecimal price = BigDecimal.valueOf(Double.parseDouble(priceField.getText().trim()));
			ProductModel productModel = new ProductModel(id, name, 'd', price);
			productService.updateProduct(productModel);
			statusLabel.setText("Item has been updated to " + name);	
			if (productUpdateListener != null) {
	            productUpdateListener.onProductUpdated();
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void BTNAction(ActionEvent event) {
		if(!editMode) {
			handleAdd();
			
		} else {
			handleEdit();
		}
	}
	
	@FXML
	private void editOn() {
		idLabel.setVisible(true);
		idField.setVisible(true);
		modeLabel.setText("Edit Product");
		submitBTN.setText("Apply");
	}
	
	@FXML
	private void editOff() {
		idLabel.setVisible(false);
		idField.setVisible(false);
		modeLabel.setText("Add Product");
		submitBTN.setText("Add");
	}
	
	@FXML
	private void editToggle(ActionEvent event) {
		try {
			if(editToggle.isSelected()) {
				editOn();
				editMode = true;
			} else {
				editOff();
				editMode = false;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
