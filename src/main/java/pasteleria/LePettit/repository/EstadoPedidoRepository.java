package pasteleria.LePettit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pasteleria.LePettit.model.EstadoPedidoEntity;

@Repository
public interface EstadoPedidoRepository extends JpaRepository<EstadoPedidoEntity, Long> {
}
