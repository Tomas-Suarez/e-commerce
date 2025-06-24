package pasteleria.LePettit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pasteleria.LePettit.model.EstadoFacturaEntity;

@Repository
public interface EstadoFacturaRepository extends JpaRepository<EstadoFacturaEntity, Long> {
}
