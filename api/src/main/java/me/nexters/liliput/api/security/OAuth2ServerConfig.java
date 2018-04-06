package me.nexters.liliput.api.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
public class OAuth2ServerConfig {
    @Configuration
    @EnableResourceServer
    protected class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
        @Value("${resource.id:spring-boot-application}")
        private String resourceId;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(resourceId);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                .antMatchers("/api/v1/urls/**")
                .hasRole("USER");
        }
    }

    @Configuration
    @RequiredArgsConstructor
    @EnableAuthorizationServer
    public class JwtOAuth2AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
        @Value("${resource.id:spring-boot-application}")
        private String resourceId;
        @Value("${access_token.validity_period:3600}")
        private int accessTokenValiditySeconds = 3600;
        private final AuthenticationManager authenticationManager;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
            endpoints.accessTokenConverter(jwtAccessTokenConverter())
                     .authenticationManager(this.authenticationManager);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                   .withClient("bar")
                   .authorizedGrantTypes("password")
                   .authorities("ROLE_USER")
                   .scopes("read", "write")
                   .resourceIds(resourceId)
                   .accessTokenValiditySeconds(accessTokenValiditySeconds)
                   .secret("foo");
        }
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        return new JwtAccessTokenConverter();
    }
}