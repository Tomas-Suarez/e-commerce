package pasteleria.LePettit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pasteleria.LePettit.model.ProductoEntity;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {
    List<ProductoEntity> findByCategoriaIdAndActivoTrue(Long categoriaId);

}
