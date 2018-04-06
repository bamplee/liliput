package me.nexters.liliput.api.interfaces;

import me.nexters.liliput.api.domain.service.RouteLogService;
import me.nexters.liliput.api.domain.service.ShortUrlService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RoutingController {
    private final static String REDIRECT_PREFIX = "redirect:";
    private final ShortUrlService shortUrlService;
    private final RouteLogService routeLogService;

    public RoutingController(ShortUrlService shortUrlService, RouteLogService routeLogService) {
        this.shortUrlService = shortUrlService;
        this.routeLogService = routeLogService;
    }

    @GetMapping("/{path}")
    public String route(@PathVariable String path, HttpServletRequest request) {
        logging(path, request);
        return StringUtils.join(REDIRECT_PREFIX, shortUrlService.getRouteUrl(path));
    }

    private void logging(String path, HttpServletRequest request) {
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        String referer = request.getHeader(HttpHeaders.REFERER);
        String remoteAddr = request.getRemoteAddr();
        routeLogService.logging(path, userAgent, referer, remoteAddr);
    }
}
