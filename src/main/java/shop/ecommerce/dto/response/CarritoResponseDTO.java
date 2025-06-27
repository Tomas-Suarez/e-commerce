package shop.ecommerce.dto.response;

import java.time.LocalDate;
import java.util.List;

public record CarritoResponseDTO(

        Long id,
        LocalDate fecha,
        boolean activo,

        ClienteResponseDTO cliente,

        List<DetalleCarritoResponseDTO> productos
) {
}
