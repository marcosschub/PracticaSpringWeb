package org.eduardomango.practicaspringweb.model.entities.User;

import jakarta.validation.constraints.Email;
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
public class UserEntity {
    @NotBlank(message = "No puede ir en blanco el id")
    @Positive( message = "No puede ser negativo el id")
    private long id;

    @NotBlank(message = "No puede ir en blanco el nombre")
    @Size(min = 1,max = 30,message = "El nombre tiene que tener entre 1 y 30 caracteres")
    private String username;

    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$",message = "No tiene formato de email")
    private String email;

    @NotBlank(message = "No puede ir en blanco la contraseña")
    @Size(min = 1,max = 12,message = "la contraseña tiene que tener entre 1 y 12 caracteres")
    private String password;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserEntity that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
