package shop.ecommerce.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PromocionResponseDTO(

        String nombre,
        String descripcion,
        float valor_descuento,
        String codigo_promocional,
        BigDecimal minimo_compra,
        LocalDateTime fecha_inicio,
        LocalDateTime fecha_fin,
        boolean activa,

        String tipoDescuento
) {
}
