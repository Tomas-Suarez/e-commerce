package pasteleria.LePettit.dto.response;

public record ClienteResponseDTO(
        String nombre,
        String apellido,
        String dni,
        String telefono,
        String direccion
) {
}
