package shop.ecommerce.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import shop.ecommerce.dto.request.DetalleCarritoRequestDTO;
import shop.ecommerce.dto.response.DetalleCarritoResponseDTO;
import shop.ecommerce.model.CarritoEntity;
import shop.ecommerce.model.DetalleCarritoEntity;
import shop.ecommerce.model.ProductoEntity;

@Component
@RequiredArgsConstructor
public class DetalleCarritoMapper {

    private final ModelMapper modelMapper;
    private final ProductoMapper productoMapper;

    private ProductoEntity crearProductoPorId(Long idProducto) {
        ProductoEntity producto = new ProductoEntity();
        producto.setId(idProducto);
        return producto;
    }

    private CarritoEntity crearCarritoPorId(Long idCarrito) {
        CarritoEntity carrito = new CarritoEntity();
        carrito.setId(idCarrito);
        return carrito;
    }

    public DetalleCarritoEntity toEntity(DetalleCarritoRequestDTO dto) {
        DetalleCarritoEntity detalleCarrito = modelMapper.map(dto, DetalleCarritoEntity.class);

        if (dto.productoId() != null) {
            detalleCarrito.setProducto(crearProductoPorId(dto.productoId()));
        }

        if (dto.carritoId() != null) {
            detalleCarrito.setCarrito(crearCarritoPorId(dto.carritoId()));
        }

        return detalleCarrito;
    }

    public DetalleCarritoResponseDTO toDto(DetalleCarritoEntity entity) {
        return new DetalleCarritoResponseDTO(
                entity.getCantidad(),
                entity.getSubtotal(),
                productoMapper.toDto(entity.getProducto())
        );
    }

    public void updateEntityFromDto(DetalleCarritoRequestDTO dto, DetalleCarritoEntity entity) {
        modelMapper.map(dto, entity);

        if (dto.productoId() != null) {
            entity.setProducto(crearProductoPorId(dto.productoId()));
        }

        if (dto.carritoId() != null) {
            entity.setCarrito(crearCarritoPorId(dto.carritoId()));
        }
    }
}
