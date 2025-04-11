package proj.Kape.Kapehan;

import proj.Kape.Kapehan.service.DbConfig;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionTest {
    public static void main(String[] args) {
        try (Connection conn = DbConfig.getConnection()) {
            if (conn != null) {
                System.out.println("✅ Successfully connected to the database!");
            } else {
                System.out.println("❌ Failed to make connection!");
            }
        } catch (SQLException e) {
            System.out.println("❌ Connection error:");
            e.printStackTrace();
        }
    }
}
