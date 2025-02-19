package Service;

import static org.mockito.Mockito.lenient;

import DAO.AccountRepository;
import Model.Account;
import DAO.MessageRepository;
import Model.Message;
import java.util.ArrayList;

public class MessageService {
    private final AccountRepository accountRepo = new AccountRepository();
    private final MessageRepository messageRepo = new MessageRepository();

    public Message postMessage(Message msg) {
        System.out.println("Received message: " + msg);
        // Check for valid message text (not blank, not too long)
        if (!validMessage(msg.getMessage_text())) {
            return null;
        }

        Account existsAccount = accountRepo.findByID(msg.getPosted_by());
        if (existsAccount == null) {
            System.out.println("Account not found: " + msg.getPosted_by());
            return null;
        }

        Message savedMessage = messageRepo.postMessage(msg);
        if (savedMessage == null) {
            System.out.println("Failed to save the message.");
            return null;
        }

        return savedMessage;
    }

    public ArrayList<Message> getAllMessages() {
        return messageRepo.getAllMessages();
    }

    public Message getMessageById(Integer id) {
        return messageRepo.getMessageById(id);
    }

    public Message deleteMessageById(Integer id) {
        return messageRepo.deleteMessageById(id);
    }

    public Message updateMessageById(Integer id, String text) {
        if (!validMessage(text)) {
            return null;
        }
        return messageRepo.updateMessageById(id, text);
    }

    public ArrayList<Message> getMessagesByAccount(Integer id) {
        return messageRepo.getMessagesByAccount(id);
    }

    public boolean validMessage(String str) {
        if (str.length() > 255 || isBlank(str)) {
            System.out.println("Invalid message text: " + str);
            return false;
        }
        return true;
    }

    private boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
}
