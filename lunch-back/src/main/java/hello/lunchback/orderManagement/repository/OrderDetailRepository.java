package hello.lunchback.orderManagement.repository;

import hello.lunchback.orderManagement.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity,Integer> {

    List<OrderDetailEntity> findByOrderOrderId(Integer orderId);
}
