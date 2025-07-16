package shop.ecommerce.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DireccionRequestDTO(
        @Min(1000)
        @Max(9999)
        int codigo_postal,

        @NotBlank
        @Size(max = 120)
        String calle,

        @Min(1)
        int numero,

        Integer piso,

        @NotBlank
        @Size(max = 126)
        String referencia
) {
}
