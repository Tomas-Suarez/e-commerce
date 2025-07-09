package shop.ecommerce.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import shop.ecommerce.dto.request.CarritoRequestDTO;
import shop.ecommerce.dto.response.CarritoResponseDTO;
import shop.ecommerce.dto.response.ClienteResponseDTO;
import shop.ecommerce.dto.response.DetalleCarritoResponseDTO;
import shop.ecommerce.model.CarritoEntity;
import shop.ecommerce.model.ClienteEntity;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CarritoMapper {

    private final ModelMapper modelMapper;
    private final ClienteMapper clienteMapper;
    private final DetalleCarritoMapper detalleCarritoMapper;

    private ClienteEntity crearClientePorId(Long clienteId) {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(clienteId);
        return cliente;
    }

    public CarritoEntity toEntity(CarritoRequestDTO dto) {
        CarritoEntity carrito = modelMapper.map(dto, CarritoEntity.class);

        if (dto.clienteId() != null) {
            carrito.setCliente(crearClientePorId(dto.clienteId()));
        }

        return carrito;
    }

    public CarritoResponseDTO toDto(CarritoEntity entity) {
        ClienteResponseDTO clienteDTO = clienteMapper.toDto(entity.getCliente());

        List<DetalleCarritoResponseDTO> productosDTO = entity.getProductos() != null
                ? entity.getProductos().stream()
                .map(detalleCarritoMapper::toDto)
                .toList()
                : List.of();

        return new CarritoResponseDTO(
                entity.getFecha(),
                entity.isActivo(),
                clienteDTO,
                productosDTO
        );
    }
}
