package proj.Kape.Kapehan.service;

import proj.Kape.Kapehan.models.ProductModel;
import proj.Kape.Kapehan.utils.DbConfig;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

    public int addProduct(ProductModel product) {
        String sql = "INSERT INTO products (name, type, price) VALUES (?, ?, ?)";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, product.getProductName());
            stmt.setString(2, String.valueOf(product.getProductType()));
            stmt.setBigDecimal(3, product.getPrice());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                System.out.println("❌ Inserting product failed, no rows affected.");
                return -1;
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    System.out.println("❌ Inserting product failed, no ID obtained.");
                    return -1;
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
            return -1;
        }
    }

    public ProductModel getProductById(int productId) {
        String sql = "SELECT * FROM products WHERE product_id = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new ProductModel(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("type").charAt(0),
                        rs.getBigDecimal("price")
                );
            }
        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
        }
        return null;
    }

    public List<ProductModel> getAllProducts() {
        List<ProductModel> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = DbConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(new ProductModel(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("type").charAt(0),
                        rs.getBigDecimal("price")
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
        }
        return products;
    }

    public boolean updateProduct(ProductModel product) {
        String sql = "UPDATE products SET name = ?, type = ?, price = ? WHERE product_id = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getProductName());
            stmt.setString(2, String.valueOf(product.getProductType()));
            stmt.setBigDecimal(3, product.getPrice());
            stmt.setInt(4, product.getProductId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteProduct(int productId) {
        String sql = "DELETE FROM products WHERE product_id = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
            return false;
        }
    }
}
