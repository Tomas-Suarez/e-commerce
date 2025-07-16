package shop.ecommerce.dto.response;

public record DireccionResponseDTO(
        int codigoPostal,
        String calle,
        int numero,
        Integer piso,
        String referencia
) {

}
