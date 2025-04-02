package proj.Kape.Kapehan.service;

import proj.Kape.Kapehan.models.InvoiceItemModel;
import proj.Kape.Kapehan.models.InvoiceModel;
import proj.Kape.Kapehan.utils.DbConfig;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class InvoiceServiceTest {
    public static void main(String[] args) {
        InvoiceService invoiceService = new InvoiceService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Create Invoice");
            System.out.println("2. Add Invoice Item");
            System.out.println("3. Retrieve Invoice by ID");
            System.out.println("4. List All Invoices");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try (Connection conn = DbConfig.getConnection()) {
                switch (choice) {
                    case 1:
                        System.out.print("Enter total amount: ");
                        BigDecimal total = scanner.nextBigDecimal();
                        scanner.nextLine();
                        InvoiceModel invoice = new InvoiceModel(0, LocalDate.now(), LocalTime.now(), total, null);
                        int invoiceId = invoiceService.createInvoice(invoice);
                        System.out.println("✅ Invoice created with ID: " + invoiceId);
                        break;
                    
                    case 2:
                        System.out.print("Enter Invoice ID: ");
                        int invId = scanner.nextInt();
                        System.out.print("Enter Product ID: ");
                        int productId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter Size (M/L): ");
                        String type = scanner.nextLine();
                        System.out.print("Enter Quantity: ");
                        int quantity = scanner.nextInt();
                        System.out.print("Enter Subtotal: ");
                        BigDecimal subtotal = scanner.nextBigDecimal();
                        scanner.nextLine();
                        InvoiceItemModel item = new InvoiceItemModel(0, invId, productId, type, quantity, subtotal);
                        invoiceService.addInvoiceItem(item);
                        System.out.println("✅ Item added to Invoice.");
                        break;
                    
                    case 3:
                        System.out.print("Enter Invoice ID: ");
                        int searchId = scanner.nextInt();
                        scanner.nextLine();
                        InvoiceModel foundInvoice = invoiceService.getInvoiceById(searchId);
                        System.out.println(foundInvoice != null ? foundInvoice : "❌ Invoice not found.");
                        break;
                    
                    case 4:
                        List<InvoiceModel> invoices = invoiceService.getAllInvoices();
                        invoices.forEach(System.out::println);
                        break;
                    
                    case 5:
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
