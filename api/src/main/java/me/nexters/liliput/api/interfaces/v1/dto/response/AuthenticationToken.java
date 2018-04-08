package me.nexters.liliput.api.interfaces.v1.dto.response;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gunnar Hillert
 * @since 1.0
 */
public class AuthenticationToken {
    private String username;
    private Map<String, Boolean> roles;
    private String token;

    public AuthenticationToken() {
    }

    public AuthenticationToken(String username, Map<String, Boolean> roles, String token) {
        this.roles = new ConcurrentHashMap<String, Boolean>(roles);
        this.token = token;
        this.username = username;
    }

    public Map<String, Boolean> getRoles() {
        return this.roles;
    }

    public String getToken() {
        return this.token;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
}