package com.zipurl.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "zipurl")
public class ZipUrlConfig {
    private Integer shortIdSize;
    private String urlPrefix;
}
