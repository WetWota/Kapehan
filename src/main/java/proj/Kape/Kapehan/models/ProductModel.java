package proj.Kape.Kapehan.models;

import java.math.BigDecimal;

public class ProductModel {
	private int productId;
	private String productName;
	private char productType;
	private BigDecimal price;
	
	public ProductModel(int productId, String productName, char productType, BigDecimal price) {
		this.productId = productId;
		this.productName = productName;
		this.productType = productType;
		this.price = price;
	}
	public int getProductId() { return productId; }
	public String getProductName() { return productName; }
	public char getProductType() { return productType; }
	public BigDecimal getPrice() { return price; }
	
	public void setProductName(String productName) { this.productName = productName; }
	public void setProductType(char productType) { this.productType = productType; }
	public void setPrice(BigDecimal price) { this.price = price; }
}