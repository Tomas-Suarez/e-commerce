package shop.ecommerce.service;

import shop.ecommerce.dto.request.DetalleCarritoRequestDTO;
import shop.ecommerce.dto.response.DetalleCarritoResponseDTO;

import java.util.List;

public interface DetalleCarritoService {

    DetalleCarritoResponseDTO agregarProductoAlCarrito(DetalleCarritoRequestDTO dto);

    DetalleCarritoResponseDTO actualizarProductoDelCarrito(Long detalleId, DetalleCarritoRequestDTO dto);

    void eliminarProductoDelCarrito(Long detalleId);

    List<DetalleCarritoResponseDTO> obtenerProductosDelCarrito(Long carritoId);

}
