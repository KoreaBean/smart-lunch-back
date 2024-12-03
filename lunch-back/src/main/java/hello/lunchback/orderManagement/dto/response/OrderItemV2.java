package hello.lunchback.orderManagement.dto.response;

import hello.lunchback.orderManagement.entity.OrderDetailEntity;
import lombok.Data;

@Data
public class OrderItemV2 {

    private String name;
    private Integer unitPrice;
    private Integer quantity;
    private Integer totalPrice;

    public OrderItemV2(OrderDetailEntity orderDetailEntity) {
        this.name = orderDetailEntity.getMenuName();
        this.unitPrice = orderDetailEntity.getMenuPrice();
        this.quantity = orderDetailEntity.getQuantity();
        this.totalPrice = unitPrice * quantity;
    }
}
