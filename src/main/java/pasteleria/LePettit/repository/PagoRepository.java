package pasteleria.LePettit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pasteleria.LePettit.model.PagoEntity;

public interface PagoRepository extends JpaRepository<Long, PagoEntity> {
}
