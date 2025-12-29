package com.zipurl.core.controller;

import com.zipurl.core.constants.Constants;
import com.zipurl.core.dto.CreateUrlRequest;
import com.zipurl.core.dto.ShortUrlResponse;
import com.zipurl.core.service.ShortUrlGeneratorService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CreateUrlController implements ControllerHandler {

    private final ShortUrlGeneratorService shortUrlGeneratorService;

    @RateLimiter(name = "createShortUrlLimiter", fallbackMethod = "createUrlLimited")
    @PostMapping("/create")
    public ResponseEntity<ShortUrlResponse> createUrl(@RequestBody(required = false) CreateUrlRequest req) {
        ShortUrlResponse response = new ShortUrlResponse();
        response.setStatus(Constants.FAILURE);
        try {

            if (req != null && req.getUrl() != null && !req.getUrl().isBlank()) {
                String shortUrl = shortUrlGeneratorService.generateShortUrl(req.getUrl());
                if (shortUrl != null) {
                    response.setStatus(Constants.SUCCESS);
                    response.setShortUrl(shortUrl);
                } else {
                    response.setMessage("Something went wrong");
                }
            } else {
                return ResponseEntity.unprocessableContent().body(response);
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.internalServerError().body(response);
        }
        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity<ShortUrlResponse> createUrlLimited(CreateUrlRequest req, Throwable ex) {
        ShortUrlResponse response = new ShortUrlResponse();
        response.setStatus(Constants.FAILURE);
        response.setMessage("Too many requests");
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(response);
    }
}
