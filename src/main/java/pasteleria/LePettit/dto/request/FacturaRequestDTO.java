package pasteleria.LePettit.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FacturaRequestDTO {

    @NotBlank
    private String numero;

    @NotNull
    private LocalDate fecha_emision;

    @NotBlank
    private String cuit_emisor;

    @NotBlank
    private String razon_social;

    @NotBlank
    @Size(max = 80)
    private String domicilio_emisor;

    @Min(0)
    private BigDecimal total;

    @NotNull
    private Long CondicionIvaId;

    @NotNull
    private Long EstadoFacturaId;

    @NotNull
    private Long TipoFacturaId;

}
