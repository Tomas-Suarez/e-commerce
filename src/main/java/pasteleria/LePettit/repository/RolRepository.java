package pasteleria.LePettit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pasteleria.LePettit.model.RolEntity;

@Repository
public interface RolRepository extends JpaRepository<RolEntity, Long> {
}
