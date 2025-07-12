package shop.ecommerce.dto.response;

public record FinalizarCarritoResponseDTO(
        CarritoResponseDTO carritoFinalizado,
        CarritoResponseDTO nuevoCarrito,
        PedidoResponseDTO pedidoGenerado
) {
}
