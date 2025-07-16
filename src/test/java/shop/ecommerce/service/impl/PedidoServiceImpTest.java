package shop.ecommerce.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.ecommerce.dto.request.DireccionRequestDTO;
import shop.ecommerce.dto.request.PedidoRequestDTO;
import shop.ecommerce.dto.response.ClienteResponseDTO;
import shop.ecommerce.dto.response.DireccionResponseDTO;
import shop.ecommerce.dto.response.PedidoResponseDTO;
import shop.ecommerce.exception.ResourceNotFoundException;
import shop.ecommerce.mapper.PedidoMapper;
import shop.ecommerce.model.CarritoEntity;
import shop.ecommerce.model.ClienteEntity;
import shop.ecommerce.model.EstadoPedidoEntity;
import shop.ecommerce.model.PedidoEntity;
import shop.ecommerce.repository.ClienteRepository;
import shop.ecommerce.repository.EstadoPedidoRepository;
import shop.ecommerce.repository.PedidoRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.*;
import static shop.ecommerce.constants.EstadoPedidoConstants.ESTADO_PEDIDO_PENDIENTE;
import static shop.ecommerce.constants.PedidoConstants.COSTO_ENVIO;
import static shop.ecommerce.constants.TestConstants.*;


@ExtendWith(MockitoExtension.class)
public class PedidoServiceImpTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private EstadoPedidoRepository estadoPedidoRepository;

    @Mock
    private PedidoMapper pedidoMapper;

    @InjectMocks
    private PedidoServiceImp service;

    private PedidoResponseDTO responseDTO;
    private PedidoRequestDTO requestDTO;
    private PedidoEntity pedidoEntity;
    private CarritoEntity carritoEntity;
    private EstadoPedidoEntity estadoPedidoEntity;
    private ClienteEntity clienteEntity;

    @BeforeEach
    void setUp() {

        DireccionRequestDTO direccionRequestDTO = new DireccionRequestDTO(
                5700,
                "Calle 123",
                27,
                null,
                "Casa con rejas"
        );

        DireccionResponseDTO direccionResponseDTO = new DireccionResponseDTO(
                direccionRequestDTO.codigo_postal(),
                direccionRequestDTO.calle(),
                direccionRequestDTO.numero(),
                direccionRequestDTO.piso(),
                direccionRequestDTO.referencia()
        );

        requestDTO = new PedidoRequestDTO(
                LocalDateTime.now(),
                COSTO_ENVIO,
                new BigDecimal(2000),
                CLIENTE_ID,
                direccionRequestDTO,
                ESTADO_PEDIDO_ID,
                CARRITO_ID
        );

        clienteEntity = ClienteEntity.builder()
                .id(CLIENTE_ID)
                .nombre("Jepeto")
                .apellido("Sanchez")
                .dni("1222323")
                .telefono("4423233")
                .build();

        carritoEntity = CarritoEntity.builder()
                .id(CARRITO_ID)
                .fecha(LocalDate.now())
                .activo(true)
                .cliente(clienteEntity)
                .productos(List.of())
                .build();

        carritoEntity = CarritoEntity.builder()
                .id(CARRITO_ID)
                .fecha(LocalDate.now())
                .activo(true)
                .cliente(clienteEntity)
                .productos(List.of())
                .build();

        estadoPedidoEntity = EstadoPedidoEntity.builder()
                .id(ESTADO_PEDIDO_ID)
                .nombre("PENDIENTE")
                .build();

        pedidoEntity = PedidoEntity.builder()
                .id(PEDIDO_ID)
                .fecha(requestDTO.fecha())
                .costoEnvio(requestDTO.costoEnvio())
                .total(requestDTO.total())
                .cliente(clienteEntity)
                .estadoPedido(estadoPedidoEntity)
                .carrito(carritoEntity)
                .build();

        ClienteResponseDTO clienteDTO = new ClienteResponseDTO(
                clienteEntity.getNombre(),
                clienteEntity.getApellido(),
                clienteEntity.getDni(),
                clienteEntity.getTelefono()
        );

        responseDTO = new PedidoResponseDTO(
                requestDTO.fecha(),
                requestDTO.costoEnvio(),
                requestDTO.total(),
                clienteDTO,
                direccionResponseDTO,
                estadoPedidoEntity.getNombre());
    }

    @Test
    void crearPedido_exitosamente() {
        when(clienteRepository.findById(CLIENTE_ID)).thenReturn(Optional.of(clienteEntity));
        when(estadoPedidoRepository.findById(ESTADO_PEDIDO_ID)).thenReturn(Optional.of(estadoPedidoEntity));
        when(pedidoMapper.toEntity(requestDTO)).thenReturn(pedidoEntity);
        when(pedidoRepository.save(pedidoEntity)).thenReturn(pedidoEntity);
        when(pedidoMapper.toDto(pedidoEntity)).thenReturn(responseDTO);

        PedidoResponseDTO resultado = service.crearPedido(requestDTO);

        assertThat(resultado).isEqualTo(responseDTO);
        verify(pedidoRepository).save(pedidoEntity);
        verify(pedidoMapper).toDto(pedidoEntity);
    }

    @Test
    void crearPedido_sinCliente_LanzaExcepcion() {
        when(clienteRepository.findById(CLIENTE_ID)).thenReturn(Optional.empty());

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> service.crearPedido(requestDTO));

        verify(pedidoRepository, never()).save(any());
        verify(pedidoMapper, never()).toDto(any());

    }

    @Test
    void crearPedido_sinEstadoPedido_LanzaExcepcion() {
        when(clienteRepository.findById(CLIENTE_ID)).thenReturn(Optional.of(clienteEntity));
        when(estadoPedidoRepository.findById(ESTADO_PEDIDO_ID)).thenReturn(Optional.empty());

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> service.crearPedido(requestDTO));

        verify(pedidoRepository, never()).save(any());
        verify(pedidoMapper, never()).toDto(any());
    }

    @Test
    void obtenerPedidoPorId_exitosamente(){
        when(pedidoRepository.findById(PEDIDO_ID)).thenReturn(Optional.of(pedidoEntity));
        when(pedidoMapper.toDto(pedidoEntity)).thenReturn(responseDTO);

        PedidoResponseDTO resultado = service.obtenerPedidoPorId(PEDIDO_ID);

        assertThat(resultado).isEqualTo(responseDTO);
        verify(pedidoRepository).findById(PEDIDO_ID);
    }

    @Test
    void obtenerPedidoPorId_noExiste_LanzaExcepcion(){
        when(pedidoRepository.findById(PEDIDO_ID)).thenReturn(Optional.empty());

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(()-> service.obtenerPedidoPorId(PEDIDO_ID));

        verify(pedidoRepository).findById(PEDIDO_ID);
        verify(pedidoMapper, never()).toDto(any());
    }


    @Test
    void obtenerPedidosPendientesPorCliente_exitosamente(){
        when(clienteRepository.findById(CLIENTE_ID)).thenReturn(Optional.of(clienteEntity));
        when(pedidoRepository.findByClienteIdAndEstadoPedidoNombre(CLIENTE_ID, ESTADO_PEDIDO_PENDIENTE))
                .thenReturn(List.of(pedidoEntity));
        when(pedidoMapper.toDto(pedidoEntity)).thenReturn(responseDTO);

        List<PedidoResponseDTO> resultado = service.obtenerPedidosPendientesPorCliente(CLIENTE_ID);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0)).isEqualTo(responseDTO);
        verify(clienteRepository).findById(CLIENTE_ID);
        verify(pedidoRepository).findByClienteIdAndEstadoPedidoNombre(CLIENTE_ID, ESTADO_PEDIDO_PENDIENTE);
        verify(pedidoMapper).toDto(pedidoEntity);
    }

    @Test
    void obtenerPedidosPendientesPorCliente_sinCliente_LanzaExcepcion(){
        when(clienteRepository.findById(CLIENTE_ID)).thenReturn(Optional.empty());

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> service.obtenerPedidosPendientesPorCliente(CLIENTE_ID));

        verify(clienteRepository).findById(CLIENTE_ID);
        verify(pedidoRepository, never()).findByClienteIdAndEstadoPedidoNombre(any(), any());
        verify(pedidoMapper, never()).toDto(any());
    }

    @Test
    void actualizarEstadoPedido_exitosamente(){
        when(pedidoRepository.findById(PEDIDO_ID)).thenReturn(Optional.of(pedidoEntity));
        when(estadoPedidoRepository.findById(ESTADO_PEDIDO_ID)).thenReturn(Optional.of(estadoPedidoEntity));
        when(pedidoMapper.toDto(pedidoEntity)).thenReturn(responseDTO);

        PedidoResponseDTO resultado = service.actualizarEstadoPedido(PEDIDO_ID, ESTADO_PEDIDO_ID);

        assertThat(resultado).isEqualTo(responseDTO);
        verify(pedidoRepository).save(pedidoEntity);
        verify(pedidoMapper).toDto(pedidoEntity);
    }

    @Test
    void actualizarEstadoPedido_sinEstadoPedido_lanzaExcepcion(){
        when(pedidoRepository.findById(PEDIDO_ID)).thenReturn(Optional.empty());
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> service.actualizarEstadoPedido(PEDIDO_ID, ESTADO_PEDIDO_ID));

        verify(pedidoRepository).findById(PEDIDO_ID);
        verify(pedidoRepository, never()).save(any());
        verify(pedidoMapper, never()).toDto(any());
    }

    @Test
    void actualizarEstadoPedido_sinPedido_lanzaExcepcion(){
        when(pedidoRepository.findById(PEDIDO_ID)).thenReturn(Optional.of(pedidoEntity));
        when(estadoPedidoRepository.findById(ESTADO_PEDIDO_ID)).thenReturn(Optional.empty());
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> service.actualizarEstadoPedido(PEDIDO_ID, ESTADO_PEDIDO_ID));

        verify(pedidoRepository).findById(PEDIDO_ID);
        verify(estadoPedidoRepository).findById(ESTADO_PEDIDO_ID);
        verify(pedidoRepository, never()).save(any());
        verify(pedidoMapper, never()).toDto(any());
    }
}