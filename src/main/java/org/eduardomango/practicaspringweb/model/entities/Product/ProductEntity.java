package org.eduardomango.practicaspringweb.model.entities.Product;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProductEntity {
    //todo se usa notblank con tipos de datos que sean string/char?
    @NotNull(message = "No puede ser nulo el id")
    @Positive(message = "No puede ser negativo el id")
    @EqualsAndHashCode.Include
    private long id;

    @NotBlank(message = "No puede ir en blanco el nombre")
    @Size(min = 1, max = 30, message = "El nombre tiene que tener entre 1 y 30 caracteres")
    private String name;

    @NotNull(message = "No puede ser nulo el precio")
    @Positive(message = "El precio no puede ser negativo")
    private double price;

    @NotBlank(message = "No puede ir en blanco la descripcion")
    @Size(min = 1, max = 50, message = "La descripcion tiene que tener entre 1 y 50 caracteres")
    private String description;
}
