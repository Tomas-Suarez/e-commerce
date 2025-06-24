package pasteleria.LePettit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pasteleria.LePettit.model.MetodoPagoEntity;

public interface MetodoPagoRepository extends JpaRepository<Long, MetodoPagoEntity> {
}
