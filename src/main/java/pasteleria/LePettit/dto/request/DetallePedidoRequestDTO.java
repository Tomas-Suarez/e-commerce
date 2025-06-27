package pasteleria.LePettit.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DetallePedidoRequestDTO(
        @Min(1)
        int cantidad,

        @NotNull
        BigDecimal subtotal,

        @NotNull
        Long idProducto,

        @NotNull
        Long idPedido
) {

}
