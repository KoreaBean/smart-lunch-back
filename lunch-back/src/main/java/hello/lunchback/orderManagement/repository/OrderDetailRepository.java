package hello.lunchback.orderManagement.repository;

import hello.lunchback.orderManagement.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity,Integer> {
}
