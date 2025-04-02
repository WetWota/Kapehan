package proj.Kape.Kapehan.service;

import proj.Kape.Kapehan.models.ProductModel;
import proj.Kape.Kapehan.utils.DbConfig;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ProductServiceTest {
    public static void main(String[] args) {
        ProductService productService = new ProductService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Add Product");
            System.out.println("2. Get Product by ID");
            System.out.println("3. Get All Products");
            System.out.println("4. Update Product");
            System.out.println("5. Delete Product");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try (Connection conn = DbConfig.getConnection()) {
                switch (choice) {
                    case 1:
                        System.out.print("Enter product name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter product type (D/F): ");
                        char type = scanner.nextLine().charAt(0);
                        System.out.print("Enter price: ");
                        BigDecimal price = scanner.nextBigDecimal();
                        scanner.nextLine();
                        ProductModel product = new ProductModel(0, name, type, price);
                        int productId = productService.addProduct(product);
                        System.out.println("✅ Product added with ID: " + productId);
                        break;

                    case 2:
                        System.out.print("Enter product ID: ");
                        int searchId = scanner.nextInt();
                        scanner.nextLine();
                        ProductModel foundProduct = productService.getProductById(searchId);
                        System.out.println(foundProduct != null ? foundProduct : "❌ Product not found.");
                        break;

                    case 3:
                        List<ProductModel> products = productService.getAllProducts();
                        products.forEach(System.out::println);
                        break;

                    case 4:
                        System.out.print("Enter product ID to update: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter new name: ");
                        String newName = scanner.nextLine();
                        System.out.print("Enter new type (R/S): ");
                        char newType = scanner.nextLine().charAt(0);
                        System.out.print("Enter new price: ");
                        BigDecimal newPrice = scanner.nextBigDecimal();
                        scanner.nextLine();
                        ProductModel updatedProduct = new ProductModel(updateId, newName, newType, newPrice);
                        boolean updated = productService.updateProduct(updatedProduct);
                        System.out.println(updated ? "✅ Product updated." : "❌ Update failed.");
                        break;

                    case 5:
                        System.out.print("Enter product ID to delete: ");
                        int deleteId = scanner.nextInt();
                        scanner.nextLine();
                        boolean deleted = productService.deleteProduct(deleteId);
                        System.out.println(deleted ? "✅ Product deleted." : "❌ Deletion failed.");
                        break;

                    case 6:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;

                    default:
                        System.out.println("❌ Invalid choice. Please try again.");
                }
            } catch (SQLException e) {
                System.out.println("❌ Database error: " + e.getMessage());
            }
        }
    }
}
