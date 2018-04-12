package me.nexters.liliput.api.auth.process;

import me.nexters.liliput.api.auth.process.jwt.JwtInfo;
import me.nexters.liliput.api.auth.util.JwtUtil;
import org.apache.http.client.HttpResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Component
public class BaseSecurityHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        UserDetails userDetails = new UserDetailsImpl(user.getUsername(), new ArrayList<>(authentication.getAuthorities()));
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setHeader(JwtInfo.HEADER_NAME, JwtUtil.createToken(userDetails));
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws HttpResponseException {
        throw new HttpResponseException(HttpStatus.FORBIDDEN.value(), exception.getMessage());
    }
}
