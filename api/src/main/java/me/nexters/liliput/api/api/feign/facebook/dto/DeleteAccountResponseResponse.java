package me.nexters.liliput.api.api.feign.facebook.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeleteAccountResponseResponse {
    @JsonProperty("success")
    private boolean removed;
}
