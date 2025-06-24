package pasteleria.LePettit.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PromocionResponseDTO {

    private String nombre;
    private String descripcion;
    private float valor_descuento;
    private String codigo_promocional;
    private BigDecimal minimo_compra;
    private LocalDateTime fecha_inicio;
    private LocalDateTime fecha_fin;
    private boolean activa;

    private String tipoDescuento;
}
