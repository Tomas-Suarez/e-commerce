package shop.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "direccion")
public class DireccionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1000)
    @Max(9999)
    private int codigo_postal;

    @NotBlank
    @Size(max = 120)
    private String calle;

    @Min(1)
    private int numero;

    private Integer piso;

    @NotBlank
    @Size(max = 126)
    private String referencia;

}
