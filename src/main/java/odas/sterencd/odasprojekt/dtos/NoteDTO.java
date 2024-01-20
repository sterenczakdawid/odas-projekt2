package odas.sterencd.odasprojekt.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class NoteDTO {
    @NotNull
    @NotBlank
    @Length( max = 64, message = "Tytuł nie może mieć więcej niż 64 znaki")
    private String title;

    @NotNull
    @NotEmpty
    private String content;

    private String username;
    private String password;
    private Boolean isPublic;
    private Boolean isEncrypted;
}
