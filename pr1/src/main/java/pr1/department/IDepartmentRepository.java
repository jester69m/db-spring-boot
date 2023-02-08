package pr1.department;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface IDepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByName(String name);
}
