package pasteleria.LePettit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pasteleria.LePettit.model.TipoDescuentoEntity;

@Repository
public interface TipoDescuentoRepository extends JpaRepository<TipoDescuentoEntity, Long> {
}
