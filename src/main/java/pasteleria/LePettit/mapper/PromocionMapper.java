package pasteleria.LePettit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pasteleria.LePettit.dto.request.PromocionRequestDTO;
import pasteleria.LePettit.dto.response.PromocionResponseDTO;
import pasteleria.LePettit.model.PromocionEntity;

@Mapper(componentModel = "spring")
public interface PromocionMapper {

    //DTO -> Entity
    @Mapping(source = "TipoDescuentoId", target = "tipoDescuento.id")
    PromocionEntity toEntity(PromocionRequestDTO dto);

    //Entity -> DTO
    @Mapping(source = "tipoDescuento", target = "tipoDescuento.nombre")
    PromocionResponseDTO toDto(PromocionEntity entity);
}
