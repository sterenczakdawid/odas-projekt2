package odas.sterencd.odasprojekt.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import odas.sterencd.odasprojekt.dtos.VerificationRequest;
import odas.sterencd.odasprojekt.models.Role;
import odas.sterencd.odasprojekt.models.User;
import odas.sterencd.odasprojekt.repositories.UserRepository;
import odas.sterencd.odasprojekt.dtos.AuthenticationRequest;
import odas.sterencd.odasprojekt.dtos.AuthenticationResponse;
import odas.sterencd.odasprojekt.dtos.RegisterRequest;
import odas.sterencd.odasprojekt.utils.Delay;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TwoFactorAuthenticationService tfaService;
    private final LoginAttemptService loginAttemptService;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() == null ? Role.USER : request.getRole())
                .mfaEnabled(request.isMfaEnabled())
                .build();

        // if mfa enabled --> generate secret
        if (request.isMfaEnabled()) {
            user.setSecret(tfaService.generateNewSecret());
        }
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        Delay.delay();
        return AuthenticationResponse.builder()
                .secretImageUri(tfaService.generateQrCodeImageUri(user.getSecret()))
                .accessToken(jwtToken)
                .mfaEnabled(user.isMfaEnabled())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = (User) loadUserByUsername(request.getEmail());
        Delay.delay();
        if(user.isMfaEnabled()) {
            return AuthenticationResponse.builder()
                    .accessToken("")
                    .mfaEnabled(true)
                    .build();
        }
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .mfaEnabled(false)
                .build();
    }

    private UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if( loginAttemptService.isBlocked()){
            throw new RuntimeException("IP blocked for a while");
        }
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Incorrect authorization data"));
    }

    public AuthenticationResponse verifyCode(VerificationRequest verificationRequest) {
        User user = userRepository
                .findByEmail(verificationRequest.getEmail())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("No user found with email %S", verificationRequest.getEmail())
                ));
        if(tfaService.isOtpNotValid(user.getSecret(), verificationRequest.getCode())) {
            throw new BadCredentialsException("Code is not correct");
        }
        var jwtToken = jwtService.generateToken(user);
        Delay.delay();
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .mfaEnabled(user.isMfaEnabled())
                .build();
    }
}
