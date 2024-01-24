package odas.sterencd.odasprojekt.configuration;

import lombok.RequiredArgsConstructor;
import odas.sterencd.odasprojekt.dtos.NoteDTO;
import odas.sterencd.odasprojekt.dtos.RegisterRequest;
import odas.sterencd.odasprojekt.repositories.UserRepository;
import odas.sterencd.odasprojekt.services.AuthenticationService;
import odas.sterencd.odasprojekt.services.NoteService;
import odas.sterencd.odasprojekt.utils.bruteforce.AuthenticationFailureListener;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static odas.sterencd.odasprojekt.models.Role.ADMIN;
import static odas.sterencd.odasprojekt.models.Role.USER;

@Configuration
@RequiredArgsConstructor
public class AppConfiguration {
    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        try {
            return  config.getAuthenticationManager();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner commandLineRunner(NoteService noteService, AuthenticationService authenticationService) {
        return args -> {
            RegisterRequest adminUser = RegisterRequest.builder()
                    .name("admin")
                    .email("admin@admin.pl")
                    .password("Admin123!")
                    .role(ADMIN)
                    .mfaEnabled(false)
                    .build();
            authenticationService.register(adminUser);

            RegisterRequest user = RegisterRequest.builder()
                    .name("Dawid")
                    .email("dawid@mail.pl")
                    .password("Dawid123!")
                    .role(USER)
                    .mfaEnabled(true)
                    .build();
            authenticationService.register(user);

            NoteDTO note = NoteDTO.builder().title("Przykładowa notatka publiczna").content("## Notatka publiczna").isEncrypted(false).isPublic(true).build();
            noteService.addNote(note, adminUser.getEmail());
            NoteDTO note2 = NoteDTO.builder().title("Szyfrowana").content("*Ta notatka jest zaszyfrowana*").isEncrypted(true).password("123").isPublic(false).build();
            noteService.addNote(note2, adminUser.getEmail());
            NoteDTO note3 = NoteDTO.builder().title("Publiczna 2").content("**Druga publiczna**").isEncrypted(false).password("").isPublic(true).build();
            noteService.addNote(note3, adminUser.getEmail());
        };
    }

}
