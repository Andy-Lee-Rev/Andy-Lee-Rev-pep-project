package DAO;

import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;

public class MessageRepository {
    public Message postMessage(Message msg) {
        String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, msg.getPosted_by());
                stmt.setString(2, msg.getMessage_text());
                stmt.setLong(3, msg.getTime_posted_epoch());

                int affectedRows = stmt.executeUpdate();
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            msg.setMessage_id(generatedKeys.getInt(1));
                        }
                        return msg;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
    }
    
    public ArrayList<Message> getAllMessages() {
        String sql = "SELECT * FROM Message";
        ArrayList<Message> messages = new ArrayList<Message>();
        try (Connection conn = ConnectionUtil.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Message message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch")
                );
                messages.add(message);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
}
