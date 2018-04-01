package me.nexters.liliput.api.interfaces.v1.dto.request;

import lombok.Data;

@Data
public class V1ShortUrlUpdateRequest {
    private String path;
    private String customPath;
    private String webUrl;
}
