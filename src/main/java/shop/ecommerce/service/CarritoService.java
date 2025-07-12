package shop.ecommerce.service;

import shop.ecommerce.dto.request.CarritoRequestDTO;
import shop.ecommerce.dto.response.CarritoResponseDTO;
import shop.ecommerce.dto.response.FinalizarCarritoResponseDTO;

public interface CarritoService {

    CarritoResponseDTO crearCarrito(CarritoRequestDTO dto);

    CarritoResponseDTO obtenerCarritoPorId(Long carritoId);

    CarritoResponseDTO obtenerCarritoActivoPorCliente(Long clienteId);

    void vaciarCarrito(Long carritoId);

    FinalizarCarritoResponseDTO finalizarCarrito(Long carritoId);
}
