package me.nexters.liliput.api.infrastructure.persistence.jpa.repository;

import me.nexters.liliput.api.infrastructure.persistence.jpa.entity.RouteLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteLogRepository extends JpaRepository<RouteLog, Long> {
}
