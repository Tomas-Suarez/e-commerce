package shop.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "factura")
public class FacturaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @OneToOne
    @JoinColumn(name = "id_pedido", nullable = false, unique = true)
    private PedidoEntity pedido;

    @ManyToOne
    @JoinColumn(name = "id_tipo_factura", nullable = false)
    private TipoFacturaEntity tipoFactura;

    @ManyToOne
    @JoinColumn(name = "id_condicion_iva", nullable = false)
    private CondicionIvaEntity condicionIva;

    @ManyToOne
    @JoinColumn(name = "id_estado_factura", nullable = false)
    private EstadoFacturaEntity estadoFactura;

}
