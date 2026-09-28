package Spring.Boot.mini.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import Spring.Boot.mini.DTO.ResponseStructure;
import Spring.Boot.mini.Entity.Account;
import Spring.Boot.mini.Entity.AccountType;
import Spring.Boot.mini.Entity.Bank;
import Spring.Boot.mini.Exception.IdNotFoundException;
import Spring.Boot.mini.Exception.NotUniqueValue;
import Spring.Boot.mini.Exception.RecordNotFound;
import Spring.Boot.mini.Repository.AccountRepository;
import Spring.Boot.mini.Repository.BankRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private BankRepository bankRepository;

    // 1. Save Account Logic
    public ResponseStructure<Account> saveAccount(Account account) {
        
        // Business Logic: The parent Bank must exist before an account can be created
        if (account.getBank() == null || !bankRepository.existsById(account.getBank().getBankId())) {
            throw new IllegalArgumentException("Bank must exist before saving an account.");
        }

        // Business Logic: Account number must be unique
        if (accountRepository.existsByAccountNumber(account.getAccountNumber())) {
            throw new NotUniqueValue("Account number should be unique");
        }

        // Business Logic: Enforce 1000 minimum balance for SAVINGS and CURRENT accounts
        AccountType type = account.getAccountType();
        if ((type == AccountType.SAVINGS || type == AccountType.CURRENT) && account.getBalance() < 1000) {
            throw new IllegalArgumentException("Minimum balance for " + type + " account must be at least 1000");
        }

        Account savedAccount = accountRepository.save(account);

        ResponseStructure<Account> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.CREATED.value());
        res.setMessage("Account successfully created");
        res.setData(savedAccount);
        
        return res;
    }

    // 2. Fetch Accounts by Bank ID Logic
    public ResponseStructure<List<Account>> getAccountsByBankId(int bankId) {
    	List<Account> accounts = accountRepository.findByBank_BankId(bankId);
        
        if (accounts.isEmpty()) {
            throw new RecordNotFound("No accounts found registered under Bank ID: " + bankId);
        }
        
        ResponseStructure<List<Account>> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Accounts retrieved successfully for Bank ID: " + bankId);
        res.setData(accounts);
        
        return res;
    }

    // 3. Deposit Logic
    @Transactional
    public ResponseStructure<Account> deposit(int accountId, double amount) {
        // Rule: Deposit amount must be positive
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RecordNotFound("Account not found with ID: " + accountId));

        account.setBalance(account.getBalance() + amount);
        Account updatedAccount = accountRepository.save(account);

        ResponseStructure<Account> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Amount deposited successfully.");
        res.setData(updatedAccount);
        return res;
    }

    // 4. Withdraw Logic
    @Transactional
    public ResponseStructure<Account> withdraw(int accountId, double amount) {
        // Rule: Withdraw amount must be positive
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdraw amount must be positive");
        }

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RecordNotFound("Account not found with ID: " + accountId));

        // Rule: Withdraw amount must be less than or equal to balance
        if (amount > account.getBalance()) {
            throw new IllegalArgumentException("Insufficient balance. Cannot withdraw more than your current balance.");
        }

        account.setBalance(account.getBalance() - amount);
        Account updatedAccount = accountRepository.save(account);

        ResponseStructure<Account> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Amount withdrawn successfully.");
        res.setData(updatedAccount);
        return res;
    }

    // 5. Transfer Logic
    @Transactional
    public ResponseStructure<String> transfer(int senderId, int receiverId, double amount) {
        // Rule: Sender and receiver must not be the same
        if (senderId == receiverId) {
            throw new IllegalArgumentException("Sender and receiver account IDs must not be the same");
        }

        // Rule: Transfer amount must be positive
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        Account sender = accountRepository.findById(senderId)
                .orElseThrow(() -> new RecordNotFound("Sender account not found with ID: " + senderId));

        Account receiver = accountRepository.findById(receiverId)
                .orElseThrow(() -> new RecordNotFound("Receiver account not found with ID: " + receiverId));

        // Rule: Transfer amount must be less than or equal to balance
        if (amount > sender.getBalance()) {
            throw new IllegalArgumentException("Insufficient balance. Transfer amount exceeds sender balance.");
        }

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        accountRepository.save(sender);
        accountRepository.save(receiver);

        ResponseStructure<String> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Transfer successful!");
        res.setData("Transferred " + amount + " from Account ID " + senderId + " to Account ID " + receiverId);
        return res;
    }
    
  //getting all bank details
  	public ResponseStructure<List<Account>> getAllAccount() {
  		List<Account> bank=accountRepository.findAll();
  		ResponseStructure<List<Account>> res=new ResponseStructure<>();
  		if(bank.isEmpty())
  			throw new RecordNotFound("no record available");
  		else {
  			res.setStatusCode(HttpStatus.OK.value());
  			res.setMessage("account retrived successfully");
  			res.setData(bank);
  			return res;
  		}
  		
  	}
  	
  //getting details by account id
  	public ResponseStructure<Account> getAccountById(@PathVariable int id) {
  		Optional<Account> bank=accountRepository.findById(id);
  		ResponseStructure<Account> res=new ResponseStructure<Account>();
  		if(bank.isPresent()) {
  			Account banks=bank.get();
  			res.setStatusCode(HttpStatus.OK.value());
  			res.setMessage(id+" account retrived successfully");
  			res.setData(banks);
  			return res;
  		}
  		else
  			throw new IdNotFoundException(id+"id is not available");
  	}
  	
  //delete Account by id
  	public ResponseStructure<Account> deleteAccountById(int id) {
  		Optional<Account> bank=accountRepository.findById(id);
  		ResponseStructure<Account> res=new ResponseStructure<Account>();
  		if(bank.isPresent()) {
  			accountRepository.delete(bank.get());
  			Account banks=bank.get();
  			res.setStatusCode(HttpStatus.OK.value());
  			res.setMessage(id+" book is deleted successfully");
  			res.setData(banks);
  			return res;
  		}
  		else
  			throw new IdNotFoundException(id+"id is not available");
  	}
  	
 // Partial update for account type and holder name using PATCH
  	public ResponseStructure<Account> updateAccountTypeAndHolderName(String holderName, String newHolderName, AccountType newAccountType) {
  	    // 1. Fetch the account profile by holder name
  	    // (Assumes you have findByAccountHolderName defined in your repository)
  	    Account existingAccount = accountRepository.findByAccountHolderName(holderName);
  	    
  	    if (existingAccount == null) {
  	        throw new RecordNotFound("Account not found with holder name: " + holderName);
  	    }

  	    // 2. Apply partial updates dynamically if provided
  	    if (newHolderName != null && !newHolderName.trim().isEmpty()) {
  	        existingAccount.setAccountHolderName(newHolderName);
  	    }
  	    
  	    if (newAccountType != null) {
  	        // Enforce the 1000 minimum balance check if they are switching to SAVINGS or CURRENT
  	        if ((newAccountType == AccountType.SAVINGS || newAccountType == AccountType.CURRENT) 
  	                && existingAccount.getBalance() < 1000) {
  	            throw new IllegalArgumentException("Cannot change type to " + newAccountType + ". Current balance is below 1000.");
  	        }
  	        existingAccount.setAccountType(newAccountType);
  	    }

  	    // 3. Save updated entity
  	    Account updatedAccount = accountRepository.save(existingAccount);

  	    ResponseStructure<Account> res = new ResponseStructure<>();
  	    res.setStatusCode(HttpStatus.OK.value());
  	    res.setMessage("Account details partially updated successfully using PATCH");
  	    res.setData(updatedAccount);
  	    
  	    return res;
  	}
 // Fetch all accounts belonging to a specific Account Type
  	public ResponseStructure<List<Account>> getAccountsByType(AccountType accountType) {
  	    List<Account> accounts = accountRepository.findByAccountType(accountType);
  	    
  	    if (accounts.isEmpty()) {
  	        throw new RecordNotFound("No accounts found with type: " + accountType);
  	    }
  	    
  	    ResponseStructure<List<Account>> res = new ResponseStructure<>();
  	    res.setStatusCode(HttpStatus.OK.value());
  	    res.setMessage("Accounts retrieved successfully for type: " + accountType);
  	    res.setData(accounts);
  	    
  	    return res;
  	}
  	
 // Fetch all accounts with a balance strictly greater than the given value
  	public ResponseStructure<List<Account>> getAccountsWithBalanceGreaterThan(double balance) {
  	    List<Account> accounts = accountRepository.findByBalanceGreaterThan(balance);
  	    
  	    if (accounts.isEmpty()) {
  	        throw new RecordNotFound("No accounts found with a balance greater than: " + balance);
  	    }
  	    
  	    ResponseStructure<List<Account>> res = new ResponseStructure<>();
  	    res.setStatusCode(HttpStatus.OK.value());
  	    res.setMessage("Accounts with balance greater than " + balance + " retrieved successfully");
  	    res.setData(accounts);
  	    
  	    return res;
  	}
}