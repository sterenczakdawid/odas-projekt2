package odas.sterencd.odasprojekt.services;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import odas.sterencd.odasprojekt.utils.bruteforce.AuthenticationFailureListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Slf4j
@Service
public class LoginAttemptService {

    private final static int MAX_ATTEMPTS = 5;
    private final LoadingCache<String, Integer> attemptsCache;

    private final Logger logger = LoggerFactory.getLogger(AuthenticationFailureListener.class);


    @Autowired
    private HttpServletRequest request;

    public LoginAttemptService() {
        attemptsCache = CacheBuilder.newBuilder().expireAfterWrite(Duration.ofMinutes(5)).build(new CacheLoader<String, Integer>() {
            @Override
            public Integer load(String key) throws Exception {
                return 0;
            }
        });
    }

    public void registerFailedLogin(String address) {
        int attempts;
        try {
            attempts = attemptsCache.get(address);
        } catch ( Exception e){
            attempts = 0;
        }
        attempts++;
        logger.info("zarejestrowalem probe nr" + attempts);
        attemptsCache.put(address,attempts);
    }

    public boolean isBlocked() {
        try {
            return attemptsCache.get(getClientIP()) >= MAX_ATTEMPTS;
        } catch (final Exception e) {
            return false;
        }
    }

    private String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(request.getRemoteAddr())) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
