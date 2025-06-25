package pasteleria.LePettit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pasteleria.LePettit.dto.request.DetalleCarritoRequestDTO;
import pasteleria.LePettit.dto.response.DetalleCarritoResponseDTO;
import pasteleria.LePettit.model.DetalleCarritoEntity;

@Mapper(componentModel = "spring", uses = {
        ProductoMapper.class
})
public interface DetalleCarritoMapper {

    //DTO -> Entity
    @Mapping(source = "carritoId", target = "carrito.id")
    @Mapping(source = "productoId", target = "producto.id")
    DetalleCarritoEntity toEntity(DetalleCarritoRequestDTO dto);

    //Entity -> DTO
    DetalleCarritoResponseDTO toDto(DetalleCarritoEntity entity);
}
