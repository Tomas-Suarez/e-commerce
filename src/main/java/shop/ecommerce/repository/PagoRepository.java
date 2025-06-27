package shop.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.ecommerce.model.PagoEntity;

@Repository
public interface PagoRepository extends JpaRepository<PagoEntity, Long> {
}
