package Spring.Boot.mini.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Spring.Boot.mini.Entity.Account;
import Spring.Boot.mini.Entity.AccountType;

public interface AccountRepository extends JpaRepository<Account,Integer> {
boolean existsByAccountNumber(Long accountNumber);
//Required finder mapping method inside AccountRepository.java
Account findByAccountHolderName(String accountHolderName);
List<Account> findByBank_BankId(int bankId);
List<Account> findByAccountType(AccountType accountType);
List<Account> findByBalanceGreaterThan(double balance);
}
