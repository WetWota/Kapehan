package proj.Kape.Kapehan.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Base64;

public class DbConfig {
    private static final String dbURL = "jdbc:mysql://localhost:3306/kapehan";
    private static final String dbUser = "root";
    private static final String encodedPass = "UGF1bGFzYW50b3NAMDgyNjAz"; // Base64 encoded password

    public static Connection getConnection() throws SQLException {
        String dbPass = decodeBase64Password(encodedPass);
        return DriverManager.getConnection(dbURL, dbUser, dbPass);
    }

    private static String decodeBase64Password(String encodedPass) {
        return new String(Base64.getDecoder().decode(encodedPass));
    }
}