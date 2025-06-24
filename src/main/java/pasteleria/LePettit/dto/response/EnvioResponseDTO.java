package pasteleria.LePettit.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EnvioResponseDTO {

    private String direccion;
    private LocalDateTime fecha_envio;
    private LocalDateTime fecha_entrega;
    private String estadoEnvio;
}
