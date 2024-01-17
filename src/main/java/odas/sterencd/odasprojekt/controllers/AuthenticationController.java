package odas.sterencd.odasprojekt.controllers;

import lombok.RequiredArgsConstructor;
import odas.sterencd.odasprojekt.dtos.VerificationRequest;
import odas.sterencd.odasprojekt.services.AuthenticationService;
import odas.sterencd.odasprojekt.dtos.AuthenticationRequest;
import odas.sterencd.odasprojekt.dtos.AuthenticationResponse;
import odas.sterencd.odasprojekt.dtos.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        var response = authenticationService.register(request);
        if(request.isMfaEnabled()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.accepted().build();

    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(@RequestBody VerificationRequest verificationRequest) {
        return ResponseEntity.ok(authenticationService.verifyCode(verificationRequest));
    }
}
