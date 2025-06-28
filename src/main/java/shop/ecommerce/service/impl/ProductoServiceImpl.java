package shop.ecommerce.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.ecommerce.dto.request.ProductoRequestDTO;
import shop.ecommerce.dto.response.ProductoResponseDTO;
import shop.ecommerce.exception.ResourceNotFoundException;
import shop.ecommerce.mapper.ProductoMapper;
import shop.ecommerce.model.CategoriaEntity;
import shop.ecommerce.model.ProductoEntity;
import shop.ecommerce.repository.CategoriaRepository;
import shop.ecommerce.repository.ProductoRepository;
import shop.ecommerce.service.ProductoService;

import java.util.List;

import static shop.ecommerce.constants.ProductoConstants.CATEGORIA_NO_ENCONTRADA;
import static shop.ecommerce.constants.ProductoConstants.PRODUCTO_NO_ENCONTRADO;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoMapper productoMapper;

    @Override
    public ProductoResponseDTO crearProducto(ProductoRequestDTO dto) {

        CategoriaEntity categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(CATEGORIA_NO_ENCONTRADA + dto.categoriaId()));

        ProductoEntity producto = productoMapper.toEntity(dto);
        producto.setCategoria(categoria);
        producto.setActivo(true);

        return productoMapper.toDto(productoRepository.save(producto));
    }

    @Override
    public List<ProductoResponseDTO> obtenerProductosActivos() {
        List<ProductoEntity> productos = productoRepository.findAll()
                .stream()
                .filter(ProductoEntity::isActivo)
                .toList();

        return productos.stream()
                .map(productoMapper::toDto)
                .toList();
    }

    @Override
    public List<ProductoResponseDTO> obtenerProductosInactivos() {
        List<ProductoEntity> productos = productoRepository.findAll()
                .stream()
                .filter(productoEntity-> !productoEntity.isActivo())
                .toList();

        return productos.stream()
                .map(productoMapper::toDto)
                .toList();
    }

    @Override
    public ProductoResponseDTO obtenerProductoPorId(Long id) {
        ProductoEntity productoEntity = productoRepository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException(PRODUCTO_NO_ENCONTRADO + id));
        return productoMapper.toDto(productoEntity);
    }

    @Override
    public List<ProductoResponseDTO> obtenerPorCategoriaId(Long categoriaId) {

        if(!categoriaRepository.existsById(categoriaId)){
            throw new ResourceNotFoundException(CATEGORIA_NO_ENCONTRADA + categoriaId);
        }

        List<ProductoEntity> productos = productoRepository.findByCategoriaIdAndActivoTrue(categoriaId);
        return productos.stream()
                .map(productoMapper::toDto)
                .toList();
    }

    @Override
    public ProductoResponseDTO actualizarProducto(Long id, ProductoRequestDTO dto) {
        ProductoEntity producto = productoRepository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException(PRODUCTO_NO_ENCONTRADO + id));

        CategoriaEntity categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(()->
                        new ResourceNotFoundException(CATEGORIA_NO_ENCONTRADA + dto.categoriaId()));

        productoMapper.updateEntityFromDto(dto, producto);
        producto.setCategoria(categoria);

        return productoMapper.toDto(productoRepository.save(producto));
    }

    @Override
    public void borradoLogicoProducto(Long id) {
        ProductoEntity producto = productoRepository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException(PRODUCTO_NO_ENCONTRADO + id));

        producto.setActivo(false);
        productoRepository.save(producto);
    }
}
