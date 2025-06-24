package pasteleria.LePettit.dto.response;

import lombok.Data;

@Data
public class ClienteResponseDTO {
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String direccion;
}
