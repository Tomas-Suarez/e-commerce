package shop.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "detalle_pedido")
public class DetallePedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1)
    private int cantidad;

    @NotNull
    private BigDecimal precioUnitario;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private ProductoEntity producto;

    @ManyToOne
    @JoinColumn(name = "id_pedido", nullable = false)
    private PedidoEntity pedido;

    public BigDecimal calcularSubTotal() {
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
}
