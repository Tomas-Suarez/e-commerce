package shop.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.ecommerce.model.TipoFacturaEntity;

@Repository
public interface TipoFacturaRepository extends JpaRepository<TipoFacturaEntity, Long> {
}
