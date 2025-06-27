package pasteleria.LePettit.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PromocionRequestDTO(
        @NotBlank
        @Size(max = 30)
        String nombre,

        @NotBlank
        @Size(max = 128)
        String descripcion,

        @NotNull
        float valor_descuento,

        String codigo_promocional,

        @NotNull
        BigDecimal minimo_compra,

        @NotNull
        LocalDateTime fecha_inicio,

        LocalDateTime fecha_fin,

        @NotNull
        boolean activa,

        @NotNull
        Long TipoDescuentoId
) {
}
