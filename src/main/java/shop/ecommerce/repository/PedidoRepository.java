package shop.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.ecommerce.model.PedidoEntity;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {

    List<PedidoEntity> findByClienteIdAndEstadoPedidoNombre(Long clienteId, String estadoNombre);
    List<PedidoEntity> findByClienteId(Long clienteId);

}
