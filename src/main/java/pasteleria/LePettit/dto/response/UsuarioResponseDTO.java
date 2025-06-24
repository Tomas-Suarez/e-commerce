package pasteleria.LePettit.dto.response;

import lombok.Data;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String username;
    private String email;

    private String rolNombre;
    private ClienteResponseDTO cliente;
}
