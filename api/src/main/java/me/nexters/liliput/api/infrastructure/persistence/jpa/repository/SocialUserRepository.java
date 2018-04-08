package me.nexters.liliput.api.infrastructure.persistence.jpa.repository;

import me.nexters.liliput.api.infrastructure.persistence.jpa.entity.SocialUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialUserRepository extends JpaRepository<SocialUser, Long> {
    SocialUser findByProviderUserId(String providerUserId);
}
