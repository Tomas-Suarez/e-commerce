package pasteleria.LePettit.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DetallePedidoResponseDTO {

    private int cantidad;
    private BigDecimal subtotal;
    private ProductoResponseDTO productos;
}
