package shop.ecommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "promocion_categoria")
public class PromocionCategoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_promocion", nullable = false)
    private PromocionEntity promocion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_producto", nullable = false)
    private CategoriaEntity categoria;
}
