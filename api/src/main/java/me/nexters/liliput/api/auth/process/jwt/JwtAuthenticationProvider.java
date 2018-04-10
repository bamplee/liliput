package me.nexters.liliput.api.auth.process.jwt;

import me.nexters.liliput.api.auth.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtUserDetailsService userDetailsService;

    public JwtAuthenticationProvider(JwtUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            throw new BadCredentialsException("Bad token");
        }
        String token = authentication.getCredentials()
                                     .toString();
        if (JwtUtil.verify(token)) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(token);
            return new JwtAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("Bad token");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
