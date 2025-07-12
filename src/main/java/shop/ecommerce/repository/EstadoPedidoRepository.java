package shop.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.ecommerce.model.EstadoPedidoEntity;

import java.util.Optional;

@Repository
public interface EstadoPedidoRepository extends JpaRepository<EstadoPedidoEntity, Long> {

    Optional<EstadoPedidoEntity> findByNombre(String nombre);
}
