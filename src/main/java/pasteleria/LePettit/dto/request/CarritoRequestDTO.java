package pasteleria.LePettit.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CarritoRequestDTO {

    @NotNull
    private LocalDate fecha;

    private boolean activo;

    @NotNull
    private Long clienteId;

}
