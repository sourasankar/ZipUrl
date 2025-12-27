package com.zipurl.core.controller;

import com.zipurl.core.dto.GetUrlRequest;
import com.zipurl.core.dto.ShortUrlResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetUrlController implements ControllerHandler {

    @GetMapping
    public ResponseEntity<ShortUrlResponse> getLongUrl(@RequestBody(required = false) GetUrlRequest req) {
        ShortUrlResponse response = new ShortUrlResponse();
        response.setStatus("Failure");
        return null;
    }
}
