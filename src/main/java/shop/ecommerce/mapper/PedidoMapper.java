package shop.ecommerce.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import shop.ecommerce.dto.request.PedidoRequestDTO;
import shop.ecommerce.dto.response.ClienteResponseDTO;
import shop.ecommerce.dto.response.PedidoResponseDTO;
import shop.ecommerce.model.CarritoEntity;
import shop.ecommerce.model.ClienteEntity;
import shop.ecommerce.model.EstadoPedidoEntity;
import shop.ecommerce.model.PedidoEntity;

@Component
@RequiredArgsConstructor
public class PedidoMapper {

    private ModelMapper modelMapper;
    private ClienteMapper clienteMapper;

    private ClienteEntity crearClientePorId(Long clienteId) {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(clienteId);
        return cliente;
    }

    private EstadoPedidoEntity crearEstadoPedidoPorId(Long idEstadoPedido) {
        EstadoPedidoEntity estadoPedido = new EstadoPedidoEntity();
        estadoPedido.setId(idEstadoPedido);
        return estadoPedido;
    }

    private CarritoEntity crearCarritoPorId(Long idCarrito){
        CarritoEntity carrito = new CarritoEntity();
        carrito.setId(idCarrito);
        return carrito;
    }

    public PedidoEntity toEntity(PedidoRequestDTO dto){
        PedidoEntity pedido = modelMapper.map(dto, PedidoEntity.class);

        if(dto.idEstadoPedido() != null){
            pedido.setEstadoPedido(crearEstadoPedidoPorId(dto.idEstadoPedido()));
        }

        if(dto.idCliente() != null){
            pedido.setCliente(crearClientePorId(dto.idCliente()));
        }

        if(dto.idCarrito() != null){
            pedido.setCarrito(crearCarritoPorId(dto.idCarrito()));
        }

        return pedido;
    }

    public PedidoResponseDTO toDto(PedidoEntity entity){
        ClienteResponseDTO clienteResponseDTO = clienteMapper.toDto(entity.getCliente());

        return new PedidoResponseDTO(
                entity.getFecha(),
                entity.getTotal(),
                clienteResponseDTO,
                entity.getEstadoPedido() != null ? entity.getEstadoPedido().getNombre() : null
        );
    }

}
