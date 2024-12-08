package hello.lunchback.storeManagement.dto.response;

import hello.lunchback.orderManagement.dto.OrderStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem {
    private Integer orderId;
    private String orderDate;
    private Integer totalPrice;
    private OrderStatus status;
}
