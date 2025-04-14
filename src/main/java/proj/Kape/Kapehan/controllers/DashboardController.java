package proj.Kape.Kapehan.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import proj.Kape.Kapehan.models.InvoiceItemModel;
import proj.Kape.Kapehan.utils.SceneManager;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DashboardController {
	
	private ProductGridController productGridController;
	private GridPane orderGrid;
	private final List<InvoiceItemModel> orderItems = new ArrayList<>();
	
	private BigDecimal total = new BigDecimal("0");
	@FXML private Pane itemListPane;
	@FXML private Pane orderPane;
	@FXML private TextField cashField;
	@FXML private TextField totalField;
	@FXML private TextField changeField;
	@FXML private Button logoutBTN;
	@FXML private Button submitBTN;
	@FXML private Label alertLabel;
	
	@FXML
	private void initialize() {
		setupOrderGrid();
		setupMenuListGrid();
		alertLabel.setText("");
	}
	
	@FXML
	private void setupMenuListGrid() {
		GridPane productGrid = new GridPane();
		
		productGrid.prefWidthProperty().bind(itemListPane.widthProperty());
        productGrid.prefHeightProperty().bind(itemListPane.heightProperty());
        
        itemListPane.getChildren().clear();
        itemListPane.getChildren().add(productGrid);
        
        productGridController = new ProductGridController(productGrid, this);
	}
	
	private void setupOrderGrid() {
        orderGrid = new GridPane();
        orderGrid.setHgap(10);
        orderGrid.setVgap(10);
        orderPane.getChildren().add(orderGrid);
        refreshOrderGrid();
    }
	
	public void addOrderItem(InvoiceItemModel item) {
        orderItems.add(item);
        refreshOrderGrid();
    }
	
	private BigDecimal calculateTotal() {
	    return orderItems.stream()
	            .map(InvoiceItemModel::getSubtotal)
	            .reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	private void refreshOrderGrid() {
        orderGrid.getChildren().clear();

        // Add headers
        orderGrid.add(new Label(""), 0, 0);
        orderGrid.add(new Label("Name"), 1, 0);
        orderGrid.add(new Label("Qty"), 2, 0);
        orderGrid.add(new Label("Price"), 3, 0);
        orderGrid.add(new Label("Subtotal"), 4, 0);
        orderGrid.add(new Label("Action"), 5, 0);

        // Add order rows
        for (int i = 0; i < orderItems.size(); i++) {
        	final int index = i;
            InvoiceItemModel item = orderItems.get(i);
            
            int row = i + 1;

            String productName = item.getItemName();
            BigDecimal price = item.getPrice();
            BigDecimal subTotal = item.getSubtotal();
            
            orderGrid.add(new Label(String.valueOf(row)), 0, row);
            orderGrid.add(new Label(productName), 1, row);
            orderGrid.add(new Label(String.valueOf(item.getQuantity())), 2, row);
            
            orderGrid.add(new Label("₱" + price), 3, row);
            
            orderGrid.add(new Label("₱" + subTotal), 4, row);

            Button removeBtn = new Button("Remove");
            removeBtn.setOnAction(e -> {
                orderItems.remove(index);
                refreshOrderGrid();
            });
            orderGrid.add(removeBtn, 5, row);
        }
        total = calculateTotal();
        totalField.setText(total.toString());
        System.out.println(total);
    }
	
	@FXML
	private void calculate(ActionEvent event) {
		try {
			Double cash = Double.parseDouble(cashField.getText());
			Double total = Double.parseDouble(totalField.getText());
			if(total > cash) {
				showAlert("invalid");
			} else {
				Double change = cash - total;
				changeField.setText(String.valueOf(change));
				submitBTN.requestFocus();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleSubmit(ActionEvent event) {
		try {
			Double cash = Double.parseDouble(cashField.getText());
			Double total = Double.parseDouble(totalField.getText());
			if(total > cash) {
				showAlert("invalid");
			} else {
				Double change = cash - total;
				changeField.setText(String.valueOf(change));
				clearOrder();
				showAlert("Invoice Created");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void clearOrder(){
		orderItems.clear();
		orderGrid.getChildren().clear();
		total = new BigDecimal("0");
		cashField.setText("");
		totalField.setText("");
		changeField.setText("");
	}
	
    private void showAlert(String message) {
    	alertLabel.setText(message);
    }
    
    @FXML
    private void productManager(ActionEvent event) {
    	try {
        	Stage productManager = new Stage();
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/scenes/ProductPane.fxml"));
        	Parent root = loader.load();
        	ProductPaneController productPaneController = loader.getController();
            productPaneController.setProductUpdateListener(() -> {
                setupMenuListGrid(); // Refresh the product grid
            });
        	Scene login = new Scene(root);
    		productManager.setScene(login);
            productManager.setTitle("Void invoice");
            productManager.show();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
	
	@FXML
	private void handleLogout() {
		try {
			SceneManager.switchLogin();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public interface ProductUpdateListener {
	    void onProductUpdated();
	}
}
