package odas.sterencd.odasprojekt.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import odas.sterencd.odasprojekt.models.Role;
import odas.sterencd.odasprojekt.utils.validators.PasswordValid;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotNull
    @NotEmpty
    @Length(min = 4, max = 32)
    private String name;

    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    @PasswordValid
    private String password;

    private Role role;
    private boolean mfaEnabled;
}
