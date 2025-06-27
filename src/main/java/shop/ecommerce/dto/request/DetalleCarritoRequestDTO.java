package shop.ecommerce.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DetalleCarritoRequestDTO(
        @Min(1)
        int cantidad,

        @NotNull
        BigDecimal subtotal,

        @NotNull
        Long carritoId,

        @NotNull
        Long productoId
) {

}
