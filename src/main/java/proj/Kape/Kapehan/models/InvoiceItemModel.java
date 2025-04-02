package proj.Kape.Kapehan.models;

import java.math.BigDecimal;

public class InvoiceItemModel {
    private int itemId;
    private int invoiceId;
    private int productId;
    private String type;
    private int quantity;
    private BigDecimal subtotal;

    // Constructors
    public InvoiceItemModel() {}

    public InvoiceItemModel(int itemId, int invoiceId, int productId, String type, int quantity, BigDecimal subtotal) {
        this.itemId = itemId;
        this.invoiceId = invoiceId;
        this.productId = productId;
        this.type = type;
        this.quantity = quantity;
        this.subtotal = subtotal;
    }

    // Getters
    public int getItemId() { return itemId; }
    public int getInvoiceId() { return invoiceId; }
    public int getProductId() { return productId; }
    public String getType() { return type; }
    public int getQuantity() { return quantity; }
    public BigDecimal getSubtotal() { return subtotal; }

    // Setters
    public void setItemId(int itemId) { this.itemId = itemId; }
    public void setInvoiceId(int invoiceId) { this.invoiceId = invoiceId; }
    public void setProductId(int productId) { this.productId = productId; }
    public void setSize(String type) { this.type = type; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    @Override
    public String toString() {
        return "InvoiceItem {" +
               "itemId=" + itemId +
               ", invoiceId=" + invoiceId +
               ", productId=" + productId +
               ", type='" + type + '\'' +
               ", quantity=" + quantity +
               ", subtotal=" + subtotal +
               '}';
    }
}