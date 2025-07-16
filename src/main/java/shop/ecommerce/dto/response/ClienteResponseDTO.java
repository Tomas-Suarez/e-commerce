package shop.ecommerce.dto.response;

public record ClienteResponseDTO(
        String nombre,
        String apellido,
        String dni,
        String telefono
) {
}
