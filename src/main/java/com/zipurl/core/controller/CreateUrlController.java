package com.zipurl.core.controller;

import com.zipurl.core.constants.Constants;
import com.zipurl.core.dto.CreateUrlRequest;
import com.zipurl.core.dto.ShortUrlResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateUrlController implements ControllerHandler {

    @PostMapping("/create")
    public ResponseEntity<ShortUrlResponse> createUrl(@RequestBody(required = false) CreateUrlRequest req) {
        ShortUrlResponse response = new ShortUrlResponse();
        response.setStatus(Constants.FAILURE);
        try {

            if (req != null && req.getUrl() != null && !req.getUrl().isBlank()) {
                /// TO-DO
                response.setStatus(Constants.SUCCESS);
            } else {
                return ResponseEntity.unprocessableContent().body(response);
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.internalServerError().body(response);
        }
        return ResponseEntity.ok().body(response);
    }
}
