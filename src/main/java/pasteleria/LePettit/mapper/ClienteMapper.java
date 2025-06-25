package pasteleria.LePettit.mapper;

import org.mapstruct.Mapper;
import pasteleria.LePettit.dto.request.ClienteRequestDTO;
import pasteleria.LePettit.dto.response.ClienteResponseDTO;
import pasteleria.LePettit.model.ClienteEntity;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    //DTO -> Entity
    ClienteEntity toEntity(ClienteRequestDTO dto);

    //Entity -> DTO
    ClienteResponseDTO toDto(ClienteEntity entity);

}
