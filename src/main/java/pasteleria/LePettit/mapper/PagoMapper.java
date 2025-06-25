package pasteleria.LePettit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pasteleria.LePettit.dto.request.PagoRequestDTO;
import pasteleria.LePettit.dto.response.PagoResponseDTO;
import pasteleria.LePettit.model.PagoEntity;

@Mapper(componentModel = "spring")
public interface PagoMapper {

    //DTO -> Entity
    @Mapping(source = "pedidoId", target = "pedido.id")
    @Mapping(source = "metodoPagoId", target = "metodoPago")
    PagoEntity toEntity(PagoRequestDTO dto);

    //Entity -> DTO
    @Mapping(source = "metodoPago.nombre", target = "metodoPago")
    PagoResponseDTO toDto(PagoEntity entity);


}
