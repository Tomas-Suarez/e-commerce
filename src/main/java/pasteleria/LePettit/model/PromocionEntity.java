package pasteleria.LePettit.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "promocion")
public class PromocionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 30)
    private String nombre;

    @NotBlank
    @Size(max = 128)
    private String descripcion;

    @NotNull
    private float valor_descuento;

    private String codigo_promocional;

    @NotNull
    private BigDecimal minimo_compra;

    @NotNull
    private LocalDateTime fecha_inicio;

    private LocalDateTime fecha_fin;

    @NotNull
    private boolean activa;

    @ManyToOne
    @JoinColumn(name = "id_tipo_descuento", nullable = false)
    private TipoDescuentoEntity tipoDescuento;

}
