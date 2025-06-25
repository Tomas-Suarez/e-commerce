package pasteleria.LePettit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pasteleria.LePettit.dto.request.CarritoRequestDTO;
import pasteleria.LePettit.dto.response.CarritoResponseDTO;
import pasteleria.LePettit.model.CarritoEntity;

// Con el "Spring", hacemos inyeccion de dependencia, Para no tener que hacer una instancia manual
@Mapper(componentModel = "spring", uses = {
        ClienteMapper.class,
        DetalleCarritoMapper.class
})
public interface CarritoMapper {

    // DTO -> Entity
    @Mapping(source = "clienteId", target = "cliente.id")
    CarritoEntity toEntity(CarritoRequestDTO dto);

    //Entity -> DTO
    CarritoResponseDTO toDTO(CarritoEntity entity);

}
