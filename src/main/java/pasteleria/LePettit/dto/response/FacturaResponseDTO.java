package pasteleria.LePettit.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FacturaResponseDTO {

    private String numero;
    private LocalDate fecha_emision;
    private String cuit_emisor;
    private String razon_social;
    private String domicilio_emisor;
    private BigDecimal total;

    private String tipoFactura;
    private String condicionIva;
    private String estadoFactura;

}
