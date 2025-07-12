package shop.ecommerce.service;

import shop.ecommerce.dto.request.PedidoRequestDTO;
import shop.ecommerce.dto.response.PedidoResponseDTO;

import java.util.List;

public interface PedidoService {

    PedidoResponseDTO crearPedido(PedidoRequestDTO dto);

    PedidoResponseDTO crearPedidoDesdeCarrito(Long idCliente);

    PedidoResponseDTO obtenerPedidoPorId(Long idPedido);

    List<PedidoResponseDTO> obtenerPedidosPendientesPorCliente(Long idCliente);

    List<PedidoResponseDTO> obtenerPedidosPorCliente(Long idCliente);

    PedidoResponseDTO actualizarEstadoPedido(Long idPedido, Long idEstado);
}
