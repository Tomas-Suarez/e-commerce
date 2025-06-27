package shop.ecommerce.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FacturaResponseDTO(
        String numero,
        LocalDate fechaEmision,
        String cuitEmisor,
        String razonSocial,
        String domicilioEmisor,
        BigDecimal total,

        String tipoFactura,
        String condicionIva,
        String estadoFactura
) {
}
