package me.nexters.liliput.api.domain.service;

import me.nexters.liliput.api.domain.dto.ShortUrlModel;

import java.util.List;

public interface ShortUrlService {
    ShortUrlModel createShortUrl(String userId, String path, String webUrl);

    ShortUrlModel updateShortUrl(String userId, String originalPath, String customPath, String webUrl);

    ShortUrlModel readShortUrl(String path);

    ShortUrlModel deleteShortUrl(String userId, String path);

    String getRouteUrl(String path);

    List<ShortUrlModel> readShortUrls(String userId);
}
