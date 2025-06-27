package shop.ecommerce.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagoResponseDTO(
        BigDecimal monto,
        boolean activo,
        LocalDateTime fecha_pago,
        String metodoPago
) {
}
