package me.nexters.liliput.api.auth;

import me.nexters.liliput.api.auth.config.CommonConfig;
import me.nexters.liliput.api.auth.config.SecurityConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@Import({
            CommonConfig.class,
            SecurityConfig.class
        })
public class AuthConfig {
}
