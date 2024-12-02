package hello.lunchback.orderManagement.dto.response;

import lombok.Data;

@Data
public class OrderNotificationDto {

    private Integer orderId;
    private String orderDate;
    private Long totalPrice;





    public OrderNotificationDto(Integer orderId, String orderDate, Long totalPrice) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }
}
