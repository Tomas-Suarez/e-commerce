package pasteleria.LePettit.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CarritoResponseDTO {

    private Long id;
    private LocalDate fecha;
    private boolean activo;

    private ClienteResponseDTO cliente;

    private List<DetalleCarritoResponseDTO> productos;
}
