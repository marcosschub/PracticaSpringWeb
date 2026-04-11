package org.eduardomango.practicaspringweb.model.entities.Sale;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.eduardomango.practicaspringweb.model.entities.Product.ProductEntity;
import org.eduardomango.practicaspringweb.model.entities.User.UserEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SaleEntity {
    @NotBlank(message = "No puede ir en blanco el id")
    @Positive( message = "No puede ser negativo el id")
    private Long id;
    private ProductEntity products;
    @NotBlank(message = "No puede ir en blanco la cantidad")
    @Positive(message = "La cantidad no puede ser negativa")
    private Long quantity;
    private UserEntity client;
    private LocalDate saleDate;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SaleEntity that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
