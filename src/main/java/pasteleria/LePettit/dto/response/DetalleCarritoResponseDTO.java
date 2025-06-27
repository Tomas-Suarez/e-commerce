package pasteleria.LePettit.dto.response;

import java.math.BigDecimal;

public record DetalleCarritoResponseDTO(
        int cantidad,
        BigDecimal subtotal,
        ProductoResponseDTO producto
) {
}
