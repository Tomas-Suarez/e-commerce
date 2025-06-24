package pasteleria.LePettit.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClienteRequestDTO {
    @NotBlank
    @Size(max = 30)
    private String nombre;

    @NotBlank
    @Size(max = 30)
    private String apellido;

    @NotBlank
    @Pattern(regexp = "^\\d{7,8}$", message = "El DNI debe tener 7 u 8 dígitos numéricos sin puntos ni letras")
    private String dni;

    @NotBlank
    @Size(max = 20)
    @Pattern(
            regexp = "^\\+?\\d{1,4}?[\\s.-]?\\(?\\d{1,4}\\)?[\\s.-]?\\d{3,4}[\\s.-]?\\d{3,4}$",
            message = "El teléfono debe tener un formato válido"
    )
    private String telefono;

    @NotBlank
    @Size(max = 80)
    private String direccion;
}
