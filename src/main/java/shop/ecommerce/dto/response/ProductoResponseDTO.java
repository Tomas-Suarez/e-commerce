package shop.ecommerce.dto.response;

import java.math.BigDecimal;

public record ProductoResponseDTO(
        Long id,
        String nombre,
        BigDecimal precio,
        int stock,
        String descripcion,
        boolean activo,
        String categoriaNombre
) {
}
