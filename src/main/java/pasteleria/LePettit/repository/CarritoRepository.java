package pasteleria.LePettit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pasteleria.LePettit.model.CarritoEntity;

@Repository
public interface CarritoRepository extends JpaRepository<CarritoEntity, Long>{
}
