package pasteleria.LePettit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pasteleria.LePettit.dto.request.PedidoRequestDTO;
import pasteleria.LePettit.dto.response.PedidoResponseDTO;
import pasteleria.LePettit.model.PedidoEntity;

@Mapper(componentModel = "spring", uses = {
        ClienteMapper.class
})
public interface PedidoMapper {

    //DTO -> Entity
    @Mapping(source = "idCliente", target = "cliente.id")
    @Mapping(source = "idEstadoPedido", target = "estadoPedido.id")
    PedidoEntity toEntity(PedidoRequestDTO dto);

    //Entity -> DTO
    @Mapping(source = "estadoPedido.nombre", target = "estadoPedido")
    PedidoResponseDTO toDto(PedidoEntity entity);

}
