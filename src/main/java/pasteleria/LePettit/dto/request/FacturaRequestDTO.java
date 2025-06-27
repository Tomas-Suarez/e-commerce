package pasteleria.LePettit.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FacturaRequestDTO(
        @NotBlank
        String numero,

        @NotNull
        LocalDate fechaEmision,

        @NotBlank
        String cuitEmisor,

        @NotBlank
        String razonSocial,

        @NotBlank
        @Size(max = 80)
        String domicilioEmisor,

        @Min(0)
        BigDecimal total,

        @NotNull
        Long tipoFacturaId,

        @NotNull
        Long condicionIvaId,

        @NotNull
        Long estadoFacturaId,

        @NotNull
        Long pedidoId
) {
}
