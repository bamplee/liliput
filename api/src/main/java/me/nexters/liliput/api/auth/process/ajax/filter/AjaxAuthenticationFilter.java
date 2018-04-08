package me.nexters.liliput.api.auth.process.ajax.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.nexters.liliput.api.interfaces.v1.dto.request.V1SocialUserJoinRequest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

public class AjaxAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final ObjectMapper objectMapper;

    public AjaxAuthenticationFilter(RequestMatcher requestMatcher, ObjectMapper objectMapper) {
        super(requestMatcher);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
                                                                                                          AuthenticationException,
                                                                                                          IOException {
        if (isJson(request)) {
            V1SocialUserJoinRequest joinRequest = objectMapper.readValue(request.getReader(), V1SocialUserJoinRequest.class);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(joinRequest.getCode(), "");
            return getAuthenticationManager().authenticate(authentication);
        } else {
            throw new AccessDeniedException("Don't use content type for " + request.getContentType());
        }
    }

    private boolean isJson(HttpServletRequest request) {
        return MediaType.APPLICATION_JSON_VALUE.contains(request.getContentType());
    }
}
