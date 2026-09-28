package Spring.Boot.mini.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Spring.Boot.mini.DTO.ResponseStructure;
import Spring.Boot.mini.Entity.Address;
import Spring.Boot.mini.Repository.AddressRepository;
import Spring.Boot.mini.Service.AddressService;

@RestController
@RequestMapping("/address")
public class AddressController {

	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private AddressService addressService;
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Address>> getAddressById(@PathVariable int id){
		return new ResponseEntity<>(addressService.getAddressById(id),HttpStatus.OK);
	}
	
	@PatchMapping("/update/{id}")
	public ResponseEntity<ResponseStructure<Address>> updateAddressById(@PathVariable int id,@RequestBody Map<String, Object> map){
		return new ResponseEntity<>(addressService.updateAddressById(id,map),HttpStatus.OK);
	}
	
	@GetMapping("/bank/{id}")
	public ResponseEntity<ResponseStructure<List<Address>>> getAddressByBankId(@PathVariable int id){
		return new ResponseEntity<>(addressService.getAddressByBankId(id),HttpStatus.OK);
	}
	
	@GetMapping("/{city}/{street}")
	public ResponseEntity<ResponseStructure<Address>> getAddressByCityAndStreet(@PathVariable String city,@PathVariable String street){
		return new ResponseEntity<>(addressService.getAddressByCityAndStreet(city,street),HttpStatus.OK);
	}
	
	@GetMapping("city/{city}")
	public ResponseEntity<ResponseStructure<List<Address>>> getAddressByCity(@PathVariable String city){
		return new ResponseEntity<>(addressService.getAddressByCity(city),HttpStatus.OK);
	}

	
}
