package me.nexters.liliput.api;

import me.nexters.liliput.api.common.util.ShortUrlBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@SpringBootApplication
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean
    public ShortUrlBuilder shortUrlBuilder() {
        return new ShortUrlBuilder();
    }
}
