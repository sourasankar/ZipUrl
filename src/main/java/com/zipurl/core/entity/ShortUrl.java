package com.zipurl.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(schema = "short_url")
public class ShortUrl {

    @Id
    @Column(name = "short_id", length = 10)
    private String shortId;

    @Column(name = "url", length = 200)
    private String url;
}
