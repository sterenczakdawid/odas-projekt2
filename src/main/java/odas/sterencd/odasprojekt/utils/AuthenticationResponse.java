package odas.sterencd.odasprojekt.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// if user didnt enable 2fa --> secret empty --> client wont see attribute secret image uri
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AuthenticationResponse {
    private String accessToken;
    private boolean mfaEnabled;
    private String secretImageUri;
}
