package pasteleria.LePettit.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pasteleria.LePettit.dto.request.ProductoRequestDTO;
import pasteleria.LePettit.dto.response.ProductoResponseDTO;
import pasteleria.LePettit.exception.NotFoundException;
import pasteleria.LePettit.mapper.ProductoMapper;
import pasteleria.LePettit.model.CategoriaEntity;
import pasteleria.LePettit.model.ProductoEntity;
import pasteleria.LePettit.repository.CategoriaRepository;
import pasteleria.LePettit.repository.ProductoRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private ProductoMapper productoMapper;

    @InjectMocks
    private ProductoServiceImpl service;

    private ProductoRequestDTO requestDTO;
    private ProductoEntity productoEntity;
    private ProductoResponseDTO responseDTO;
    private CategoriaEntity categoria;

    @BeforeEach
    void setUp() {
        requestDTO = new ProductoRequestDTO(
                "Chocotorta",
                new BigDecimal("8900"),
                20,
                "Soy una descripcion",
                true,
                1L
        );

        categoria = CategoriaEntity.builder()
                .id(1L)
                .nombre("Tortas")
                .build();

        productoEntity = ProductoEntity.builder()
                .id(10L)
                .nombre(requestDTO.nombre())
                .descripcion(requestDTO.descripcion())
                .precio(requestDTO.precio())
                .stock(requestDTO.stock())
                .activo(true)
                .categoria(categoria)
                .build();

        responseDTO = new ProductoResponseDTO(
                productoEntity.getId(),
                productoEntity.getNombre(),
                productoEntity.getPrecio(),
                productoEntity.getStock(),
                productoEntity.getDescripcion(),
                productoEntity.isActivo(),
                categoria.getNombre()
        );
    }

    @Test
    void crearProducto_exitosamente(){
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(productoMapper.toEntity(requestDTO)).thenReturn(productoEntity);
        when(productoRepository.save(productoEntity)).thenReturn(productoEntity);
        when(productoMapper.toDto(productoEntity)).thenReturn(responseDTO);

        ProductoResponseDTO resultado = service.crearProducto(requestDTO);

        assertThat(resultado).isEqualTo(responseDTO);
        verify(productoRepository).save(productoEntity);
        verify(productoMapper).toDto(productoEntity);
        System.out.println(resultado);
    }

    @Test
    void crearProducto_sinCategoriaExistente_LanzaException(){
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> service.crearProducto(requestDTO));

        verify(productoRepository, never()).save(any());
        verify(productoMapper, never()).toDto(any());
    }

    @Test
    void obtenerTodosLosProductosActivos(){
        List<ProductoEntity> productos = List.of(productoEntity);
        when(productoRepository.findAll()).thenReturn(productos);
        when(productoMapper.toDto(productoEntity)).thenReturn(responseDTO);

        List<ProductoResponseDTO> resultado = service.obtenerProductosActivos();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0)).isEqualTo(responseDTO);
        verify(productoRepository).findAll();
        System.out.println(resultado);
    }

    @Test
    void obtenerTodosLosProductosInactivos(){
        ProductoEntity productoInactivo = ProductoEntity.builder()
                .id(11L)
                .nombre("Producto Inactivo")
                .descripcion("Descripcion")
                .precio(new BigDecimal("5000"))
                .stock(5)
                .activo(false)  // <-- Inactivo
                .categoria(categoria)
                .build();

        ProductoResponseDTO responseInactivo = new ProductoResponseDTO(
                productoInactivo.getId(),
                productoInactivo.getNombre(),
                productoInactivo.getPrecio(),
                productoInactivo.getStock(),
                productoInactivo.getDescripcion(),
                productoInactivo.isActivo(),
                categoria.getNombre()
        );

        List<ProductoEntity> productos = List.of(productoInactivo);
        when(productoRepository.findAll()).thenReturn(productos);
        when(productoMapper.toDto(productoInactivo)).thenReturn(responseInactivo);

        List<ProductoResponseDTO> resultado = service.obtenerProductosInactivos();

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0)).isEqualTo(responseInactivo);
        verify(productoRepository).findAll();
        System.out.println(resultado);
    }

    @Test
    void obtenerProductoPorId_Correctamente(){
        when(productoRepository.findById(10L)).thenReturn(Optional.of(productoEntity));
        when(productoMapper.toDto(productoEntity)).thenReturn(responseDTO);

        ProductoResponseDTO resultado = service.obtenerProductoPorId(10L);

        assertThat(resultado).isEqualTo(responseDTO);

        verify(productoRepository).findById(10L);
        System.out.println(resultado);
    }

    @Test
    void obtenerProductoPorId_NoExistente_LanzaException(){
        when(productoRepository.findById(2L)).thenReturn(Optional.empty());

        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(()-> service.obtenerProductoPorId(2L));

        verify(productoRepository).findById(2L);

        verify(productoMapper, never()).toDto(productoEntity);
    }

    @Test
    void actualizarUnProducto_existente(){
        when(productoRepository.findById(10L)).thenReturn(Optional.of(productoEntity));
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        doNothing().when(productoMapper).updateEntityFromDto(requestDTO, productoEntity);
        when(productoRepository.save(productoEntity)).thenReturn(productoEntity);
        when(productoMapper.toDto(productoEntity)).thenReturn(responseDTO);

        ProductoResponseDTO resultado = service.actualizarProducto(10L, requestDTO);

        assertThat(resultado).isEqualTo(responseDTO);
    }

    @Test
    void actualizarProducto_productoNoExistente_LanzaException(){
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(()-> service.actualizarProducto(99L, requestDTO));

        verify(productoRepository, never()).save(any());
        verify(productoMapper, never()).updateEntityFromDto(any(), any());
    }

    @Test
    void actualizarProducto_categoriaNoExistente_LanzaException(){
        when(productoRepository.findById(10L)).thenReturn(Optional.of(productoEntity));
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(()-> service.actualizarProducto(10L, requestDTO));

        verify(productoRepository, never()).save(any());
        verify(productoMapper, never()).updateEntityFromDto(any(), any());
    }

    @Test
    void borradoLogico_porProductoExistente(){
        when(productoRepository.findById(10L)).thenReturn(Optional.of(productoEntity));
        service.borradoLogicoProducto(10L);

        assertThat(productoEntity.isActivo()).isFalse();
        verify(productoRepository).save(productoEntity);

    }

    @Test
    void borradoLogico_ProductoNoExistente_LanzarException(){
        when(productoRepository.findById(10L)).thenReturn(Optional.empty());

        assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(()-> service.borradoLogicoProducto(10L));

        verify(productoRepository, never()).save(any());
    }

    @Test
    void obtenerProductos_porCategoria(){
        List<ProductoEntity> productos = List.of(productoEntity);
        when(productoRepository.findByCategoriaIdAndActivoTrue(1L)).thenReturn(productos);
        when(productoMapper.toDto(productoEntity)).thenReturn(responseDTO);

        List<ProductoResponseDTO> resultado = service.obtenerPorCategoriaId(1L);

        verify(productoRepository).findByCategoriaIdAndActivoTrue(1L);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0)).isEqualTo(responseDTO);

        System.out.println(resultado);
    }

}
