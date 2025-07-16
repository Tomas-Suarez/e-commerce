package shop.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "pedido")
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDateTime fecha;

    @NotNull
    private BigDecimal costoEnvio;

    @NotNull
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private ClienteEntity cliente;

    @ManyToOne
    @JoinColumn(name = "id_estado_pedido", nullable = false)
    private EstadoPedidoEntity estadoPedido;

    @ManyToOne
    @JoinColumn(name = "id_carrito", nullable = true)
    private CarritoEntity carrito;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_direccion", nullable = false)
    private DireccionEntity direccion;

    @Builder.Default
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedidoEntity> detalles = new ArrayList<>();

    public BigDecimal calcularTotal(){
        if(detalles == null || detalles.isEmpty()){
            return BigDecimal.ZERO;
        }

        return detalles.stream()
                .map(DetallePedidoEntity::calcularSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
