package org.eduardomango.practicaspringweb.model.entities.User;

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
public class UserEntity {
    @NotNull(message = "No puede ser nulo el id")
    @Positive(message = "No puede ser negativo el id")
    @EqualsAndHashCode.Include
    private long id;

    @NotBlank(message = "No puede ir en blanco el nombre")
    @Size(min = 1, max = 30, message = "El nombre tiene que tener entre 1 y 30 caracteres")
    private String username;

    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "No tiene formato de email")
    private String email;

    @NotBlank(message = "No puede ir en blanco la contraseña")
    @Size(min = 1, max = 12, message = "la contraseña tiene que tener entre 1 y 12 caracteres")
    private String password;
}
