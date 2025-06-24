package pasteleria.LePettit.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PromocionRequestDTO {

    @NotBlank
    @Size(max = 30)
    private String nombre;

    @NotBlank
    @Size(max = 128)
    private String descripcion;

    @NotNull
    private float valor_descuento;

    private String codigo_promocional;

    @NotNull
    private BigDecimal minimo_compra;

    @NotNull
    private LocalDateTime fecha_inicio;

    private LocalDateTime fecha_fin;

    @NotNull
    private boolean activa;

    @NotNull
    private Long TipoDescuentoId;
}
