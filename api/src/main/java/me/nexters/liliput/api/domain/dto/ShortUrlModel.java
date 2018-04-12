package me.nexters.liliput.api.domain.dto;

import lombok.Data;

@Data
public class ShortUrlModel {
    private String userId;
    private String path;
    private String webUrl;
    private String createdDate;
}
