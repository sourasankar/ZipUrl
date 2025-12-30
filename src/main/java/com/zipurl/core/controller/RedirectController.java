package com.zipurl.core.controller;

import com.zipurl.core.service.RedisReadService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class RedirectController {

    private final RedisReadService redisReadService;

    @GetMapping("/{shortUrlId}")
    public void redirectToLongUrl(@PathVariable("shortUrlId") String shortUrlId, HttpServletResponse response) throws IOException {
        String longUrl = redisReadService.getCachedUrl(shortUrlId);
        if (longUrl != null) {
            response.sendRedirect(longUrl);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
