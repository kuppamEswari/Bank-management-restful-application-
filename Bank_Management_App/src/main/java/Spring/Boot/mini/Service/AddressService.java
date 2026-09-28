package Spring.Boot.mini.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import Spring.Boot.mini.DTO.ResponseStructure;
import Spring.Boot.mini.Entity.Address;
import Spring.Boot.mini.Exception.IdNotFoundException;
import Spring.Boot.mini.Exception.RecordNotFound;
import Spring.Boot.mini.Repository.AddressRepository;

@Service
public class AddressService {
	@Autowired
	private AddressRepository addressRepository;
	
	//getting details by address id
	public ResponseStructure<Address> getAddressById(@PathVariable int id) {
		Optional<Address> bank=addressRepository.findById(id);
		ResponseStructure<Address> res=new ResponseStructure<Address>();
		if(bank.isPresent()) {
			Address banks=bank.get();
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage(id+" address retrived successfully");
			res.setData(banks);
			return res;
		}
		else
			throw new IdNotFoundException(id+"id is not available");
	}

	//update the address
	public  ResponseStructure<Address> updateAddressById(@PathVariable Integer id,@RequestBody Map<String, Object> map) {
		 ResponseStructure<Address> res = new ResponseStructure<>();
		    Optional<Address> opt = addressRepository.findById(id);

		    if (opt.isPresent()) {
		    	Address bank = opt.get();

		        // Dynamically iterate through the JSON keys provided in the request
		        for (Map.Entry<String, Object> entry : map.entrySet()) {
		            String key = entry.getKey();
		            Object value = entry.getValue();

		            switch (key) {
		                case "street": // Changed from "name" to match book.setTitle()
		                    bank.setStreet((String) value);
		                    break;

		                case "city":
		                    bank.setCity((String) value);
		                    break;

		                case "state":
		                    bank.setState((String) value);
		                    break;

		                case "publishedYear":
		                    bank.setPincode((Integer) value);
		                    break;

		                default:
		                    res.setStatusCode(HttpStatus.BAD_REQUEST.value());
		                    res.setMessage("Invalid field: " + key);
		                    res.setData(null); // Fixed typo from setDate to setData
		                    return res; // Fixed return type
		            }
		        }

		        Address updatedAddress = addressRepository.save(bank);

		        res.setStatusCode(HttpStatus.OK.value());
		        res.setMessage("Book updated successfully");
		        res.setData(updatedAddress); // Fixed typo from setDate to setData
		        
		        return res;// Fixed return type
		    } 
		    
		    throw new IdNotFoundException("Book record with ID: " + id + " does not existing");
	}

	public ResponseStructure<List<Address>> getAddressByBankId(int id) {
		List<Address> bank=addressRepository.findByBankBankId(id);
		ResponseStructure<List<Address>> res=new ResponseStructure<>();
		if(bank.isEmpty())
			throw new RecordNotFound("no record available");
		else {
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("book retrived successfully");
			res.setData(bank);
			return res;
		}
	}

	public  ResponseStructure<Address> getAddressByCityAndStreet(String city, String street) {
		Optional<Address> opt = addressRepository.findByCityAndStreet(city, street);
		ResponseStructure<Address> res=new ResponseStructure<Address>();
		if(opt.isPresent()) {
			Address banks=opt.get();
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage(city+" and "+ street +" address retrived successfully");
			res.setData(banks);
			return res;
		}
		else
			throw new IdNotFoundException(city+" and "+ street+" is not available");
	}

	public ResponseStructure<List<Address>> getAddressByCity(String city) {
		List<Address> bank=addressRepository.findByCity(city);
		ResponseStructure<List<Address>> res=new ResponseStructure<>();
		if(bank.isEmpty())
			throw new RecordNotFound("no record available");
		else {
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("book retrived successfully");
			res.setData(bank);
			return res;
		}
	}
}
