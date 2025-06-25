package pasteleria.LePettit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pasteleria.LePettit.dto.request.FacturaRequestDTO;
import pasteleria.LePettit.dto.response.FacturaResponseDTO;
import pasteleria.LePettit.model.FacturaEntity;

@Mapper(componentModel = "spring")
public interface FacturaMapper {

    //DTO -> Entity
    @Mapping(source = "CondicionIvaId", target = "condicionIva.id")
    @Mapping(source = "EstadoFacturaId", target = "estadoFactura.id")
    @Mapping(source = "TipoFacturaId", target = "tipoFactura.id")
    FacturaEntity toEntity(FacturaRequestDTO dto);


    //Entity -> DTO
    @Mapping(source = "condicionIva.nombre", target = "condicionIva")
    @Mapping(source = "estadoFactura.nombre", target = "estadoFactura")
    @Mapping(source = "tipoFactura.nombre", target = "tipoFactura")
    FacturaResponseDTO toDto(FacturaEntity entity);
    
}
