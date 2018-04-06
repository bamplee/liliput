package me.nexters.liliput.api.domain.service;

import me.nexters.liliput.api.application.common.util.ShortUrlBuilder;
import me.nexters.liliput.api.domain.dto.ShortUrlModel;
import me.nexters.liliput.api.infrastructure.persistence.jpa.entity.ShortUrl;
import me.nexters.liliput.api.infrastructure.persistence.jpa.repository.ShortUrlRepository;
import me.nexters.liliput.api.interfaces.support.NoResultFoundException;
import me.nexters.liliput.api.domain.exception.UrlShorteningFailureException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {
    private final static Integer MAX_LENGTH = 8;
    private final static String MAIN_URL = "http://nexters.me";
    private final ShortUrlBuilder shortUrlBuilder;
    private final ShortUrlRepository shortUrlRepository;

    public ShortUrlServiceImpl(ShortUrlBuilder shortUrlBuilder, ShortUrlRepository shortUrlRepository) {
        this.shortUrlBuilder = shortUrlBuilder;
        this.shortUrlRepository = shortUrlRepository;
    }

    @Override
    public ShortUrlModel createShortUrl(String path, String webUrl) {
        String shortPath = StringUtils.isEmpty(path) ? shortUrlBuilder.shorten(MAX_LENGTH) : path;
        if (existsPath(shortPath)) {
            if (StringUtils.isEmpty(path)) {
                // Todo 랜덤으로 생성한 Path가 중복일 경우에는 따로 처리가 필요함
                throw new UrlShorteningFailureException.PathIsAlreadyInUseException();
            }
            throw new UrlShorteningFailureException.CustomPathIsAlreadyInUseException();
        }
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setPath(shortPath);
        shortUrl.setWebUrl(webUrl);
        shortUrl.setIsActive(Boolean.TRUE);
        return transform(shortUrlRepository.save(shortUrl));
    }

    @Override
    public ShortUrlModel updateShortUrl(String path, String customPath, String webUrl) {
        if (!existsPath(path)) {
            throw new NoResultFoundException();
        }
        ShortUrl shortUrlResult = shortUrlRepository.findByPath(path);
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setId(shortUrlResult.getId());
        shortUrl.setPath(StringUtils.isEmpty(customPath) ? shortUrlResult.getPath() : customPath);
        shortUrl.setWebUrl(StringUtils.isEmpty(webUrl) ? shortUrlResult.getWebUrl() : webUrl);
        shortUrl.setIsActive(shortUrlResult.getIsActive());
        return transform(shortUrlRepository.save(shortUrl));
    }

    @Override
    public ShortUrlModel readShortUrl(String path) {
        if (!existsPath(path)) {
            throw new NoResultFoundException();
        }
        return transform(shortUrlRepository.findByPath(path));
    }

    @Override
    public ShortUrlModel deleteShortUrl(String path) {
        if (!existsPath(path)) {
            throw new NoResultFoundException();
        }
        ShortUrl shortUrlResult = shortUrlRepository.findByPath(path);
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setId(shortUrlResult.getId());
        shortUrl.setPath(shortUrlResult.getPath());
        shortUrl.setWebUrl(shortUrlResult.getWebUrl());
        shortUrl.setIsActive(Boolean.FALSE);
        return transform(shortUrlRepository.save(shortUrl));
    }

    @Override
    public String getRouteUrl(String path) {
        ShortUrl shortUrl = shortUrlRepository.findByPath(path);
        return Objects.isNull(shortUrl) ? MAIN_URL : shortUrl.getWebUrl();
    }

    private Boolean existsPath(String path) {
        ShortUrl shortUrl = shortUrlRepository.findByPath(path);
        return Objects.isNull(shortUrl) ? Boolean.FALSE : shortUrl.getIsActive();
    }

    private ShortUrlModel transform(ShortUrl shortUrl) {
        ShortUrlModel shortUrlModel = new ShortUrlModel();
        shortUrlModel.setPath(shortUrl.getPath());
        shortUrlModel.setWebUrl(shortUrl.getWebUrl());
        return shortUrlModel;
    }
}
