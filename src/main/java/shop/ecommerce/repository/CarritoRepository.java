package shop.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.ecommerce.model.CarritoEntity;

import java.util.Optional;

@Repository
public interface CarritoRepository extends JpaRepository<CarritoEntity, Long>{

    Optional<CarritoEntity> findByClienteIdAndActivoTrue(Long clienteId);

}
