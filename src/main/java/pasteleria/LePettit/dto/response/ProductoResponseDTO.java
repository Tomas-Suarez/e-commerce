package pasteleria.LePettit.dto.response;

import lombok.Data;

@Data
public class ProductoResponseDTO {

    private Long id;
    private String nombre;
    private float precio;
    private String descripcion;
    private String categoriaNombre;
}
