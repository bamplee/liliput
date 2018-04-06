package me.nexters.liliput.api.interfaces.v1;

import me.nexters.liliput.api.domain.service.ShortUrlService;
import me.nexters.liliput.api.interfaces.v1.dto.request.V1ShortUrlDeleteRequest;
import me.nexters.liliput.api.interfaces.v1.dto.request.V1ShortUrlUpdateRequest;
import me.nexters.liliput.api.interfaces.v1.dto.request.V1ShortUrlCreateRequest;
import me.nexters.liliput.api.interfaces.v1.dto.response.V1ShortUrlResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/urls")
public class V1ShortUrlController {
    private final ShortUrlService shortUrlService;

    public V1ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @PostMapping
    public V1ShortUrlResponse createShortUrl(@RequestBody V1ShortUrlCreateRequest request) {
        return new V1ShortUrlResponse(shortUrlService.createShortUrl(request.getPath(), request.getWebUrl()));
    }

    @GetMapping(value = "/{path}")
    public V1ShortUrlResponse readShortUrl(@PathVariable String path) {
        return new V1ShortUrlResponse(shortUrlService.readShortUrl(path));
    }

    @PutMapping
    public V1ShortUrlResponse updateShortUrl(@RequestBody V1ShortUrlUpdateRequest request) {
        return new V1ShortUrlResponse(shortUrlService.updateShortUrl(request.getPath(),
                                                                     request.getCustomPath(),
                                                                     request.getWebUrl()));
    }

    @DeleteMapping
    public V1ShortUrlResponse deleteShortUrl(@RequestBody V1ShortUrlDeleteRequest request) {
        return new V1ShortUrlResponse(shortUrlService.deleteShortUrl(request.getPath()));
    }
}
