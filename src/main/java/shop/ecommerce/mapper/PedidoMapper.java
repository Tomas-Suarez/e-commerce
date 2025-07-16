package shop.ecommerce.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import shop.ecommerce.dto.request.PedidoRequestDTO;
import shop.ecommerce.dto.response.ClienteResponseDTO;
import shop.ecommerce.dto.response.DireccionResponseDTO;
import shop.ecommerce.dto.response.PedidoResponseDTO;
import shop.ecommerce.model.*;

@Component
@RequiredArgsConstructor
public class PedidoMapper {

    private final ModelMapper modelMapper;
    private final ClienteMapper clienteMapper;
    private final DireccionMapper direccionMapper;

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

        if(dto.direccion() != null){
            pedido.setDireccion(direccionMapper.toEntity(dto.direccion()));
        }

        return pedido;
    }

    public PedidoResponseDTO toDto(PedidoEntity entity){
        ClienteResponseDTO clienteResponseDTO = clienteMapper.toDto(entity.getCliente());
        DireccionResponseDTO direccionResponseDTO = direccionMapper.toDto(entity.getDireccion());

        return new PedidoResponseDTO(
                entity.getFecha(),
                entity.getCostoEnvio(),
                entity.getTotal(),
                clienteResponseDTO,
                direccionResponseDTO,
                entity.getEstadoPedido().getNombre()
        );
    }

}
