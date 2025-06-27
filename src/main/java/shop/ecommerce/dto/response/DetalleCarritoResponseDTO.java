package shop.ecommerce.dto.response;

import java.math.BigDecimal;

public record DetalleCarritoResponseDTO(
        int cantidad,
        BigDecimal subtotal,
        ProductoResponseDTO producto
) {
}
