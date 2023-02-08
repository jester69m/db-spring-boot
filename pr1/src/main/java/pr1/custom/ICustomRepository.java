package pr1.custom;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICustomRepository extends JpaRepository<Custom, Long> {

    List<Custom> findByPrice(int price);
    List<Custom> findByVolume(int volume);
    List<Custom> findByWeight(int weight);
    List<Custom> findByCustomStatus(customStatus customStatus);

}
