package shop.ecommerce.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoResponseDTO(

        LocalDateTime fecha,
        BigDecimal total,

        ClienteResponseDTO cliente,
        String estadoPedido
) {
}
