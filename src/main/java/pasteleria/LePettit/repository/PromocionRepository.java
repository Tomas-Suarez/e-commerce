package pasteleria.LePettit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pasteleria.LePettit.model.PromocionEntity;

@Repository
public interface PromocionRepository extends JpaRepository<PromocionEntity, Long> {
}
