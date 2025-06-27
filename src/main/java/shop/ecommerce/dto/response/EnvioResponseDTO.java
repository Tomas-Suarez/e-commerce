package shop.ecommerce.dto.response;

import java.time.LocalDateTime;

public record EnvioResponseDTO(
        String direccion,
        LocalDateTime fecha_envio,
        LocalDateTime fecha_entrega,
        String estadoEnvio
) {
}
