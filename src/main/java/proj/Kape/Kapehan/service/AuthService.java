package proj.Kape.Kapehan.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

import proj.Kape.Kapehan.utils.DbConfig;

import java.util.Date;

public class AuthService {
    
	public boolean registerUser(String username, String password, String role) {
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		String sqlAccount = "INSERT INTO account_data (username, password, role) VALUES (?, ?, ?)";
		
		
		try (Connection conn = DbConfig.getConnection()) {
			conn.setAutoCommit(false); 
			try (PreparedStatement stmt = conn.prepareStatement(sqlAccount, PreparedStatement.RETURN_GENERATED_KEYS)) {
				stmt.setString(1, username);
				stmt.setString(2, hashedPassword);
				stmt.setString(3, role);
				int affectedRows = stmt.executeUpdate();
		
				
				if (affectedRows == 0) {
					System.out.println("[ERROR] Insert into account_data failed.");
					conn.rollback();
					return false;
			}
			
				ResultSet generatedKeys = stmt.getGeneratedKeys();
				int accountId;
				if (generatedKeys.next()) {
					accountId = generatedKeys.getInt(1);
				} else {
					System.out.println("[ERROR] No account_id returned.");
					conn.rollback();
					return false;
				}
				
			conn.commit();
			return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean login(String username, String password) {
        String sql = "SELECT password FROM account_data WHERE username = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery(); 
            

            if (rs.next()) {
                String storedHash = rs.getString("password");
                return BCrypt.checkpw(password, storedHash);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getAccountId(String username) {
        String sql = "SELECT account_id FROM account_data WHERE username = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("account_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getRole(String username) {
        String sql = "SELECT role FROM account_data WHERE username = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateAccount(String username, String newRole, String newPassword) {
        StringBuilder sql = new StringBuilder("UPDATE account_data SET ");
        boolean updateRole = newRole != null && !newRole.isEmpty();
        boolean updatePassword = newPassword != null && !newPassword.isEmpty();
        
        if (!updateRole && !updatePassword) {
            return false;
        }

        if (updateRole) {
            sql.append("role = ?");
            if (updatePassword) sql.append(", ");
        }
        if (updatePassword) {
            sql.append("password = ?");
        }
        sql.append(" WHERE username = ?");

        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            
            int paramIndex = 1;
            if (updateRole) stmt.setString(paramIndex++, newRole);
            if (updatePassword) stmt.setString(paramIndex++, BCrypt.hashpw(newPassword, BCrypt.gensalt()));
            stmt.setString(paramIndex, username);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAccount(String username) {
        String sql = "DELETE FROM account_data WHERE username = ?";
        try (Connection conn = DbConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

