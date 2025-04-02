package proj.Kape.Kapehan.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class InvoiceModel {
    private int invoiceId;
    private LocalDate transactionDate;
    private LocalTime transactionTime;
    private BigDecimal total;

    private List<InvoiceItemModel> items;

    public InvoiceModel() {}

    public InvoiceModel(int invoiceId, LocalDate transactionDate, LocalTime transactionTime, BigDecimal total, List<InvoiceItemModel> items) {
        this.invoiceId = invoiceId;
        this.transactionDate = transactionDate;
        this.transactionTime = transactionTime;
        this.total = total;
        this.items = items;
    }

    public int getInvoiceId() { return invoiceId; }
    public LocalDate getTransactionDate() { return transactionDate; }
    public LocalTime getTransactionTime() { return transactionTime; }
    public BigDecimal getTotal() { return total; }
    public List<InvoiceItemModel> getItems() { return items; }

    public void setInvoiceId(int invoiceId) { this.invoiceId = invoiceId; }
    public void setTransactionDate(LocalDate transactionDate) { this.transactionDate = transactionDate; }
    public void setTransactionTime(LocalTime transactionTime) { this.transactionTime = transactionTime; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public void setItems(List<InvoiceItemModel> items) { this.items = items; }
    
    @Override
    public String toString() {
        return "InvoiceModel {" +
               "invoiceId=" + invoiceId +
               ", transactionDate=" + transactionDate +
               ", transactionTime=" + transactionTime +
               ", total=" + total +
               ", items=" + items +
               '}';
    }
}