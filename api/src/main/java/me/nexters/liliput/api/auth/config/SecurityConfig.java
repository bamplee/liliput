package me.nexters.liliput.api.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.nexters.liliput.api.auth.process.BaseSecurityHandler;
import me.nexters.liliput.api.auth.process.ajax.AjaxAuthenticationProvider;
import me.nexters.liliput.api.auth.process.ajax.filter.AjaxAuthenticationFilter;
import me.nexters.liliput.api.auth.process.jwt.JwtAuthenticationProvider;
import me.nexters.liliput.api.auth.process.jwt.filter.JwtAuthenticationFilter;
import me.nexters.liliput.api.auth.process.jwt.matcher.SkipPathRequestMatcher;
import me.nexters.liliput.api.domain.service.SocialUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan("me.nexters.liliput.api")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtAuthenticationProvider jwtProvider;
    private final AjaxAuthenticationProvider ajaxProvider;
    private final BaseSecurityHandler securityHandler;
    private final ObjectMapper objectMapper;
    private final SocialUserService socialUserService;
    private static final String LOGIN_ENTRY_POINT = "/login";
    private static final String TOKEN_ENTRY_POINT = "/token";
    private static final String ERROR_ENTRY_POINT = "/error";
    private static final String SAMPLE_LOGIN_PAGE = "/sample/page";
    private static final String API_ENTRY_PAGE = "/api/v1/**";

    public SecurityConfig(JwtAuthenticationProvider jwtProvider,
                          AjaxAuthenticationProvider ajaxProvider,
                          BaseSecurityHandler securityHandler,
                          ObjectMapper objectMapper, SocialUserService socialUserService) {
        this.jwtProvider = jwtProvider;
        this.ajaxProvider = ajaxProvider;
        this.securityHandler = securityHandler;
        this.objectMapper = objectMapper;
        this.socialUserService = socialUserService;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
           .antMatchers(SAMPLE_LOGIN_PAGE)
           .antMatchers("/resources/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(ajaxProvider)
            .authenticationProvider(jwtProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .addFilterBefore(ajaxAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtAuthenticationFilter(), FilterSecurityInterceptor.class)
            .csrf()
            .disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(API_ENTRY_PAGE)
            .authenticated();
    }

    @Bean
    public AntPathRequestMatcher antPathRequestMatcher() {
        return new AntPathRequestMatcher(LOGIN_ENTRY_POINT, HttpMethod.POST.name());
    }

    @Bean
    public AjaxAuthenticationFilter ajaxAuthenticationFilter() throws Exception {
        AjaxAuthenticationFilter filter = new AjaxAuthenticationFilter(antPathRequestMatcher(), objectMapper, socialUserService);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(securityHandler);
        filter.setAuthenticationFailureHandler(securityHandler);
        return filter;
    }

    @Bean
    public SkipPathRequestMatcher skipPathRequestMatcher() {
        return new SkipPathRequestMatcher(Arrays.asList(LOGIN_ENTRY_POINT, TOKEN_ENTRY_POINT, ERROR_ENTRY_POINT, SAMPLE_LOGIN_PAGE));
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(skipPathRequestMatcher());
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationFailureHandler(securityHandler);
        return filter;
    }
}