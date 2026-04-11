package org.eduardomango.practicaspringweb.model.entities.Sale;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SalesDTO {
    @NotBlank(message = "No puede ir en blanco el id de venta")
    @Positive( message = "No puede ser negativo el id de venta ")
    private long idSale;
    @NotBlank(message = "No puede ir en blanco el id de producto")
    @Positive( message = "No puede ser negativo el id de producto")
    private long idProduct;
    @NotBlank(message = "No puede ir en blanco el id de producto")
    @Positive( message = "No puede ser negativo el id de producto")
    private long idClient;
    @NotBlank(message = "No puede ir en blanco la cantidad")
    @Positive(message = "La cantidad no puede ser negativa")
    private long quantity;
}
