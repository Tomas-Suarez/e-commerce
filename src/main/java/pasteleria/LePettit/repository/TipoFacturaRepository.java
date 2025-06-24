package pasteleria.LePettit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pasteleria.LePettit.model.TipoFacturaEntity;

@Repository
public interface TipoFacturaRepository extends JpaRepository<TipoFacturaEntity, Long> {
}
