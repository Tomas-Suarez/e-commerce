package shop.ecommerce.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoRequestDTO(
        @NotNull
        LocalDateTime fecha,

        @NotNull
        BigDecimal costoEnvio,

        @NotNull
        BigDecimal total,

        @NotNull
        Long idCliente,

        @NotNull
        DireccionRequestDTO direccion,

        @NotNull
        Long idEstadoPedido,

        @NotNull
        Long idCarrito
) {
}
