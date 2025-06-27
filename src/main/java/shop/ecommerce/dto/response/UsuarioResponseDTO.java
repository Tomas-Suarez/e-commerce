package shop.ecommerce.dto.response;

public record UsuarioResponseDTO(
        Long id,
        String username,
        String email,

        String rolNombre,
        ClienteResponseDTO cliente
) {
}
