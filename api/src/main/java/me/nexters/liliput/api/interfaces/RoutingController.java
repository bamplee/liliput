package me.nexters.liliput.api.interfaces;

import me.nexters.liliput.api.domain.shorten.service.ShortUrlService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RoutingController {
    private final static String REDIRECT_PREFIX = "redirect:";
    private final ShortUrlService shortUrlService;

    public RoutingController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @GetMapping("/{path}")
    public String route(@PathVariable String path) {
        return StringUtils.join(REDIRECT_PREFIX, shortUrlService.getRouteUrl(path));
    }

    private String getRequestUrl(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getRequestURL()
                                 .toString()
                                 .replace(httpServletRequest.getRequestURI(), StringUtils.EMPTY);
    }
}
