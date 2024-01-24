package odas.sterencd.odasprojekt.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Akceptuj wszystkie ścieżki
                        .allowedOrigins("*") // Akceptuj żądania z dowolnej strony
                        .allowedMethods("*") // Akceptuj dowolne metody HTTP
                        .allowedHeaders("*"); // Akceptuj dowolne nagłówki HTTP
            }
        };
    }
}
