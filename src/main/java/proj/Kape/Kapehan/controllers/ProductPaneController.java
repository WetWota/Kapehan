package proj.Kape.Kapehan.controllers;

import java.math.BigDecimal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import proj.Kape.Kapehan.models.ProductModel;
import proj.Kape.Kapehan.service.ProductService;

public class ProductPaneController {
	private final ProductService productService = new ProductService();
	
	@FXML private TextField idField;
	@FXML private TextField nameField;
	@FXML private TextField priceField;
	@FXML private Label modeLabel;
	@FXML private Label idLabel;
	@FXML private ToggleButton editToggle;
	@FXML private Button submitBTN;
	
	private boolean editMode = false;
	
	@FXML
	private void initialize() {
		editOff();
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
		}
	}
	
	@FXML
	private void handleAdd() {
		try {
			String name = nameField.getText().trim();
			BigDecimal price = BigDecimal.valueOf(Double.parseDouble(priceField.getText().trim()));
			
			ProductModel productModel = new ProductModel(0, name, 'd', price );
			productService.addProduct(productModel);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleEdit() {
		
		
		
		String name = nameField.getText().trim();
		BigDecimal price = BigDecimal.valueOf(Double.parseDouble(priceField.getText().trim()));
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
