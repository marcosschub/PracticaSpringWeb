package org.eduardomango.practicaspringweb.model.entities.Product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProductEntity {
    @NotBlank(message = "No puede ir en blanco el id")
    @Positive( message = "No puede ser negativo el id")
    private long id;
    @NotBlank(message = "No puede ir en blanco el nombre")
    @Size(min = 1,max = 30,message = "El nombre tiene que tener entre 1 y 30 caracteres")
    private String name;
    @NotBlank(message = "No puede ir en blanco el precio")
    @Positive(message = "El precio no puede ser negativo")
    private double price;
    @NotBlank(message = "No puede ir en blanco la descripcion")
    @Size(min = 1,max = 50,message = "La descripcion tiene que tener entre 1 y 50 caracteres")
    private String description;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProductEntity that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
