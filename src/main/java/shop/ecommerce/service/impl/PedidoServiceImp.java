package shop.ecommerce.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.ecommerce.dto.request.PedidoRequestDTO;
import shop.ecommerce.dto.response.PedidoResponseDTO;
import shop.ecommerce.exception.ResourceNotFoundException;
import shop.ecommerce.mapper.PedidoMapper;
import shop.ecommerce.model.*;
import shop.ecommerce.repository.*;
import shop.ecommerce.service.PedidoService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static shop.ecommerce.constants.CarritoConstants.CARRITO_NO_ENCONTRADO_POR_CLIENTE;
import static shop.ecommerce.constants.CarritoConstants.CARRITO_VACIO;
import static shop.ecommerce.constants.ClienteConstants.CLIENTE_NO_ENCONTRADO_POR_ID;
import static shop.ecommerce.constants.EstadoPedidoConstants.*;
import static shop.ecommerce.constants.PedidoConstants.PEDIDO_NO_ENCONTRADO_POR_ID;

@Service
@RequiredArgsConstructor
public class PedidoServiceImp implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;
    private final ClienteRepository clienteRepository;
    private final EstadoPedidoRepository estadoPedidoRepository;
    private final CarritoRepository carritoRepository;
    private final DetalleCarritoRepository detalleCarritoRepository;

    private PedidoEntity construirPedidoDesdeDTO(PedidoRequestDTO dto,
                                                 ClienteEntity cliente,
                                                 EstadoPedidoEntity estadoPedido) {
        PedidoEntity pedido = pedidoMapper.toEntity(dto);
        pedido.setCliente(cliente);
        pedido.setEstadoPedido(estadoPedido);
        pedido.setFecha(LocalDateTime.now());
        pedido.setTotal(BigDecimal.ZERO);
        return pedido;
    }

    private EstadoPedidoEntity obtenerEstadoPedidoPorIdOExcepcion(Long idEstadoPedido) {
        return estadoPedidoRepository.findById(idEstadoPedido)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ESTADO_PEDIDO_NO_ENCONTRADO_POR_ID + idEstadoPedido));
    }

    private ClienteEntity obtenerClientePorIdOExcepcion(Long idCliente) {
        return clienteRepository.findById(idCliente)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CLIENTE_NO_ENCONTRADO_POR_ID + idCliente));
    }

    private CarritoEntity obtenerCarritoActivoPorClienteIdOExcepcion(Long idCliente) {
        return carritoRepository.findByClienteIdAndActivoTrue(idCliente)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CARRITO_NO_ENCONTRADO_POR_CLIENTE + idCliente));
    }

    private PedidoEntity obtenerPedidoPorIdOExcepcion(Long idPedido){
        return pedidoRepository.findById(idPedido)
                .orElseThrow(() ->
                        new ResourceNotFoundException(PEDIDO_NO_ENCONTRADO_POR_ID + idPedido));
    }



    @Override
    @Transactional
    public PedidoResponseDTO crearPedido(PedidoRequestDTO dto) {

        ClienteEntity cliente = obtenerClientePorIdOExcepcion(dto.idCliente());

        EstadoPedidoEntity estadoPedido = obtenerEstadoPedidoPorIdOExcepcion(dto.idEstadoPedido());

        PedidoEntity pedido = construirPedidoDesdeDTO(dto, cliente, estadoPedido);

        return pedidoMapper.toDto(pedidoRepository.save(pedido));
    }

    @Override
    public PedidoResponseDTO crearPedidoDesdeCarrito(Long idCliente) {

        ClienteEntity cliente = obtenerClientePorIdOExcepcion(idCliente);

        CarritoEntity carrito = obtenerCarritoActivoPorClienteIdOExcepcion(idCliente);

        List<DetalleCarritoEntity> productosDelCarrito = detalleCarritoRepository.findByCarritoId(carrito.getId());

        if (productosDelCarrito.isEmpty()) {
            throw new IllegalStateException(CARRITO_VACIO);
        }

        EstadoPedidoEntity estadoInicial = estadoPedidoRepository.findByNombre(ESTADO_PEDIDO_PENDIENTE)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ESTADO_PEDIDO_NO_ENCONTRADO_POR_NOMBRE + ESTADO_PEDIDO_PENDIENTE));

        PedidoEntity pedido = new PedidoEntity();
        pedido.setCliente(cliente);
        pedido.setEstadoPedido(estadoInicial);
        pedido.setFecha(LocalDateTime.now());

        List<DetallePedidoEntity> detallePedido = productosDelCarrito.stream().map(detCarrito -> {
            DetallePedidoEntity detPedido = new DetallePedidoEntity();
            detPedido.setPedido(pedido);
            detPedido.setProducto(detCarrito.getProducto());
            detPedido.setCantidad(detCarrito.getCantidad());
            detPedido.setPrecioUnitario(detCarrito.getProducto().getPrecio());
            return detPedido;
        }).toList();

        BigDecimal total = detallePedido.stream()
                .map(DetallePedidoEntity::calcularSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        pedido.setTotal(total);
        pedido.setDetalles(detallePedido);
        pedidoRepository.save(pedido);

        return pedidoMapper.toDto(pedido);
    }

    @Override
    public PedidoResponseDTO obtenerPedidoPorId(Long idPedido) {

        PedidoEntity pedido = obtenerPedidoPorIdOExcepcion(idPedido);

        return pedidoMapper.toDto(pedido);
    }

    @Override
    public List<PedidoResponseDTO> obtenerPedidosPendientesPorCliente(Long idCliente) {

        obtenerClientePorIdOExcepcion(idCliente);

        List<PedidoEntity> pedidosPendientes = pedidoRepository.findByClienteIdAndEstadoPedidoNombre(idCliente, ESTADO_PEDIDO_PENDIENTE);

        return pedidosPendientes.stream()
                .map(pedidoMapper::toDto)
                .toList();
    }

    @Override
    public List<PedidoResponseDTO> obtenerPedidosPorCliente(Long idCliente) {

        obtenerClientePorIdOExcepcion(idCliente);

        List<PedidoEntity> pedidos = pedidoRepository.findByClienteId(idCliente);

        return pedidos.stream()
                .map(pedidoMapper::toDto)
                .toList();
    }

    @Override
    public PedidoResponseDTO actualizarEstadoPedido(Long idPedido, Long idEstado) {

        PedidoEntity pedido = obtenerPedidoPorIdOExcepcion(idPedido);

        EstadoPedidoEntity estado = obtenerEstadoPedidoPorIdOExcepcion(idEstado);

        pedido.setEstadoPedido(estado);
        pedidoRepository.save(pedido);

        return pedidoMapper.toDto(pedido);
    }
}