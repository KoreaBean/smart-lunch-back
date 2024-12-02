package hello.lunchback.storeManagement.dto.response;

import hello.lunchback.login.entity.MemberEntity;
import hello.lunchback.orderManagement.entity.OrderDetailEntity;
import hello.lunchback.orderManagement.entity.OrderEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StoreOrderDetailDto {
    private List<OrderDetailEntity> menuList = new ArrayList<>();
    private String orderDate;
    private String consumerName;
    private String consumerPhoneNumber;

    public void setData(List<OrderDetailEntity> menuList, OrderEntity orderEntity, MemberEntity consumer) {
        this.menuList = menuList;
        this.orderDate = orderEntity.getOrderDate();
        this.consumerName = consumer.getMemberName();
        this.consumerPhoneNumber = consumer.getMemberPhone();
    }
}
