package shop.ecommerce.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.ecommerce.dto.request.ProductoRequestDTO;
import shop.ecommerce.dto.response.ProductoResponseDTO;
import shop.ecommerce.exception.ResourceNotFoundException;
import shop.ecommerce.mapper.ProductoMapper;
import shop.ecommerce.model.CategoriaEntity;
import shop.ecommerce.model.ProductoEntity;
import shop.ecommerce.repository.CategoriaRepository;
import shop.ecommerce.repository.ProductoRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static shop.ecommerce.constants.TestConstants.CATEGORIA_ID;
import static shop.ecommerce.constants.TestConstants.PRODUCTO_ID;

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
                CATEGORIA_ID
        );

        categoria = CategoriaEntity.builder()
                .id(CATEGORIA_ID)
                .nombre("Tortas")
                .build();

        productoEntity = ProductoEntity.builder()
                .id(PRODUCTO_ID)
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
        when(categoriaRepository.findById(CATEGORIA_ID)).thenReturn(Optional.of(categoria));
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
        when(categoriaRepository.findById(CATEGORIA_ID)).thenReturn(Optional.empty());

        assertThatExceptionOfType(ResourceNotFoundException.class)
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
                .activo(false)
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
        when(productoRepository.findById(PRODUCTO_ID)).thenReturn(Optional.of(productoEntity));
        when(productoMapper.toDto(productoEntity)).thenReturn(responseDTO);

        ProductoResponseDTO resultado = service.obtenerProductoPorId(PRODUCTO_ID);

        assertThat(resultado).isEqualTo(responseDTO);

        verify(productoRepository).findById(PRODUCTO_ID);
        System.out.println(resultado);
    }

    @Test
    void obtenerProductoPorId_NoExistente_LanzaException(){
        when(productoRepository.findById(PRODUCTO_ID)).thenReturn(Optional.empty());

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(()-> service.obtenerProductoPorId(PRODUCTO_ID));

        verify(productoRepository).findById(PRODUCTO_ID);

        verify(productoMapper, never()).toDto(productoEntity);
    }

    @Test
    void actualizarUnProducto_existente(){
        when(productoRepository.findById(PRODUCTO_ID)).thenReturn(Optional.of(productoEntity));
        when(categoriaRepository.findById(CATEGORIA_ID)).thenReturn(Optional.of(categoria));
        doNothing().when(productoMapper).updateEntityFromDto(requestDTO, productoEntity);
        when(productoRepository.save(productoEntity)).thenReturn(productoEntity);
        when(productoMapper.toDto(productoEntity)).thenReturn(responseDTO);

        ProductoResponseDTO resultado = service.actualizarProducto(PRODUCTO_ID, requestDTO);

        assertThat(resultado).isEqualTo(responseDTO);
    }

    @Test
    void actualizarProducto_productoNoExistente_LanzaException(){
        when(productoRepository.findById(PRODUCTO_ID)).thenReturn(Optional.empty());

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(()-> service.actualizarProducto(PRODUCTO_ID, requestDTO));

        verify(productoRepository, never()).save(any());
        verify(productoMapper, never()).updateEntityFromDto(any(), any());
    }

    @Test
    void actualizarProducto_categoriaNoExistente_LanzaException(){
        when(productoRepository.findById(PRODUCTO_ID)).thenReturn(Optional.of(productoEntity));
        when(categoriaRepository.findById(CATEGORIA_ID)).thenReturn(Optional.empty());

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(()-> service.actualizarProducto(PRODUCTO_ID, requestDTO));

        verify(productoRepository, never()).save(any());
        verify(productoMapper, never()).updateEntityFromDto(any(), any());
    }

    @Test
    void borradoLogico_porProductoExistente(){
        when(productoRepository.findById(PRODUCTO_ID)).thenReturn(Optional.of(productoEntity));
        service.borradoLogicoProducto(PRODUCTO_ID);

        assertThat(productoEntity.isActivo()).isFalse();
        verify(productoRepository).save(productoEntity);

    }

    @Test
    void borradoLogico_ProductoNoExistente_LanzarException(){
        when(productoRepository.findById(PRODUCTO_ID)).thenReturn(Optional.empty());

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(()-> service.borradoLogicoProducto(PRODUCTO_ID));

        verify(productoRepository, never()).save(any());
    }

    @Test
    void obtenerProductos_porCategoria(){
        List<ProductoEntity> productos = List.of(productoEntity);

        when(categoriaRepository.existsById(CATEGORIA_ID)).thenReturn(true);
        when(productoRepository.findByCategoriaIdAndActivoTrue(CATEGORIA_ID)).thenReturn(productos);
        when(productoMapper.toDto(productoEntity)).thenReturn(responseDTO);

        List<ProductoResponseDTO> resultado = service.obtenerPorCategoriaId(CATEGORIA_ID);

        verify(productoRepository).findByCategoriaIdAndActivoTrue(CATEGORIA_ID);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0)).isEqualTo(responseDTO);

        System.out.println(resultado);
    }
    @Test
    void obtenerProductos_porCategoriaNoExistente_LanzarException(){
        when(categoriaRepository.existsById(CATEGORIA_ID)).thenReturn(false);

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(()-> service.obtenerPorCategoriaId(CATEGORIA_ID));

        verify(productoRepository, never()).findByCategoriaIdAndActivoTrue(any());
    }

}
