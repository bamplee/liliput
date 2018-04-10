package me.nexters.liliput.api.auth.process.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;

import java.io.UnsupportedEncodingException;

public class JwtInfo {
    public static final String HEADER_NAME = "jwt-header";
    public static final String ISSUER = "liliput";
    @Value("${spring.social.facebook.appId}")
    public static final String TOKEN_KEY = "test";
    public static final long EXPIRES_LIMIT = 3L;

    public static Algorithm getAlgorithm() {
        try {
            return Algorithm.HMAC256(JwtInfo.TOKEN_KEY);
        } catch (IllegalArgumentException | UnsupportedEncodingException e) {
            return Algorithm.none();
        }
    }
}
