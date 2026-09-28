package Spring.Boot.mini.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import Spring.Boot.mini.DTO.ResponseStructure;
import Spring.Boot.mini.Entity.Address;
import Spring.Boot.mini.Entity.Bank;
import Spring.Boot.mini.Repository.BankRepository;
import Spring.Boot.mini.Service.BankService;

@RestController
@RequestMapping("/bank")
public class BankController {

	@Autowired
	private BankRepository bankRepository;
	@Autowired
	private BankService bankService;
	
	//save the data
	@PostMapping
	public ResponseEntity<ResponseStructure<Bank>> postBank(@RequestBody Bank bank){
		return new ResponseEntity<>(bankService.saveBank(bank),HttpStatus.CREATED);
	} 
	
	//get all bank
	@GetMapping("/all")
	public ResponseEntity<ResponseStructure<List<Bank>>> getAllBank(){
		return new ResponseEntity<>(bankService.getAllBank(),HttpStatus.OK);
	}
	
	//get bank by bank id
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Bank>> getBankById(@PathVariable int id){
		return new ResponseEntity<>(bankService.getBankById(id),HttpStatus.OK);
	}
	
	//delete bank by bank id
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<Bank>> deleteBankById(@PathVariable int id){
		return new ResponseEntity<>(bankService.deleteBankById(id),HttpStatus.OK);
	}
	// 5. Update Bank: PUT http://localhost:8080/bank/update/1
    @PutMapping("/update/{bankId}")
    public ResponseEntity<ResponseStructure<Bank>> updateBank(@PathVariable int bankId, @RequestBody Bank bank) {
        return new ResponseEntity<>(bankService.updateBank(bankId, bank), HttpStatus.OK);
    }

    // 6. Pagination & Sorting: GET http://localhost:8080/bank/pagination?pageNumber=0&pageSize=5&sortBy=bankName
    @GetMapping("/pagination")
    public ResponseEntity<ResponseStructure<List<Bank>>> getBanksWithPaginationAndSorting(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "bankId") String sortBy) {
        return new ResponseEntity<>(bankService.getBanksWithPaginationAndSorting(pageNumber, pageSize, sortBy), HttpStatus.OK);
    }

    // 7. By IFSC: GET http://localhost:8080/bank/ifsc/HDFC0000123
    @GetMapping("/ifsc/{ifsc}")
    public ResponseEntity<ResponseStructure<Bank>> getBankByIfsc(@PathVariable String ifsc) {
        return new ResponseEntity<>(bankService.getBankByIfsc(ifsc), HttpStatus.OK);
    }

    // 8. By Address Entity Body: POST http://localhost:8080/bank/address
    @PostMapping("/address")
    public ResponseEntity<ResponseStructure<Bank>> getBankByAddress(@RequestBody Address address) {
        return new ResponseEntity<>(bankService.getBankByAddress(address), HttpStatus.OK);
    }

    // 9. By City String: GET http://localhost:8080/bank/city/Bengaluru
    @GetMapping("/city/{city}")
    public ResponseEntity<ResponseStructure<List<Bank>>> getBanksByCity(@PathVariable String city) {
        return new ResponseEntity<>(bankService.getBanksByCity(city), HttpStatus.OK);
    }

    // 10. By Contact Number: GET http://localhost:8080/bank/contact/9876543210
    @GetMapping("/contact/{contactNumber}")
    public ResponseEntity<ResponseStructure<Bank>> getBankByContactNumber(@PathVariable String contactNumber) {
        return new ResponseEntity<>(bankService.getBankByContactNumber(contactNumber), HttpStatus.OK);
    }
}
