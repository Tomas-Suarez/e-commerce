package shop.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.ecommerce.model.PromocionCategoriaEntity;

@Repository
public interface PromocionCategoriaRepository extends JpaRepository<PromocionCategoriaEntity, Long> {
}
