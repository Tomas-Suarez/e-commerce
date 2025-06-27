package shop.ecommerce.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagoRequestDTO(
        @NotNull
        BigDecimal monto,

        boolean activo,

        @NotNull
        LocalDateTime fecha_pago,

        @NotNull
        Long pedidoId,

        @NotNull
        Long metodoPagoId
) {
}
