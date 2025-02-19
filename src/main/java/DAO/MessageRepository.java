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

    public Message getMessageById(Integer id) {
        String sql = "SELECT * FROM Message WHERE message_id = ?";
        try (Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Message message = new Message(
                            rs.getInt("message_id"),
                            rs.getInt("posted_by"),
                            rs.getString("message_text"),
                            rs.getLong("time_posted_epoch")
                        );
                        return message;
                    }
                } 
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
    }

    public Message deleteMessageById(Integer id) {
        String sql = "SELECT * FROM Message WHERE message_id = ?";
        String sql2 = "DELETE FROM Message WHERE message_id = ?";
        Message message = null;

        try (Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
                stmt.setInt(1, id);
                stmt2.setInt(1, id);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        message = new Message(
                            rs.getInt("message_id"),
                            rs.getInt("posted_by"),
                            rs.getString("message_text"),
                            rs.getLong("time_posted_epoch")
                        );
                    }
                }
                if (message != null) {
                    int affectedRows = stmt2.executeUpdate();
                    if (affectedRows > 0) {
                        return message;
                    } 
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
    }

    public Message updateMessageById(Integer id, String text) {
        String sql = "SELECT * FROM Message WHERE message_id = ?";
        Message message = null;

        try (Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        message = new Message(
                            rs.getInt("message_id"),
                            rs.getInt("posted_by"),
                            rs.getString("message_text"),
                            rs.getLong("time_posted_epoch")
                        );
                    }
                }
                if (message != null) {
                    message.setMessage_text(text);
                    return message;
                } else {
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
    }

    public ArrayList<Message> getMessagesByAccount(Integer Id) {
        String sql = "SELECT * FROM Message WHERE posted_by = ?";
        ArrayList<Message> messages = new ArrayList<Message>();
        try (Connection conn = ConnectionUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, Id);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Message message = new Message(
                            rs.getInt("message_id"),
                            rs.getInt("posted_by"),
                            rs.getString("message_text"),
                            rs.getLong("time_posted_epoch")
                        );
                        messages.add(message);
                    }
                    return messages;
                }
            } catch (SQLException e) {
                e.printStackTrace();

            }
            return null;
    }
}
