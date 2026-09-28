package Spring.Boot.mini.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import Spring.Boot.mini.Entity.Address;
import Spring.Boot.mini.Entity.Bank;

public interface BankRepository  extends JpaRepository<Bank,Integer>{
boolean existsBycontactNumber(Long contactNumber);
boolean existsByifsc(String ifsc);
boolean existsByAddressPincode(String pincode);
Optional<Bank> findByIfsc(String ifsc);
Optional<Bank> findByAddress(Address address);
List<Bank> findByAddress_City(String city);
Optional<Bank> findByContactNumber(String contactNumber);
Page<Bank> findAll(Pageable pageable);
}
