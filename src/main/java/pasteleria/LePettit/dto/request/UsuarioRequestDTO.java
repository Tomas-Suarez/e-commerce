package pasteleria.LePettit.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsuarioRequestDTO {

    @NotBlank
    @Size(max = 30)
    private String username;

    @NotBlank
    @Size(max = 30)
    private String password;

    @NotBlank
    @Email
    @Size(max = 40)
    private String email;

    private Long rolId;
    private ClienteRequestDTO cliente;
}
