package pasteleria.LePettit.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record EnvioRequestDTO(
        @NotBlank
        @Size(max = 80)
        String direccion,

        @NotNull
        LocalDateTime fecha_envio,

        LocalDateTime fecha_entrega,

        @NotNull
        Long PedidoId,

        @NotNull
        Long EstadoEnvioId
) {
}
