package pasteleria.LePettit.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EnvioRequestDTO {
    @NotBlank
    @Size(max = 80)
    private String direccion;

    @NotNull
    private LocalDateTime fecha_envio;

    private LocalDateTime fecha_entrega;

    @NotNull
    private Long PedidoId;

    @NotNull
    private Long EstadoPedidoId;
}
