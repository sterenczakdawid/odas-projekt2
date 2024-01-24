package odas.sterencd.odasprojekt.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import odas.sterencd.odasprojekt.models.Role;
import odas.sterencd.odasprojekt.utils.validators.EmailValid;
import odas.sterencd.odasprojekt.utils.validators.NameValid;
import odas.sterencd.odasprojekt.utils.validators.PasswordValid;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotNull
    @NotEmpty
    @NameValid
    private String name;

    @NotNull
    @NotEmpty
    @EmailValid
    private String email;

    @NotNull
    @NotEmpty
    @PasswordValid
    private String password;

    private Role role;
    private boolean mfaEnabled;
}
