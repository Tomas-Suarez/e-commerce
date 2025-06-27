package pasteleria.LePettit.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoRequestDTO(
        @NotNull
        LocalDateTime fecha,

        @NotNull
        BigDecimal total,

        @NotNull
        Long idCliente,

        @NotNull
        Long idEstadoPedido
) {
}
