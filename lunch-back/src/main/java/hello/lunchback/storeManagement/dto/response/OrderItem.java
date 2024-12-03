package hello.lunchback.storeManagement.dto.response;

import hello.lunchback.orderManagement.dto.OrderStatus;
import lombok.Data;

@Data
public class OrderItem {
    private Integer orderId;
    private String orderDate;
    private Integer totalPrice;
    private OrderStatus status;
}
