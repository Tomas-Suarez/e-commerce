package pasteleria.LePettit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pasteleria.LePettit.dto.request.DetallePedidoRequestDTO;
import pasteleria.LePettit.dto.response.DetallePedidoResponseDTO;
import pasteleria.LePettit.model.DetallePedidoEntity;

@Mapper(componentModel = "spring", uses = {
        ProductoMapper.class
})
public interface DetallePedidoMapper {

    //DTO -> Entity
    @Mapping(source = "idProducto", target = "producto.id")
    @Mapping(source = "idPedido", target = "pedido.id")
    DetallePedidoEntity toEntity(DetallePedidoRequestDTO dto);

    //Entity -> DTO
    DetallePedidoResponseDTO toDto(DetallePedidoEntity entity);
}
