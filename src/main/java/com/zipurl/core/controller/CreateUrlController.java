package com.zipurl.core.controller;

import com.zipurl.core.dto.CreateUrlRequest;
import com.zipurl.core.dto.ShortUrlResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateUrlController implements ControllerHandler {

    @PostMapping("/create")
    public ResponseEntity<ShortUrlResponse> createUrl(@RequestBody(required = false) CreateUrlRequest req) {
        ShortUrlResponse response = new ShortUrlResponse();
        response.setStatus("Failure");
        try {

            if (req != null && req.getUrl() != null && !req.getUrl().isBlank()) {
                /// TO-DO
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(response);
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        response.setStatus("Success");
        return ResponseEntity.ok().body(response);
    }
}
