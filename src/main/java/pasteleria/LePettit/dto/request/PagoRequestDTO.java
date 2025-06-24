package pasteleria.LePettit.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PagoRequestDTO {

    @NotNull
    private BigDecimal monto;

    private boolean activo;

    @NotNull
    private LocalDateTime fecha_pago;

    @NotNull
    private Long pedidoId;

    @NotNull
    private Long metodoPagoId;
}
