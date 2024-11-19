package hello.lunchback.storeManagement.repository;

import hello.lunchback.storeManagement.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<StoreEntity,Integer> {
    Optional<StoreEntity> findByStoreId(Integer storeId);
}
