package pasteleria.LePettit.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DetallePedidoRequestDTO {

    @Min(1)
    private int cantidad;

    @NotNull
    private BigDecimal subtotal;

    @NotNull
    private Long idProducto;

    @NotNull
    private Long idPedido;

}
