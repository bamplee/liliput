package me.nexters.liliput.api.domain.shorten.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
    ShortUrl findByPath(String path);
}
