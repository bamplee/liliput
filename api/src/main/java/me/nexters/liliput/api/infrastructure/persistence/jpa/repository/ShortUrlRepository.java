package me.nexters.liliput.api.infrastructure.persistence.jpa.repository;

import me.nexters.liliput.api.infrastructure.persistence.jpa.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
    ShortUrl findByPath(String path);
}
