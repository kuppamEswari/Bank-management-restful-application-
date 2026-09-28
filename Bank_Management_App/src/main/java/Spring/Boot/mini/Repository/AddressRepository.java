package Spring.Boot.mini.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Spring.Boot.mini.Entity.Address;

public interface AddressRepository extends JpaRepository<Address,Integer> {
	List<Address> findByBankBankId(int bankId);
	Optional<Address> findByCityAndStreet(String city,String street);
	List<Address> findByCity(String City);
}
