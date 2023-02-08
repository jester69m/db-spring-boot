package pr1.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {

}
