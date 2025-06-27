package pasteleria.LePettit.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductoRequestDTO(
        @NotBlank
        @Size(max = 50)
        String nombre,

        @NotNull
        BigDecimal precio,

        @NotNull
        int stock,

        @NotBlank
        @Size(max = 128)
        String descripcion,

        boolean activo,

        Long categoriaId
) {
}