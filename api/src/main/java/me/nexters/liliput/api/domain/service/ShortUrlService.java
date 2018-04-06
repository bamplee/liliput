package me.nexters.liliput.api.domain.service;

import me.nexters.liliput.api.domain.dto.ShortUrlModel;

public interface ShortUrlService {
    ShortUrlModel createShortUrl(String path, String webUrl);

    ShortUrlModel updateShortUrl(String originalPath, String customPath, String webUrl);

    ShortUrlModel readShortUrl(String path);

    ShortUrlModel deleteShortUrl(String path);

    String getRouteUrl(String path);
}
