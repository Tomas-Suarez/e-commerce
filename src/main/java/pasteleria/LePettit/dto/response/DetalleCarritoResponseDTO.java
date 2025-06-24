package pasteleria.LePettit.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DetalleCarritoResponseDTO {

    private int cantidad;
    private BigDecimal subtotal;
    private ProductoResponseDTO producto;
}
