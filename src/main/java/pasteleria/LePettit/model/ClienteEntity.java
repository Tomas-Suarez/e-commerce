package pasteleria.LePettit.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cliente")
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 30)
    private String nombre;

    @NotBlank
    @Size(max = 30)
    private String apellido;

    @NotBlank
    @Pattern(regexp = "^\\d{7,8}$",
            message = "El DNI debe tener 7 u 8 dígitos numéricos sin puntos ni letras")
    private String dni;

    @NotBlank
    @Size(max = 20)
    @Pattern(regexp = "^\\+?\\d{1,4}?[\\s.-]?\\(?\\d{1,4}\\)?[\\s.-]?\\d{3,4}[\\s.-]?\\d{3,4}$",
            message = "El teléfono debe tener un formato válido")
    private String telefono;

    @NotBlank
    @Size(max = 80)
    private String direccion;

    @OneToMany(mappedBy = "cliente")
    private List<PedidoEntity> pedidos;
}
