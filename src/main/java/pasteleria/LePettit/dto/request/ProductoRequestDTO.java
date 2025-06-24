package pasteleria.LePettit.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductoRequestDTO {

    @NotBlank
    @Size(max = 50)
    private String nombre;

    @NotNull
    private float precio;

    @NotBlank
    @Size(max = 128)
    private String descripcion;

    private Long categoriaId;

}
