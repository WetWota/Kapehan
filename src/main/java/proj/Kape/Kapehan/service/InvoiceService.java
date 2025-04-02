package proj.Kape.Kapehan.service;

import proj.Kape.Kapehan.models.InvoiceModel;
import proj.Kape.Kapehan.models.InvoiceItemModel;
import proj.Kape.Kapehan.utils.DbConfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceService {
    
    public int createInvoice(InvoiceModel invoice) {
        String insertInvoiceSQL = "INSERT INTO invoice (transaction_date, transaction_time, total) VALUES (?, ?, ?)";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertInvoiceSQL, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setDate(1, Date.valueOf(invoice.getTransactionDate()));
            pstmt.setTime(2, Time.valueOf(invoice.getTransactionTime()));
            pstmt.setBigDecimal(3, invoice.getTotal());
            pstmt.executeUpdate();
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public void addInvoiceItem(InvoiceItemModel item) {
        String insertItemSQL = "INSERT INTO invoice_items (invoice_id, product_id, size, quantity, subtotal) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertItemSQL)) {
            
            pstmt.setInt(1, item.getInvoiceId());
            pstmt.setInt(2, item.getProductId());
            pstmt.setString(3, item.getType());
            pstmt.setInt(4, item.getQuantity());
            pstmt.setBigDecimal(5, item.getSubtotal());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public InvoiceModel getInvoiceById(int invoiceId) {
        String selectSQL = "SELECT * FROM invoice WHERE invoice_id = ?";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            
            pstmt.setInt(1, invoiceId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new InvoiceModel(
                        rs.getInt("invoice_id"),
                        rs.getDate("transaction_date").toLocalDate(),
                        rs.getTime("transaction_time").toLocalTime(),
                        rs.getBigDecimal("total"),
                        getInvoiceItems(invoiceId)
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<InvoiceItemModel> getInvoiceItems(int invoiceId) {
        List<InvoiceItemModel> items = new ArrayList<>();
        String selectItemsSQL = "SELECT * FROM invoice_items WHERE invoice_id = ?";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectItemsSQL)) {
            
            pstmt.setInt(1, invoiceId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    items.add(new InvoiceItemModel(
                        rs.getInt("item_id"),
                        rs.getInt("invoice_id"),
                        rs.getInt("product_id"),
                        rs.getString("size"),
                        rs.getInt("quantity"),
                        rs.getBigDecimal("subtotal")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    
    public List<InvoiceModel> getAllInvoices() {
        List<InvoiceModel> invoices = new ArrayList<>();
        String selectAllSQL = "SELECT * FROM invoice";
        
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectAllSQL);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                invoices.add(new InvoiceModel(
                    rs.getInt("invoice_id"),
                    rs.getDate("transaction_date").toLocalDate(),
                    rs.getTime("transaction_time").toLocalTime(),
                    rs.getBigDecimal("total"),
                    getInvoiceItems(rs.getInt("invoice_id"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoices;
    }
}
