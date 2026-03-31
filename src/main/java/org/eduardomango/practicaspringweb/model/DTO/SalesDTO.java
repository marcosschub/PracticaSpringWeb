package org.eduardomango.practicaspringweb.model.DTO;

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
    private long idSale;
    private long idProduct;
    private long idClient;
    private long quantity;
}
