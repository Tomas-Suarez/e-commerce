package shop.ecommerce.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.ecommerce.dto.request.CarritoRequestDTO;
import shop.ecommerce.dto.response.CarritoResponseDTO;
import shop.ecommerce.dto.response.ClienteResponseDTO;
import shop.ecommerce.dto.response.DetalleCarritoResponseDTO;
import shop.ecommerce.dto.response.ProductoResponseDTO;
import shop.ecommerce.exception.ResourceNotFoundException;
import shop.ecommerce.mapper.CarritoMapper;
import shop.ecommerce.model.CarritoEntity;
import shop.ecommerce.model.ClienteEntity;
import shop.ecommerce.model.DetalleCarritoEntity;
import shop.ecommerce.repository.CarritoRepository;
import shop.ecommerce.repository.ClienteRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.*;
import static shop.ecommerce.constants.TestConstants.CARRITO_ID;
import static shop.ecommerce.constants.TestConstants.CLIENTE_ID;

@ExtendWith(MockitoExtension.class)
public class CarritoServiceImpTest {

    @Mock
    private CarritoRepository carritoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private CarritoMapper carritoMapper;

    @InjectMocks
    private CarritoServiceImp service;

    private CarritoResponseDTO responseDTO;
    private CarritoRequestDTO requestDTO;
    private CarritoEntity carritoEntity;
    private ClienteEntity clienteEntity;

    @BeforeEach
    void setUp() {
        requestDTO = new CarritoRequestDTO(
                LocalDate.now(),
                true,
                CLIENTE_ID
        );

        clienteEntity = ClienteEntity.builder()
                .id(CLIENTE_ID)
                .nombre("Jepeto")
                .apellido("Sanchez")
                .dni("1222323")
                .telefono("4423233")
                .direccion("av falsa")
                .build();

        carritoEntity = CarritoEntity.builder()
                .id(CARRITO_ID)
                .fecha(requestDTO.fecha())
                .activo(requestDTO.activo())
                .cliente(clienteEntity)
                .productos(List.of())
                .build();

        ClienteResponseDTO clienteDTO = new ClienteResponseDTO(
                clienteEntity.getNombre(),
                clienteEntity.getApellido(),
                clienteEntity.getDni(),
                clienteEntity.getTelefono(),
                clienteEntity.getDireccion()
        );

        // Podés usar un producto dummy o null si no querés complicar el test aún
        ProductoResponseDTO productoDTO = new ProductoResponseDTO(
                1L,
                "Producto Test",
                new BigDecimal("1000"),
                5,
                "Descripción",
                true,
                "Categoría"
        );

        DetalleCarritoResponseDTO detalleDTO = new DetalleCarritoResponseDTO(
                2,
                new BigDecimal("2000"),
                productoDTO
        );

        responseDTO = new CarritoResponseDTO(
                carritoEntity.getFecha(),
                carritoEntity.isActivo(),
                clienteDTO,
                List.of(detalleDTO)
        );

        DetalleCarritoEntity detalle = DetalleCarritoEntity.builder()
                .id(1L)
                .cantidad(2)
                .subtotal(new BigDecimal("2000"))
                .producto(null)
                .carrito(carritoEntity)
                .build();

        carritoEntity.setProductos(new ArrayList<>(List.of(detalle)));

    }

    @Test
    void crearCarrito_exitosamente() {
        when(clienteRepository.findById(CLIENTE_ID)).thenReturn(Optional.of(clienteEntity));
        when(carritoMapper.toEntity(requestDTO)).thenReturn(carritoEntity);
        when(carritoRepository.save(carritoEntity)).thenReturn(carritoEntity);
        when(carritoMapper.toDto(carritoEntity)).thenReturn(responseDTO);

        CarritoResponseDTO resultado = service.crearCarrito(requestDTO);

        assertThat(resultado).isEqualTo(responseDTO);
        verify(carritoRepository).save(carritoEntity);
        verify(carritoMapper).toDto(carritoEntity);
        System.out.println(resultado);

    }

    @Test
    void crearCarrito_sinClienteExistente_LanzaException() {
        when(clienteRepository.findById(CLIENTE_ID)).thenReturn(Optional.empty());

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> service.crearCarrito(requestDTO));

        verify(carritoRepository, never()).save(any());
        verify(carritoMapper, never()).toDto(any());

    }

    @Test
    void obtenerCarritoPorId_correctamente() {
        when(carritoRepository.findById(CARRITO_ID)).thenReturn(Optional.of(carritoEntity));
        when(carritoMapper.toDto(carritoEntity)).thenReturn(responseDTO);

        CarritoResponseDTO resultado = service.obtenerCarritoPorId(CARRITO_ID);

        assertThat(resultado).isEqualTo(responseDTO);
        verify(carritoRepository).findById(CARRITO_ID);

        System.out.println(resultado);

    }

    @Test
    void obtenerCarritoPorId_NoExiste_LanzaException(){
        when(carritoRepository.findById(CARRITO_ID)).thenReturn(Optional.empty());

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> service.obtenerCarritoPorId(CARRITO_ID));

        verify(carritoRepository).findById(CARRITO_ID);
        verify(carritoMapper, never()).toDto(carritoEntity);
    }

    @Test
    void obtenerCarritoActivoPorIdCliente_correctamente() {
        when(carritoRepository.findByClienteIdAndActivoTrue(CLIENTE_ID)).thenReturn(Optional.of(carritoEntity));
        when(carritoMapper.toDto(carritoEntity)).thenReturn(responseDTO);

        CarritoResponseDTO resultado = service.obtenerCarritoActivoPorCliente(CLIENTE_ID);

        assertThat(resultado).isEqualTo(responseDTO);
        verify(carritoRepository).findByClienteIdAndActivoTrue(CLIENTE_ID);

        System.out.println(resultado);
    }

    @Test
    void obtenerClientePorId_NoExiste_LanzaException(){
        when(carritoRepository.findByClienteIdAndActivoTrue(CLIENTE_ID)).thenReturn(Optional.empty());

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> service.obtenerCarritoActivoPorCliente(CLIENTE_ID));

        verify(carritoRepository).findByClienteIdAndActivoTrue(CLIENTE_ID);
        verify(carritoMapper, never()).toDto(carritoEntity);
    }

    @Test
    void vaciarCarrito_correctamente(){
        when(carritoRepository.findById(CARRITO_ID)).thenReturn(Optional.of(carritoEntity));

        service.vaciarCarrito(CARRITO_ID);

        verify(carritoRepository).save(carritoEntity);
        verify(carritoRepository).findById(CARRITO_ID);
    }

    @Test
    void vaciarCarrito_noExiste_LanzaException(){
        when(carritoRepository.findById(CARRITO_ID)).thenReturn(Optional.empty());

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> service.vaciarCarrito(CARRITO_ID));

        verify(carritoRepository, never()).save(any());

    }

    //Finalizar carrito - pendiente



}
