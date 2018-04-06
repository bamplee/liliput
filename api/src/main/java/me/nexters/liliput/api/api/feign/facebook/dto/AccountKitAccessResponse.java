package me.nexters.liliput.api.api.feign.facebook.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccountKitAccessResponse {
    private long id;
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_refresh_interval")
    private long tokenRefreshInterval;
}
