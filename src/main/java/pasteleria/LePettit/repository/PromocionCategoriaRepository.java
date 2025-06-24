package pasteleria.LePettit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pasteleria.LePettit.model.PromocionCategoriaEntity;

@Repository
public interface PromocionCategoriaRepository extends JpaRepository<PromocionCategoriaEntity, Long> {
}
