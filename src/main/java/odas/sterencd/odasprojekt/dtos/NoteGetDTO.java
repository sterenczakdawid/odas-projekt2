package odas.sterencd.odasprojekt.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoteGetDTO {
    @NotNull
    private Integer id;
    private String password;
}
