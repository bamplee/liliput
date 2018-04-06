package me.nexters.liliput.api.domain.route.service;

import me.nexters.liliput.api.domain.route.repository.RouteLog;
import me.nexters.liliput.api.domain.route.repository.RouteLogRepository;
import me.nexters.liliput.api.domain.shorten.repository.ShortUrl;
import me.nexters.liliput.api.domain.shorten.repository.ShortUrlRepository;
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
