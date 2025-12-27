package com.zipurl.core.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShortUrlResponse {
    private String status;
    private String message;
    private String url;
}
