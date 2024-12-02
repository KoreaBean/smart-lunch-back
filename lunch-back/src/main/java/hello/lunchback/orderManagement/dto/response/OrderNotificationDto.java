package hello.lunchback.orderManagement.dto.response;

import lombok.Data;

@Data
public class OrderNotificationDto {

    private Integer orderId;
    private String orderDate;
    private Integer totalPrice;





    public OrderNotificationDto(Integer orderId, String orderDate, Integer totalPrice) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }
}
