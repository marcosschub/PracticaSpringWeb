package org.eduardomango.practicaspringweb.model.entities.Sale;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.eduardomango.practicaspringweb.model.entities.Product.ProductEntity;
import org.eduardomango.practicaspringweb.model.entities.User.UserEntity;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SaleEntity {
    @NotNull(message = "No puede ser nulo el id")
    @Positive(message = "No puede ser negativo el id")
    @EqualsAndHashCode.Include
    private long id;

    private ProductEntity products;

    @NotNull(message = "No puede ser nula la cantidad")
    @Positive(message = "La cantidad no puede ser negativa")
    private Long quantity;

    private UserEntity client;

    private LocalDate saleDate;
}
