package Service;

import Model.Account;
import DAO.AccountRepository;

public class AccountService {
    private final AccountRepository accountRepo = new AccountRepository();

    public Account register(Account acc) {
        if (acc.getUsername() == null || acc.getUsername().trim().isEmpty() || accountRepo.findByUsername(acc.getUsername()) != null) {
            return null;
        }

        if (acc.getPassword() == null || acc.getPassword().length() < 4) {
            return null;
        }
        
        return accountRepo.register(acc);
    }

    public Account login(Account acc) {
        if (acc == null || isBlank(acc.getUsername()) || isBlank(acc.getPassword())) {
            return null; 
        }

        Account storedAccount = accountRepo.findByUsername(acc.getUsername());
        if (storedAccount == null) {
            return null; 
        }

        if (!storedAccount.getPassword().equals(acc.getPassword())) {
            return null; 
        }

        return storedAccount;
    }

    public Account findById(Integer id) {
        return accountRepo.findByID(id);
    }

    private boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
    
}
