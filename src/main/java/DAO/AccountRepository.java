package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;

public class AccountRepository {
    
    public Account register(Account acc) {
        String sql = "INSERT INTO Account (username, password) VALUES (?, ?)";
        try (Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, acc.getUsername());
            stmt.setString(2, acc.getPassword());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        acc.setAccount_id(generatedKeys.getInt(1));
                        return acc;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Account findByUsername(String username) {
        String sql = "SELECT * FROM Account WHERE username = ?";
        try (Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
        
    }
}
