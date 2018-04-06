package me.nexters.liliput.api.domain.service;

public interface RouteLogService {
    void logging(String path, String referer, String userAgent, String remoteAddress);
}
