package Spring.Boot.mini.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Spring.Boot.mini.DTO.ResponseStructure;
import Spring.Boot.mini.Entity.Account;
import Spring.Boot.mini.Entity.AccountType;
import Spring.Boot.mini.Entity.Bank;
import Spring.Boot.mini.Repository.AccountRepository;
import Spring.Boot.mini.Service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AccountService accountService;
	
	//save the data
		@PostMapping
		public ResponseEntity<ResponseStructure<Account>> postAccount(@RequestBody Account account){
			return new ResponseEntity<>(accountService.saveAccount(account),HttpStatus.CREATED);
		}
		
		//get all account
		@GetMapping("/all")
		public ResponseEntity<ResponseStructure<List<Account>>> getAllAccount(){
			return new ResponseEntity<>(accountService.getAllAccount(),HttpStatus.OK);
		}
		
		//get bank by bank id
		@GetMapping("/{id}")
		public ResponseEntity<ResponseStructure<Account>> getAccountById(@PathVariable int id){
			return new ResponseEntity<>(accountService.getAccountById(id),HttpStatus.OK);
		}
		
		//delete Account by account id
		@DeleteMapping("/{id}")
		public ResponseEntity<ResponseStructure<Account>> deleteAccountById(@PathVariable int id){
			return new ResponseEntity<>(accountService.deleteAccountById(id),HttpStatus.OK);
		}
		
		// Endpoint: PATCH http://localhost:8080/accounts/update/Kuppam Eswari
		@PatchMapping("/update/{holderName}")
		public ResponseEntity<ResponseStructure<Account>> updateAccountTypeAndHolderName(
		        @PathVariable String holderName,
		        @RequestBody Account accountUpdates) {
		        
		    return new ResponseEntity<>(accountService.updateAccountTypeAndHolderName(
		            holderName, 
		            accountUpdates.getAccountHolderName(), 
		            accountUpdates.getAccountType()
		    ), HttpStatus.OK);
		}
		
	    @PutMapping("/deposit/{accountId}")
	    public ResponseEntity<ResponseStructure<Account>> deposit(@PathVariable int accountId, @RequestParam double amount) {
	        return new ResponseEntity<>(accountService.deposit(accountId, amount), HttpStatus.OK);
	    }

	    // PUT http://localhost:8080/accounts/withdraw/1?amount=200.00
	    @PutMapping("/withdraw/{accountId}")
	    public ResponseEntity<ResponseStructure<Account>> withdraw(@PathVariable int accountId, @RequestParam double amount) {
	        return new ResponseEntity<>(accountService.withdraw(accountId, amount), HttpStatus.OK);
	    }

	    // PUT http://localhost:8080/accounts/transfer?senderId=1&receiverId=2&amount=300.00
	    @PutMapping("/transfer")
	    public ResponseEntity<ResponseStructure<String>> transfer(
	            @RequestParam int senderId, 
	            @RequestParam int receiverId, 
	            @RequestParam double amount) {
	        return new ResponseEntity<>(accountService.transfer(senderId, receiverId, amount), HttpStatus.OK);
	    }
	 // Endpoint: GET http://localhost:8080/accounts/bank/1
	    @GetMapping("/bank/{bankId}")
	    public ResponseEntity<ResponseStructure<List<Account>>> getAccountsByBankId(@PathVariable int bankId) {
	        return new ResponseEntity<>(accountService.getAccountsByBankId(bankId), HttpStatus.OK);
	    }
	 // Endpoint: GET http://localhost:8080/accounts/type/SAVINGS
	    @GetMapping("/type/{accountType}")
	    public ResponseEntity<ResponseStructure<List<Account>>> getAccountsByType(@PathVariable AccountType accountType) {
	        return new ResponseEntity<>(accountService.getAccountsByType(accountType), HttpStatus.OK);
	    }
	    
	 // Endpoint: GET http://localhost:8080/accounts/balance-greater-than/5000
	    @GetMapping("/balance-greater-than/{balance}")
	    public ResponseEntity<ResponseStructure<List<Account>>> getAccountsWithBalanceGreaterThan(@PathVariable double balance) {
	        return new ResponseEntity<>(accountService.getAccountsWithBalanceGreaterThan(balance), HttpStatus.OK);
	    }
}
