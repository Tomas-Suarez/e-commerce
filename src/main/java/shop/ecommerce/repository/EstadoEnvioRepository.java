package shop.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.ecommerce.model.EstadoEnvioEntity;

@Repository
public interface EstadoEnvioRepository extends JpaRepository<EstadoEnvioEntity, Long> {
}
