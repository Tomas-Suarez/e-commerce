package shop.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.ecommerce.model.EstadoFacturaEntity;

@Repository
public interface EstadoFacturaRepository extends JpaRepository<EstadoFacturaEntity, Long> {
}
