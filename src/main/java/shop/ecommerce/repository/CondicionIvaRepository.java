package shop.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.ecommerce.model.CondicionIvaEntity;

@Repository
public interface CondicionIvaRepository extends JpaRepository<CondicionIvaEntity, Long> {
}
