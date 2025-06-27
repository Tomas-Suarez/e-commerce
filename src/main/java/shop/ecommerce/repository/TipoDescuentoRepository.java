package shop.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.ecommerce.model.TipoDescuentoEntity;

@Repository
public interface TipoDescuentoRepository extends JpaRepository<TipoDescuentoEntity, Long> {
}
