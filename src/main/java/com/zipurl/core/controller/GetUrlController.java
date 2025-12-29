package com.zipurl.core.controller;

import com.zipurl.core.constants.Constants;
import com.zipurl.core.dto.ShortUrlResponse;
import com.zipurl.core.service.RedisReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class GetUrlController implements ControllerHandler {

    private final RedisReadService redisReadService;

    @GetMapping("/get/{shortUrlKey}")
    public ResponseEntity<ShortUrlResponse> getLongUrl(@PathVariable("shortUrlKey") String shortUrlKey) {
        ShortUrlResponse response = new ShortUrlResponse();
        response.setStatus(Constants.FAILURE);

        String longUrl = redisReadService.getCachedUrl(shortUrlKey);
        if (longUrl != null) {
            response.setShortUrl(longUrl);
            response.setStatus(Constants.SUCCESS);
        } else {
            response.setMessage("Corresponding long url not found");
        }

        return ResponseEntity.ok().body(response);
    }
}
