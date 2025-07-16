package shop.ecommerce.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.ecommerce.dto.request.CarritoRequestDTO;
import shop.ecommerce.dto.request.DireccionRequestDTO;
import shop.ecommerce.dto.response.CarritoResponseDTO;
import shop.ecommerce.dto.response.FinalizarCarritoResponseDTO;
import shop.ecommerce.dto.response.PedidoResponseDTO;
import shop.ecommerce.exception.InactiveCartException;
import shop.ecommerce.exception.ResourceNotFoundException;
import shop.ecommerce.mapper.CarritoMapper;
import shop.ecommerce.model.CarritoEntity;
import shop.ecommerce.model.ClienteEntity;
import shop.ecommerce.repository.CarritoRepository;
import shop.ecommerce.repository.ClienteRepository;
import shop.ecommerce.service.CarritoService;
import shop.ecommerce.service.PedidoService;

import java.time.LocalDate;

import static shop.ecommerce.constants.CarritoConstants.*;
import static shop.ecommerce.constants.ClienteConstants.CLIENTE_NO_ENCONTRADO_POR_ID;

@Service
@RequiredArgsConstructor
public class CarritoServiceImp implements CarritoService {

    private final CarritoRepository carritoRepository;
    private final CarritoMapper carritoMapper;
    private final PedidoService pedidoService;
    private final ClienteRepository clienteRepository;

    @Override
    @Transactional
    public CarritoResponseDTO crearCarrito(CarritoRequestDTO dto) {

        ClienteEntity clienteEntity = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(CLIENTE_NO_ENCONTRADO_POR_ID + dto.clienteId()));

        CarritoEntity carritoEntity = carritoMapper.toEntity(dto);
        carritoEntity.setCliente(clienteEntity);
        carritoEntity.setFecha(LocalDate.now());
        carritoEntity.setActivo(true);

        return carritoMapper.toDto(carritoRepository.save(carritoEntity));
    }

    @Override
    public CarritoResponseDTO obtenerCarritoPorId(Long carritoId) {

        CarritoEntity carritoEntity = carritoRepository.findById(carritoId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CARRITO_NO_ENCONTRADO_POR_ID + carritoId));

        return carritoMapper.toDto(carritoEntity);
    }

    @Override
    public CarritoResponseDTO obtenerCarritoActivoPorCliente(Long clienteId) {

        CarritoEntity carritoEntity = carritoRepository.findByClienteIdAndActivoTrue(clienteId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CARRITO_NO_ENCONTRADO_POR_CLIENTE + clienteId));

        return carritoMapper.toDto(carritoEntity);
    }

    @Override
    @Transactional
    public void vaciarCarrito(Long carritoId) {

        CarritoEntity carritoEntity = carritoRepository.findById(carritoId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CARRITO_NO_ENCONTRADO_POR_ID + carritoId));

        carritoEntity.getProductos().clear();
        carritoRepository.save(carritoEntity);

    }

    @Override
    @Transactional
    public FinalizarCarritoResponseDTO finalizarCarrito(Long carritoId, DireccionRequestDTO direccionDto) {

        CarritoEntity carritoEntity = carritoRepository.findById(carritoId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(CARRITO_NO_ENCONTRADO_POR_ID + carritoId));

        if (!carritoEntity.isActivo()) {
            throw new InactiveCartException(CARRITO_FINALIZADO);
        }

        carritoEntity.setActivo(false);
        carritoRepository.save(carritoEntity);

        Long clienteId = carritoEntity.getCliente().getId();
        PedidoResponseDTO pedido = pedidoService.crearPedidoDesdeCarrito(clienteId, direccionDto);

        vaciarCarrito(carritoId);

        CarritoRequestDTO nuevoCarritoDTO = new CarritoRequestDTO(LocalDate.now(), true, clienteId);
        CarritoResponseDTO nuevoCarrito = crearCarrito(nuevoCarritoDTO);

        return new FinalizarCarritoResponseDTO(
                carritoMapper.toDto(carritoEntity),
                nuevoCarrito,
                pedido
        );
    }
}
