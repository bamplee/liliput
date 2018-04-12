package me.nexters.liliput.api.infrastructure;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableJpaAuditing
public class InfrastructureConfig {
}
