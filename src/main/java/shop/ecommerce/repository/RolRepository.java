package shop.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.ecommerce.model.RolEntity;

@Repository
public interface RolRepository extends JpaRepository<RolEntity, Long> {
}
