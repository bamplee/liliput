package me.nexters.liliput.api.infrastructure.persistence.jpa.repository;

import me.nexters.liliput.api.infrastructure.persistence.jpa.entity.ShortUrl;
import me.nexters.liliput.api.infrastructure.persistence.jpa.entity.SocialUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
    ShortUrl findByPath(String path);

    List<ShortUrl> findByUser(SocialUser user);
}
