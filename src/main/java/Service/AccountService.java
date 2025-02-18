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
    
}
