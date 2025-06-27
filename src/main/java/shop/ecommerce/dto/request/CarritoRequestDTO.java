package shop.ecommerce.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CarritoRequestDTO(
        @NotNull
        LocalDate fecha,

        boolean activo,

        @NotNull
        Long clienteId
) {
}
