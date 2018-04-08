package me.nexters.liliput.api;

import me.nexters.liliput.api.api.ApiConfig;
import me.nexters.liliput.api.application.ApplicationConfig;
import me.nexters.liliput.api.domain.DomainConfig;
import me.nexters.liliput.api.infrastructure.InfrastructureConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@Import({
            ApiConfig.class,
            ApplicationConfig.class,
            DomainConfig.class,
            InfrastructureConfig.class
        })
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
