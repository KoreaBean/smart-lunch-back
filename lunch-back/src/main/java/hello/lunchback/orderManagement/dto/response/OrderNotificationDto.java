package hello.lunchback.orderManagement.dto.response;

import hello.lunchback.orderManagement.dto.OrderStatus;
import lombok.Data;

@Data
public class OrderNotificationDto {

    private Integer orderId;
    private String orderDate;
    private Long totalPrice;
    private OrderStatus status;





    public OrderNotificationDto(Integer orderId, String orderDate, Long totalPrice, OrderStatus status) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.status = status;
    }
}
