package shop.ecommerce.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.ecommerce.dto.request.DetalleCarritoRequestDTO;
import shop.ecommerce.dto.response.DetalleCarritoResponseDTO;
import shop.ecommerce.exception.ResourceNotFoundException;
import shop.ecommerce.mapper.DetalleCarritoMapper;
import shop.ecommerce.model.CarritoEntity;
import shop.ecommerce.model.DetalleCarritoEntity;
import shop.ecommerce.model.ProductoEntity;
import shop.ecommerce.repository.CarritoRepository;
import shop.ecommerce.repository.DetalleCarritoRepository;
import shop.ecommerce.repository.ProductoRepository;
import shop.ecommerce.service.DetalleCarritoService;

import java.math.BigDecimal;
import java.util.List;

import static shop.ecommerce.constants.CarritoConstants.CARRITO_NO_ENCONTRADO_POR_ID;
import static shop.ecommerce.constants.DetalleCarritoConstants.DETALLE_CARRITO_NO_ENCONTRADO_POR_ID;
import static shop.ecommerce.constants.ProductoConstants.PRODUCTO_NO_ENCONTRADO_POR_ID;

@Service
@RequiredArgsConstructor
public class DetalleCarritoServiceImp implements DetalleCarritoService {

    private final DetalleCarritoRepository detalleCarritoRepository;
    private final ProductoRepository productoRepository;
    private final CarritoRepository carritoRepository;
    private final DetalleCarritoMapper detalleCarritoMapper;

    @Override
    public DetalleCarritoResponseDTO agregarProductoAlCarrito(DetalleCarritoRequestDTO dto) {

        CarritoEntity carritoEntity = carritoRepository.findById(dto.carritoId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(CARRITO_NO_ENCONTRADO_POR_ID + dto.carritoId()));

        ProductoEntity productoEntity = productoRepository.findById(dto.productoId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(PRODUCTO_NO_ENCONTRADO_POR_ID + dto.productoId()));

        DetalleCarritoEntity detalleCarritoEntity = detalleCarritoRepository
                .findByCarritoIdAndProductoId(dto.carritoId(), dto.productoId())
                .orElse(null);

        // Si existe el producto en el carrito, y queremos colocar nuevamente el mismo.
        if (detalleCarritoEntity != null) {
            // Sumamos la cantidad del producto anterior con la nueva cantidad ingresada
            int nuevaCantidadProducto = detalleCarritoEntity.getCantidad() + dto.cantidad();
            detalleCarritoEntity.setCantidad(nuevaCantidadProducto);

            // Calculamos el nuevo subtotal: precio unitario * cantidad
            detalleCarritoEntity.setSubtotal(productoEntity.getPrecio().multiply(BigDecimal.valueOf(nuevaCantidadProducto)));
        } else {
            // Si no existe el producto en el carrito lo agregamos
            detalleCarritoEntity = detalleCarritoMapper.toEntity(dto);
            detalleCarritoEntity.setProducto(productoEntity);
            detalleCarritoEntity.setCarrito(carritoEntity);

            // Calculamos el nuevo subtotal: precio unitario * cantidad
            detalleCarritoEntity.setSubtotal(productoEntity.getPrecio().multiply(BigDecimal.valueOf(dto.cantidad())));
        }

        detalleCarritoRepository.save(detalleCarritoEntity);

        return detalleCarritoMapper.toDto(detalleCarritoEntity);
    }

    @Override
    public DetalleCarritoResponseDTO actualizarProductoDelCarrito(Long detalleId, DetalleCarritoRequestDTO dto) {

        DetalleCarritoEntity detalleCarritoEntity = detalleCarritoRepository.findById(detalleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(DETALLE_CARRITO_NO_ENCONTRADO_POR_ID + detalleId));

        CarritoEntity carritoEntity = carritoRepository.findById(dto.carritoId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(CARRITO_NO_ENCONTRADO_POR_ID + dto.carritoId()));

        ProductoEntity productoEntity = productoRepository.findById(dto.productoId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(PRODUCTO_NO_ENCONTRADO_POR_ID + dto.productoId()));

        detalleCarritoMapper.updateEntityFromDto(dto, detalleCarritoEntity);

        detalleCarritoEntity.setSubtotal(
                productoEntity.getPrecio()
                        .multiply(BigDecimal.valueOf(dto.cantidad())));

        detalleCarritoRepository.save(detalleCarritoEntity);

        return detalleCarritoMapper.toDto(detalleCarritoEntity);
    }

    @Override
    public void eliminarProductoDelCarrito(Long detalleId) {
        DetalleCarritoEntity detalleCarritoEntity = detalleCarritoRepository.findById(detalleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(DETALLE_CARRITO_NO_ENCONTRADO_POR_ID + detalleId));

        detalleCarritoRepository.deleteById(detalleId);
    }

    @Override
    public List<DetalleCarritoResponseDTO> obtenerProductosDelCarrito(Long carritoId) {

        carritoRepository.findById(carritoId)
                .orElseThrow(()->
                        new ResourceNotFoundException(CARRITO_NO_ENCONTRADO_POR_ID + carritoId));

        List<DetalleCarritoEntity> productosCarrito = detalleCarritoRepository.findByCarritoId(carritoId);

        return productosCarrito.stream()
                .map(detalleCarritoMapper::toDto)
                .toList();
    }

}
