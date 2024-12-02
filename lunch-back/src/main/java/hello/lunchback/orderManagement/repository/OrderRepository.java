package hello.lunchback.orderManagement.repository;

import hello.lunchback.menuManagement.entity.MenuEntity;
import hello.lunchback.orderManagement.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Integer> {

    @Query( value = "select * from order_entity where member_member_id = :memberId and order_date >= date_sub(now(), interval 7 day)",
    nativeQuery = true)
    List<OrderEntity> findByMenuBeforeWeek(@Param("memberId") Long memberId);

    OrderEntity findByOrderId(Integer orderId);

}
