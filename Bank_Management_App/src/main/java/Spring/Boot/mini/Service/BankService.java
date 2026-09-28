package Spring.Boot.mini.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import Spring.Boot.mini.DTO.ResponseStructure;
import Spring.Boot.mini.Entity.Address;
import Spring.Boot.mini.Entity.Bank;
import Spring.Boot.mini.Exception.IdNotFoundException;
import Spring.Boot.mini.Exception.LengthNotSupport;
import Spring.Boot.mini.Exception.NotUniqueValue;
import Spring.Boot.mini.Exception.RecordNotFound;
import Spring.Boot.mini.Repository.BankRepository;

@Service
public class BankService {
	
	@Autowired
	private BankRepository bankRepository;
	
	//save Bank
	public ResponseStructure<Bank> saveBank(@RequestBody Bank bank){
		String contact=String.valueOf(bank.getContactNumber());
		String pin = bank.getAddress().getPincode()+"";	
		if(bankRepository.existsBycontactNumber(bank.getContactNumber())==true)
			throw new NotUniqueValue("Contact number should be unique");
		
		else if(contact.length()!=10)
			throw new LengthNotSupport("length should be 10");
		
		else if(bankRepository.existsByifsc(bank.getIfsc())==true)
			throw new NotUniqueValue("Ifsc code should be unique");
		else if (bank.getAddress() == null) {
            throw new IllegalArgumentException("Bank cannot be saved without an address profile");
        }
		else if (bankRepository.existsByAddressPincode(pin)) {
            throw new NotUniqueValue("Pincode value should be unique");
        }
		else if(pin.length()!=6)
			throw new LengthNotSupport("length should be 6");
		
		Bank savedBank = bankRepository.save(bank);
		ResponseStructure<Bank> res=new ResponseStructure<Bank>();
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setMessage("book sucessfully loaded");
		res.setData(savedBank);
		return res;
	}

	//getting all bank details
	public ResponseStructure<List<Bank>> getAllBank() {
		List<Bank> bank=bankRepository.findAll();
		ResponseStructure<List<Bank>> res=new ResponseStructure<>();
		if(bank.isEmpty())
			throw new RecordNotFound("no record available");
		else {
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("book retrived successfully");
			res.setData(bank);
			return res;
		}
		
	}

	//getting details by bank id
	public ResponseStructure<Bank> getBankById(@PathVariable int id) {
		Optional<Bank> bank=bankRepository.findById(id);
		ResponseStructure<Bank> res=new ResponseStructure<Bank>();
		if(bank.isPresent()) {
			Bank banks=bank.get();
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage(id+" book retrived successfully");
			res.setData(banks);
			return res;
		}
		else
			throw new IdNotFoundException(id+"id is not available");
	}
	
	//delete bank by id
	public ResponseStructure<Bank> deleteBankById(int id) {
		Optional<Bank> bank=bankRepository.findById(id);
		ResponseStructure<Bank> res=new ResponseStructure<Bank>();
		if(bank.isPresent()) {
			bankRepository.delete(bank.get());
			Bank banks=bank.get();
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage(id+" book is deleted successfully");
			res.setData(banks);
			return res;
		}
		else
			throw new IdNotFoundException(id+"id is not available");
	}
	// 5. Update Bank
    public ResponseStructure<Bank> updateBank(int bankId, Bank updatedBank) {
        Bank existingBank = bankRepository.findById(bankId)
                .orElseThrow(() -> new RecordNotFound("Bank not found with ID: " + bankId));
        
        if (updatedBank.getBankName() != null) existingBank.setBankName(updatedBank.getBankName());
        if (updatedBank.getIfsc() != null) existingBank.setIfsc(updatedBank.getIfsc());
        if (updatedBank.getBranchName() != null) existingBank.setBranchName(updatedBank.getBranchName());
        if (updatedBank.getContactNumber() != null) existingBank.setContactNumber(updatedBank.getContactNumber());
        
        // Handle nested address update if provided
        if (updatedBank.getAddress() != null && existingBank.getAddress() != null) {
            Address existingAddr = existingBank.getAddress();
            Address newAddr = updatedBank.getAddress();
            if (newAddr.getStreet() != null) existingAddr.setStreet(newAddr.getStreet());
            if (newAddr.getCity() != null) existingAddr.setCity(newAddr.getCity());
            if (newAddr.getState() != null) existingAddr.setState(newAddr.getState());
            if (newAddr.getPincode() != null) existingAddr.setPincode(newAddr.getPincode());
        }

        Bank savedBank = bankRepository.save(existingBank);
        
        ResponseStructure<Bank> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Bank updated successfully");
        res.setData(savedBank);
        return res;
    }

    // 6. Get Bank by Pagination & Sorting
    public ResponseStructure<List<Bank>> getBanksWithPaginationAndSorting(int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        Page<Bank> bankPage = bankRepository.findAll(pageable);
        List<Bank> banks = bankPage.getContent();

        if (banks.isEmpty()) {
            throw new RecordNotFound("No banks found on the requested page.");
        }

        ResponseStructure<List<Bank>> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Banks page retrieved successfully.");
        res.setData(banks);
        return res;
    }

    // 7. Get Bank by IFSC
    public ResponseStructure<Bank> getBankByIfsc(String ifsc) {
        Bank bank = bankRepository.findByIfsc(ifsc)
                .orElseThrow(() -> new RecordNotFound("Bank not found with IFSC: " + ifsc));

        ResponseStructure<Bank> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Bank found with IFSC: " + ifsc);
        res.setData(bank);
        return res;
    }

    // 8. Get Bank by Address Object
    public ResponseStructure<Bank> getBankByAddress(Address address) {
        Bank bank = bankRepository.findByAddress(address)
                .orElseThrow(() -> new RecordNotFound("No bank registered at the given address context."));

        ResponseStructure<Bank> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Bank located by address context successfully.");
        res.setData(bank);
        return res;
    }

    // 9. Get Bank by City
    public ResponseStructure<List<Bank>> getBanksByCity(String city) {
        List<Bank> banks = bankRepository.findByAddress_City(city);
        if (banks.isEmpty()) {
            throw new RecordNotFound("No banks registered in city: " + city);
        }

        ResponseStructure<List<Bank>> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Banks in " + city + " retrieved successfully.");
        res.setData(banks);
        return res;
    }

    // 10. Get Bank by Contact Number
    public ResponseStructure<Bank> getBankByContactNumber(String contactNumber) {
        Bank bank = bankRepository.findByContactNumber(contactNumber)
                .orElseThrow(() -> new RecordNotFound("Bank not found with contact number: " + contactNumber));

        ResponseStructure<Bank> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMessage("Bank details found for contact number: " + contactNumber);
        res.setData(bank);
        return res;
    }

}
