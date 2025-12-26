package com.zipurl.core.repository;

import com.zipurl.core.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortUrlRepo extends JpaRepository<ShortUrl, String> {
}
