package pasteleria.LePettit.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteRequestDTO(
        @NotBlank
        @Size(max = 30)
        String nombre,

        @NotBlank
        @Size(max = 30)
        String apellido,

        @NotBlank
        @Pattern(regexp = "^\\d{7,8}$", message = "El DNI debe tener 7 u 8 dígitos numéricos sin puntos ni letras")
        String dni,

        @NotBlank
        @Size(max = 20)
        @Pattern(
                regexp = "^\\+?\\d{1,4}?[\\s.-]?\\(?\\d{1,4}\\)?[\\s.-]?\\d{3,4}[\\s.-]?\\d{3,4}$",
                message = "El teléfono debe tener un formato válido"
        )
        String telefono,

        @NotBlank
        @Size(max = 80)
        String direccion
) {
}
