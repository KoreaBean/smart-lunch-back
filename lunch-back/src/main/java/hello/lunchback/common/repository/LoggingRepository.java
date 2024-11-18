package hello.lunchback.common.repository;

import hello.lunchback.common.entity.LoggingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggingRepository extends JpaRepository<LoggingEntity, Long> {
}
