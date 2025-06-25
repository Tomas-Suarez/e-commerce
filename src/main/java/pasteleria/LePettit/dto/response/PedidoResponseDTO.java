package pasteleria.LePettit.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PedidoResponseDTO {

    private LocalDateTime fecha;
    private BigDecimal total;

    private ClienteResponseDTO cliente;
    private String estadoPedido;
}
