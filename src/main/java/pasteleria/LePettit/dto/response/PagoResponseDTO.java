package pasteleria.LePettit.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PagoResponseDTO {

    private BigDecimal monto;
    private boolean activo;
    private LocalDateTime fecha_pago;
    private String metodoPago;

}
