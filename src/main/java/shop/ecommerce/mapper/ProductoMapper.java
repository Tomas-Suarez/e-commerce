package shop.ecommerce.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import shop.ecommerce.dto.request.ProductoRequestDTO;
import shop.ecommerce.dto.response.ProductoResponseDTO;
import shop.ecommerce.model.CategoriaEntity;
import shop.ecommerce.model.ProductoEntity;

@Component
@RequiredArgsConstructor
public class ProductoMapper {

    private final ModelMapper modelMapper;

    public ProductoEntity toEntity(ProductoRequestDTO dto) {
        ProductoEntity producto = modelMapper.map(dto, ProductoEntity.class);

        if (dto.categoriaId() != null) {
            CategoriaEntity categoria = new CategoriaEntity();
            categoria.setId(dto.categoriaId());
            producto.setCategoria(categoria);
        }

        return producto;
    }

    public ProductoResponseDTO toDto(ProductoEntity entity) {
        return new ProductoResponseDTO(
                entity.getId(),
                entity.getNombre(),
                entity.getPrecio(),
                entity.getStock(),
                entity.getDescripcion(),
                entity.isActivo(),
                entity.getCategoria() != null ? entity.getCategoria().getNombre() : null
        );
    }

    public void updateEntityFromDto(ProductoRequestDTO dto, ProductoEntity entity) {
        modelMapper.map(dto, entity);

        if (dto.categoriaId() != null) {
            CategoriaEntity categoria = new CategoriaEntity();
            categoria.setId(dto.categoriaId());
            entity.setCategoria(categoria);
        }
    }

}
