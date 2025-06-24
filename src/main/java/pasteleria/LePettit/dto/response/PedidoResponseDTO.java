package pasteleria.LePettit.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PedidoResponseDTO {

    private LocalDateTime fecha;
    private BigDecimal total;

    private PedidoResponseDTO pedido;
    private String estadoPedido;
}
