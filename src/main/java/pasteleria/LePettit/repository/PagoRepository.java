package pasteleria.LePettit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pasteleria.LePettit.model.PagoEntity;

@Repository
public interface PagoRepository extends JpaRepository<PagoEntity, Long> {
}
