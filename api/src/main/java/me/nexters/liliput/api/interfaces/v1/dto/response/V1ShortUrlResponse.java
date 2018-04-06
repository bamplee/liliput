package me.nexters.liliput.api.interfaces.v1.dto.response;

import lombok.Data;
import me.nexters.liliput.api.domain.dto.ShortUrlModel;

@Data
public class V1ShortUrlResponse {
    private String path;
    private String webUrl;

    public V1ShortUrlResponse(ShortUrlModel shortUrlModel) {
        this.path = shortUrlModel.getPath();
        this.webUrl = shortUrlModel.getWebUrl();
    }
}
