package odas.sterencd.odasprojekt.utils.bruteforce;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import odas.sterencd.odasprojekt.services.LoginAttemptService;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@RequiredArgsConstructor
public class AuthenticationFailureListener {

    private final HttpServletRequest request;
    private final LoginAttemptService loginAttemptService;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationFailureListener.class);

    @EventListener
    public void onAuthenticationFailure(AuthenticationFailureBadCredentialsEvent event) {
        final String xFHeader = request.getHeader("X-Forwarded-For");
        String clientIpAddress = (xFHeader == null || xFHeader.equals("") || !xFHeader.contains(request.getRemoteAddr()))
                ? request.getRemoteAddr()
                : xFHeader.split(",")[0];

        logger.error("Authentication failure for IP: {}", clientIpAddress);
        if (xFHeader == null || xFHeader.equals("") || !xFHeader.contains(request.getRemoteAddr())) {
            loginAttemptService.registerFailedLogin(request.getRemoteAddr());
        } else {
            loginAttemptService.registerFailedLogin(xFHeader.split(",")[0]);
        }
    }
}
