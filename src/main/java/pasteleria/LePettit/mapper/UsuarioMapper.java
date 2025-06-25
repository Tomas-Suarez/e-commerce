package pasteleria.LePettit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pasteleria.LePettit.dto.request.UsuarioRequestDTO;
import pasteleria.LePettit.dto.response.UsuarioResponseDTO;
import pasteleria.LePettit.model.UsuarioEntity;

@Mapper(componentModel = "spring", uses = {
        ClienteMapper.class
})
public interface UsuarioMapper {

    //DTO -> Entity
    @Mapping(source = "rolId", target = "rol.id")
    UsuarioEntity toEntity(UsuarioRequestDTO dto);

    //Entity -> DTO
    @Mapping(source = "rol.nombre", target = "rolNombre")
    UsuarioResponseDTO toDto(UsuarioEntity entity);
}
