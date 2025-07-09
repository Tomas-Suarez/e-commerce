package shop.ecommerce.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.ecommerce.dto.request.DetalleCarritoRequestDTO;
import shop.ecommerce.dto.response.DetalleCarritoResponseDTO;
import shop.ecommerce.dto.response.ProductoResponseDTO;
import shop.ecommerce.exception.ResourceNotFoundException;
import shop.ecommerce.mapper.DetalleCarritoMapper;
import shop.ecommerce.model.CarritoEntity;
import shop.ecommerce.model.DetalleCarritoEntity;
import shop.ecommerce.model.ProductoEntity;
import shop.ecommerce.repository.CarritoRepository;
import shop.ecommerce.repository.DetalleCarritoRepository;
import shop.ecommerce.repository.ProductoRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;
import static shop.ecommerce.constants.TestConstants.*;

@ExtendWith(MockitoExtension.class)
public class DetalleCarritoServiceImpTest {

    @Mock
    private DetalleCarritoRepository detalleCarritoRepository;

    @Mock
    private CarritoRepository carritoRepository;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private DetalleCarritoMapper detalleCarritoMapper;

    @InjectMocks
    private DetalleCarritoServiceImp service;

    private DetalleCarritoRequestDTO requestDTO;
    private DetalleCarritoResponseDTO responseDTO;
    private DetalleCarritoEntity detalleCarritoEntity;
    private ProductoEntity productoEntity;
    private CarritoEntity carritoEntity;

    @BeforeEach
    void setUp() {
        requestDTO = new DetalleCarritoRequestDTO(
                2,
                new BigDecimal("4000"),
                CARRITO_ID,
                PRODUCTO_ID
        );

        productoEntity = ProductoEntity.builder()
                .id(PRODUCTO_ID)
                .nombre("productoTest")
                .precio(new BigDecimal("2000"))
                .descripcion("soy una descripcion")
                .activo(true)
                .stock(30)
                .build();

        carritoEntity = CarritoEntity.builder()
                .id(CARRITO_ID)
                .fecha(LocalDate.now())
                .productos(List.of())
                .activo(true)
                .cliente(null)
                .build();

        detalleCarritoEntity = DetalleCarritoEntity.builder()
                .id(DETALLE_CARRITO_ID)
                .carrito(carritoEntity)
                .producto(productoEntity)
                .cantidad(requestDTO.cantidad())
                .subtotal(requestDTO.subtotal())
                .build();

        ProductoResponseDTO productoResponseDTO = new ProductoResponseDTO(
                productoEntity.getId(),
                productoEntity.getNombre(),
                productoEntity.getPrecio(),
                productoEntity.getStock(),
                productoEntity.getDescripcion(),
                productoEntity.isActivo(),
                null
        );

        responseDTO = new DetalleCarritoResponseDTO(
                detalleCarritoEntity.getCantidad(),
                detalleCarritoEntity.getSubtotal(),
                productoResponseDTO
        );
    }

    @Test
    void agregarProducto_existenteEnCarrito_actualizaCantidadYSubtotal() {
        DetalleCarritoEntity existente = DetalleCarritoEntity.builder()
                .id(DETALLE_CARRITO_ID)
                .carrito(carritoEntity)
                .producto(productoEntity)
                .cantidad(1)
                .subtotal(new BigDecimal("2000"))
                .build();

        when(carritoRepository.findById(CARRITO_ID)).thenReturn(Optional.of(carritoEntity));
        when(productoRepository.findById(PRODUCTO_ID)).thenReturn(Optional.of(productoEntity));
        when(detalleCarritoRepository.findByCarritoIdAndProductoId(CARRITO_ID, PRODUCTO_ID)).thenReturn(Optional.of(existente));
        when(detalleCarritoMapper.toDto(existente)).thenReturn(responseDTO);

        DetalleCarritoResponseDTO resultado = service.agregarProductoAlCarrito(requestDTO);

        assertThat(existente.getCantidad()).isEqualTo(3);
        assertThat(existente.getSubtotal()).isEqualTo(new BigDecimal("6000"));
        assertThat(resultado).isEqualTo(responseDTO);
        verify(detalleCarritoRepository).save(existente);
    }

    @Test
    void agregarProducto_nuevoAlCarrito() {
        when(carritoRepository.findById(CARRITO_ID)).thenReturn(Optional.of(carritoEntity));
        when(productoRepository.findById(PRODUCTO_ID)).thenReturn(Optional.of(productoEntity));
        when(detalleCarritoRepository.findByCarritoIdAndProductoId(CARRITO_ID, PRODUCTO_ID))
                .thenReturn(Optional.empty());
        when(detalleCarritoMapper.toEntity(requestDTO)).thenReturn(detalleCarritoEntity);
        when(detalleCarritoMapper.toDto(detalleCarritoEntity)).thenReturn(responseDTO);

        DetalleCarritoResponseDTO resultado = service.agregarProductoAlCarrito(requestDTO);

        assertThat(resultado).isEqualTo(responseDTO);
        verify(detalleCarritoRepository).save(detalleCarritoEntity);
    }

    @Test
    void agregarProducto_carritoInexistente_lanzaException() {
        when(carritoRepository.findById(CARRITO_ID)).thenReturn(Optional.empty());

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> service.agregarProductoAlCarrito(requestDTO));

        verify(detalleCarritoRepository, never()).save(any());
    }

    @Test
    void actualizarProductoDelCarrito_exitosamente() {
        when(detalleCarritoRepository.findById(DETALLE_CARRITO_ID)).thenReturn(Optional.of(detalleCarritoEntity));
        when(carritoRepository.findById(CARRITO_ID)).thenReturn(Optional.of(carritoEntity));
        when(productoRepository.findById(PRODUCTO_ID)).thenReturn(Optional.of(productoEntity));
        doNothing().when(detalleCarritoMapper).updateEntityFromDto(requestDTO, detalleCarritoEntity);
        when(detalleCarritoMapper.toDto(detalleCarritoEntity)).thenReturn(responseDTO);

        DetalleCarritoResponseDTO resultado = service.actualizarProductoDelCarrito(DETALLE_CARRITO_ID, requestDTO);

        assertThat(resultado).isEqualTo(responseDTO);
        verify(detalleCarritoRepository).save(detalleCarritoEntity);
    }

    @Test
    void eliminarProducto_delCarrito_exitosamente() {
        when(detalleCarritoRepository.findById(DETALLE_CARRITO_ID)).thenReturn(Optional.of(detalleCarritoEntity));

        service.eliminarProductoDelCarrito(DETALLE_CARRITO_ID);

        verify(detalleCarritoRepository).deleteById(DETALLE_CARRITO_ID);
    }

    @Test
    void obtenerProductosDelCarrito(){
        when(carritoRepository.findById(CARRITO_ID)).thenReturn(Optional.of(carritoEntity));
        when(detalleCarritoRepository.findByCarritoId(CARRITO_ID)).thenReturn(List.of(detalleCarritoEntity));
        when(detalleCarritoMapper.toDto(detalleCarritoEntity)).thenReturn(responseDTO);

        List<DetalleCarritoResponseDTO> resultado = service.obtenerProductosDelCarrito(CARRITO_ID);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0)).isEqualTo(responseDTO);
    }

}