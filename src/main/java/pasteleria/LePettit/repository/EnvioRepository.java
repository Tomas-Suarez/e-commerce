package pasteleria.LePettit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pasteleria.LePettit.model.EnvioEntity;

@Repository
public interface EnvioRepository extends JpaRepository<EnvioEntity, Long> {
}
