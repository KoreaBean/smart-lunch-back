package hello.lunchback.storeManagement.repository;

import hello.lunchback.storeManagement.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface StoreRepository extends JpaRepository<StoreEntity,Integer> {
    Optional<StoreEntity> findByStoreId(Integer storeId);
}
