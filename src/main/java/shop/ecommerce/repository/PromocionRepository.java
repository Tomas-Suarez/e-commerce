package shop.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.ecommerce.model.PromocionEntity;

@Repository
public interface PromocionRepository extends JpaRepository<PromocionEntity, Long> {
}
