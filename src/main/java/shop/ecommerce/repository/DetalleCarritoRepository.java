package shop.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.ecommerce.model.DetalleCarritoEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface DetalleCarritoRepository extends JpaRepository<DetalleCarritoEntity, Long> {

    Optional<DetalleCarritoEntity> findByCarritoIdAndProductoId(Long carritoId, Long productoId);
    List<DetalleCarritoEntity> findByCarritoId(Long carritoId);


}
