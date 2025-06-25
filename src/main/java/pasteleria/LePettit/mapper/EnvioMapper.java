package pasteleria.LePettit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pasteleria.LePettit.dto.request.EnvioRequestDTO;
import pasteleria.LePettit.model.EnvioEntity;

@Mapper(componentModel = "spring")
public interface EnvioMapper {

    //DTO -> Entity
    @Mapping(source = "PedidoId", target = "pedido.id")
    @Mapping(source = "EstadoEnvioId", target = "estadoEnvio.id")
    EnvioEntity toEntity(EnvioRequestDTO dto);

    //Entity -> DTO
    @Mapping(source = "estadoEnvio.nombre", target = "estadoEnvio")
    EnvioEntity toDto(EnvioEntity entity);
}
