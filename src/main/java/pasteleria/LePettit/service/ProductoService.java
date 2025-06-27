package pasteleria.LePettit.service;

import pasteleria.LePettit.dto.request.ProductoRequestDTO;
import pasteleria.LePettit.dto.response.ProductoResponseDTO;

import java.util.List;

public interface ProductoService {

    ProductoResponseDTO crearProducto(ProductoRequestDTO dto);

    List<ProductoResponseDTO> obtenerProductosActivos();

    List<ProductoResponseDTO> obtenerProductosInactivos();

    ProductoResponseDTO obtenerProductoPorId(Long productoId);

    List<ProductoResponseDTO> obtenerPorCategoriaId(Long categoriaId);

    ProductoResponseDTO actualizarProducto(Long productoId, ProductoRequestDTO dto);

    void borradoLogicoProducto(Long productoId);

}
