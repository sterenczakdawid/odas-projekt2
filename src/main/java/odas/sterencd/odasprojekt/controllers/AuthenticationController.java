package odas.sterencd.odasprojekt.controllers;

import lombok.RequiredArgsConstructor;
import odas.sterencd.odasprojekt.services.AuthenticationService;
import odas.sterencd.odasprojekt.utils.AuthenticationRequest;
import odas.sterencd.odasprojekt.utils.AuthenticationResponse;
import odas.sterencd.odasprojekt.utils.RegisterRequest;
import odas.sterencd.odasprojekt.utils.ServiceResponse;
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
    public ServiceResponse<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return new ServiceResponse<>(this.authenticationService.register(request),true,"User successfully registered");

    }
    @PostMapping("/authenticate")
    public ServiceResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return new ServiceResponse<>(this.authenticationService.authenticate(request),true,"User successfully authenticated");
    }
}
