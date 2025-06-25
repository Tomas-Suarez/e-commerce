package pasteleria.LePettit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pasteleria.LePettit.dto.request.ProductoRequestDTO;
import pasteleria.LePettit.dto.response.ProductoResponseDTO;
import pasteleria.LePettit.model.ProductoEntity;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    //DTO -> Entity
    @Mapping(source = "categoriaId", target = "categoria.id")
    ProductoEntity toEntity(ProductoRequestDTO dto);

    //Entity -> DTO
    @Mapping(source = "categoria.nombre", target = "estadoEnvio")
    ProductoResponseDTO toDto(ProductoEntity entity);
}
