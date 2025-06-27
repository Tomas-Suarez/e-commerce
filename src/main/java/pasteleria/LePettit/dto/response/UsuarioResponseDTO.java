package pasteleria.LePettit.dto.response;

public record UsuarioResponseDTO(
        Long id,
        String username,
        String email,

        String rolNombre,
        ClienteResponseDTO cliente
) {
}
