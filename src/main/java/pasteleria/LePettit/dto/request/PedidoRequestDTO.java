package pasteleria.LePettit.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PedidoRequestDTO {

    @NotNull
    private LocalDateTime fecha;

    @NotNull
    private BigDecimal total;

    @NotNull
    private Long idCliente;

    @NotNull
    private Long idEstadoPedido;
}
