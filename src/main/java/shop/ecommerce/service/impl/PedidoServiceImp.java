package shop.ecommerce.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.ecommerce.dto.request.DireccionRequestDTO;
import shop.ecommerce.dto.request.PedidoRequestDTO;
import shop.ecommerce.dto.response.PedidoResponseDTO;
import shop.ecommerce.exception.ResourceNotFoundException;
import shop.ecommerce.mapper.DireccionMapper;
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
    private final DireccionMapper direccionMapper;
    private final EstadoPedidoRepository estadoPedidoRepository;
    private final DetalleCarritoRepository detalleCarritoRepository;
    private final ClienteRepository clienteRepository;
    private final CarritoRepository carritoRepository;

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

    private List<DetallePedidoEntity> construirDetallesPedido(
            PedidoEntity pedido,
            List<DetalleCarritoEntity> productosCarrito) {

        return productosCarrito.stream().map(detCarrito ->
                DetallePedidoEntity.builder()
                        .pedido(pedido)
                        .producto(detCarrito.getProducto())
                        .cantidad(detCarrito.getCantidad())
                        .precioUnitario(detCarrito.getProducto().getPrecio())
                        .build()
        ).toList();
    }


    @Override
    @Transactional
    public PedidoResponseDTO crearPedido(PedidoRequestDTO dto) {

        ClienteEntity cliente = clienteRepository.findById(dto.idCliente())
                .orElseThrow(() ->
                        new ResourceNotFoundException(CLIENTE_NO_ENCONTRADO_POR_ID + dto.idCliente()));

        EstadoPedidoEntity estadoPedido = estadoPedidoRepository.findById(dto.idEstadoPedido())
                .orElseThrow(() ->
                        new ResourceNotFoundException(ESTADO_PEDIDO_NO_ENCONTRADO_POR_ID + dto.idEstadoPedido()));

        PedidoEntity pedido = construirPedidoDesdeDTO(dto, cliente, estadoPedido);

        return pedidoMapper.toDto(pedidoRepository.save(pedido));
    }

    @Override
    public PedidoResponseDTO crearPedidoDesdeCarrito(Long idCliente, DireccionRequestDTO direccionDTO) {

        ClienteEntity cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CLIENTE_NO_ENCONTRADO_POR_ID + idCliente));

        CarritoEntity carrito = carritoRepository.findByClienteIdAndActivoTrue(idCliente)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CARRITO_NO_ENCONTRADO_POR_CLIENTE + idCliente));

        List<DetalleCarritoEntity> productosCarrito = detalleCarritoRepository.findByCarritoId(carrito.getId());

        if (productosCarrito.isEmpty()) {
            throw new IllegalStateException(CARRITO_VACIO);
        }

        EstadoPedidoEntity estadoInicial = estadoPedidoRepository.findByNombre(ESTADO_PEDIDO_PENDIENTE)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ESTADO_PEDIDO_NO_ENCONTRADO_POR_NOMBRE + ESTADO_PEDIDO_PENDIENTE));

        PedidoEntity pedido = PedidoEntity.builder()
                .cliente(cliente)
                .carrito(carrito)
                .estadoPedido(estadoInicial)
                .fecha(LocalDateTime.now())
                .direccion(direccionMapper.toEntity(direccionDTO))
                .build();

        List<DetallePedidoEntity> detalles = construirDetallesPedido(pedido, productosCarrito);

        pedido.setDetalles(detalles);
        pedido.setTotal(pedido.calcularTotal());
        pedidoRepository.save(pedido);

        return pedidoMapper.toDto(pedido);
    }

    @Override
    public PedidoResponseDTO obtenerPedidoPorId(Long idPedido) {

        PedidoEntity pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() ->
                        new ResourceNotFoundException(PEDIDO_NO_ENCONTRADO_POR_ID + idPedido));

        return pedidoMapper.toDto(pedido);
    }

    @Override
    public List<PedidoResponseDTO> obtenerPedidosPendientesPorCliente(Long idCliente) {

        clienteRepository.findById(idCliente)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CLIENTE_NO_ENCONTRADO_POR_ID + idCliente));

        List<PedidoEntity> pedidosPendientes = pedidoRepository.findByClienteIdAndEstadoPedidoNombre(idCliente, ESTADO_PEDIDO_PENDIENTE);

        return pedidosPendientes.stream()
                .map(pedidoMapper::toDto)
                .toList();
    }

    @Override
    public List<PedidoResponseDTO> obtenerPedidosPorCliente(Long idCliente) {

        clienteRepository.findById(idCliente)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CLIENTE_NO_ENCONTRADO_POR_ID + idCliente));

        List<PedidoEntity> pedidos = pedidoRepository.findByClienteId(idCliente);

        return pedidos.stream()
                .map(pedidoMapper::toDto)
                .toList();
    }

    @Override
    public PedidoResponseDTO actualizarEstadoPedido(Long idPedido, Long idEstado) {

        PedidoEntity pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() ->
                        new ResourceNotFoundException(PEDIDO_NO_ENCONTRADO_POR_ID + idPedido));

        EstadoPedidoEntity estado = estadoPedidoRepository.findById(idEstado)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ESTADO_PEDIDO_NO_ENCONTRADO_POR_ID + idEstado));

        pedido.setEstadoPedido(estado);
        pedidoRepository.save(pedido);

        return pedidoMapper.toDto(pedido);
    }
}