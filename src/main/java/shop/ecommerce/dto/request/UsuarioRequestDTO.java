package shop.ecommerce.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(
        @NotBlank
        @Size(max = 30)
        String username,

        @NotBlank
        @Size(max = 30)
        String password,

        @NotBlank
        @Email
        @Size(max = 40)
        String email,

        Long rolId,
        ClienteRequestDTO cliente
) {
}
