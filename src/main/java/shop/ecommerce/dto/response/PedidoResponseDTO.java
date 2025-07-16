package shop.ecommerce.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoResponseDTO(

        LocalDateTime fecha,
        BigDecimal costoEnvio,
        BigDecimal total,

        ClienteResponseDTO cliente,
        DireccionResponseDTO direccion,
        String estadoPedido

) {
}
