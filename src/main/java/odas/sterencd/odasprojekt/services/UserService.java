package odas.sterencd.odasprojekt.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import odas.sterencd.odasprojekt.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final LoginAttemptService loginAttemptService;

    @Override
    public UserDetails loadUserByUsername(String email) throws EntityNotFoundException {
        if( loginAttemptService.isBlocked()){
            throw new RuntimeException("Your IP has been blocked - too many failed login attempts!");
        }
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("There is no user with the email: " + email));
    }
}
