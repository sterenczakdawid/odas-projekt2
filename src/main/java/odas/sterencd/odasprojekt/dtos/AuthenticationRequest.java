package odas.sterencd.odasprojekt.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @NotNull
    @NotEmpty
    @NotBlank
    @Length(min = 3, max = 64)
    private String email;

    @NotNull
    @NotEmpty
    @NotBlank
    @Length(min=8, max = 32)
    private String password;
}
