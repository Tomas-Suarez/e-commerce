package shop.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.ecommerce.model.PromocionProductoEntity;

@Repository
public interface PromocionProductoRepository extends JpaRepository<PromocionProductoEntity, Long> {
}
