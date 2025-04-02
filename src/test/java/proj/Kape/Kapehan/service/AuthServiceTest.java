package proj.Kape.Kapehan.service;

import java.util.Scanner;
import proj.Kape.Kapehan.service.AuthService;

public class AuthServiceTest {
    public static void main(String[] args) {
        AuthService authService = new AuthService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Register User");
            System.out.println("2. Login");
            System.out.println("3. Get Account ID");
            System.out.println("4. Get Role");
            System.out.println("5. Update Account");
            System.out.println("6. Delete Account");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String regUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String regPassword = scanner.nextLine();
                    System.out.print("Enter role (admin/employee): ");
                    String regRole = scanner.nextLine();
                    boolean registered = authService.registerUser(regUsername, regPassword, regRole);
                    System.out.println(registered ? "✅ Registration successful" : "❌ Registration failed");
                    break;

                case 2:
                    System.out.print("Enter username: ");
                    String loginUsername = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();
                    boolean loggedIn = authService.login(loginUsername, loginPassword);
                    System.out.println(loggedIn ? "✅ Login successful" : "❌ Login failed");
                    break;

                case 3:
                    System.out.print("Enter username: ");
                    String accUsername = scanner.nextLine();
                    int accountId = authService.getAccountId(accUsername);
                    System.out.println(accountId != -1 ? "Account ID: " + accountId : "❌ Account not found");
                    break;

                case 4:
                    System.out.print("Enter username: ");
                    String roleUsername = scanner.nextLine();
                    String role = authService.getRole(roleUsername);
                    System.out.println(role != null ? "Role: " + role : "❌ Role not found");
                    break;

                case 5:
                    System.out.print("Enter username to update: ");
                    String updateUsername = scanner.nextLine();
                    System.out.print("Enter new role (leave blank to keep current): ");
                    String newRole = scanner.nextLine();
                    System.out.print("Enter new password (leave blank to keep current): ");
                    String newPassword = scanner.nextLine();
                    boolean updated = authService.updateAccount(updateUsername, 
                                                                 newRole.isEmpty() ? null : newRole, 
                                                                 newPassword.isEmpty() ? null : newPassword);
                    System.out.println(updated ? "✅ Update successful" : "❌ Update failed");
                    break;
                
                case 6:
                    System.out.print("Enter username to delete: ");
                    String deleteUsername = scanner.nextLine();
                    boolean deleted = authService.deleteAccount(deleteUsername);
                    System.out.println(deleted ? "✅ Account deleted successfully" : "❌ Account deletion failed");
                    break;

                case 7:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }
        }
    }
}
