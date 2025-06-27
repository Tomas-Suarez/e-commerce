package pasteleria.LePettit.dto.response;

import java.math.BigDecimal;

public record DetallePedidoResponseDTO(
        int cantidad,
        BigDecimal subtotal,
        ProductoResponseDTO productos
) {
}
