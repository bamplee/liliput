package me.nexters.liliput.api.domain.service;

import me.nexters.liliput.api.infrastructure.persistence.jpa.entity.RouteLog;
import me.nexters.liliput.api.infrastructure.persistence.jpa.repository.RouteLogRepository;
import me.nexters.liliput.api.infrastructure.persistence.jpa.entity.ShortUrl;
import me.nexters.liliput.api.infrastructure.persistence.jpa.repository.ShortUrlRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RouteLogServiceImpl implements RouteLogService {
    private final ShortUrlRepository shortUrlRepository;
    private final RouteLogRepository routeLogRepository;

    public RouteLogServiceImpl(ShortUrlRepository shortUrlRepository,
                               RouteLogRepository routeLogRepository) {
        this.shortUrlRepository = shortUrlRepository;
        this.routeLogRepository = routeLogRepository;
    }

    @Override
    public void logging(String path, String referer, String userAgent, String remoteAddress) {
        ShortUrl shortUrl = shortUrlRepository.findByPath(path);
        if (Objects.isNull(shortUrl)) {
            return;
        }
        RouteLog routeLog = new RouteLog();
        routeLog.setShortUrl(shortUrl);
        routeLog.setReferer(referer);
        routeLog.setUserAgent(userAgent);
        routeLog.setRemoteAddress(remoteAddress);
    }
}
