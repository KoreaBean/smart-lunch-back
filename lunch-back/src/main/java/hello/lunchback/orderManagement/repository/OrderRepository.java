package hello.lunchback.orderManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<hello.lunchback.orderManagement.entity.OrderEntity,Integer> {
}
